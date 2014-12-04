package opennote.database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import opennote.MyActivity;
import opennote.data.Category;
import opennote.opennote.NoteActivity;
import opennote.opennote.R;

/**
 * Created by Bwandy on 26/11/14.
 */
public class NoteGet extends AsyncTask<Integer,Void,String[]>{

    private Context context;

    //private final String link = "http://10.99.1.178/note_get.php";

    public NoteGet(Context context) {
        this.context = context;
    }

    protected void onPreExecute(){

    }
    @Override
    protected String[] doInBackground(Integer...params) {
        try{
            int category = params[0];
            String data  =
                    "id=" + category;

            String link = "https://open-note.ddns.net/android/note_get.php";
            URL url = new URL(link);
            trustEveryone();
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter
                    (conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String result[];
            String line;
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
            result = sb.toString().split(";");
            return result;
        }catch(Exception e){
            return new String[] {("Exception: " + e.getMessage())};
        }
    }

    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String[] result){
        TextView textView = (TextView)((Activity)context).findViewById(R.id.textView1);
        ArrayList<String> modelist = new ArrayList<String>();
        modelist.addAll(Arrays.asList(result));
        ((NoteActivity)context).updateContent(modelist);
    }
}
