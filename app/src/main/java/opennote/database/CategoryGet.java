package opennote.database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import opennote.navigation.Model;
import opennote.opennote.R;

/**
 * Created by Bwandy on 26/11/14.
 */
public class CategoryGet extends AsyncTask<Integer,Void,String[]>{

    private Context context;
    private int parent_id;
    //private final String link = "http://10.99.1.178/cat_get.php";

    public CategoryGet(Context context) {
        this.context = context;
        this.parent_id = 0;
    }
    public CategoryGet(Context context, int parent_id) {
        this.context = context;
        this.parent_id = parent_id;
    }

    protected void onPreExecute(){

    }
    @Override
    protected String[] doInBackground(Integer...params) {
        try{
            int userId = params[0];
            String data  =
                    "id=" + userId;
            data += "&parentId=" + parent_id;

            String link = "https://open-note.ddns.net/android/cat_get.php";
            URL url = new URL(link);
            trustEveryone();
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter
                    (conn.getOutputStream(), "UTF-8");
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
            ((MyActivity)context).setData(new String[]{""});
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
        if (result == null) {
            textView.setText("NullPointer");
        }
        else {
            ArrayList<String> modelList = new ArrayList<String>();
            if (parent_id == 0) {
                modelList.addAll(Arrays.asList(result));
                ((MyActivity) context).setDataModel(modelList);
            }
            else {
                modelList.addAll(Arrays.asList(result));
                ((MyActivity)context).updateContent(modelList);
            }
        }
    }
}
