package opennote.database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import opennote.dialog.connectionErrorDialog;
import opennote.MyActivity;

public class SignInActivity  extends AsyncTask<String,Void,String>{

    private Context context;
    //private final String link = "http://10.99.1.178/sign_in.php";

    public SignInActivity(Context context) {
        this.context = context;
    }

    protected void onPreExecute(){

    }
    @Override
    protected String doInBackground(String... arg0) {
        try{
            String username = arg0[0];
            String password = arg0[1];
            String data  =
                    "username=" + username;
            data += "&password=" + password;
            String link = "https://open-note.ddns.net/android/sign_in.php";
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
            String line;
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
            return sb.toString();
        }catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});
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
    protected void onPostExecute(String result){
        if (result.equals("KO")) {
            connectionErrorDialog errorDialog = new connectionErrorDialog();
            Bundle arg = new Bundle();
            arg.putString("message", "Identifiant et Mot de passe invalide.");
            errorDialog.setArguments(arg);
            errorDialog.show(((FragmentActivity) context).getSupportFragmentManager(), "Error");
        } else {
            ((MyActivity)context).setUserId(Integer.parseInt(result));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CategoryGet categoryGet = new CategoryGet(context);
                    categoryGet.execute( ((MyActivity)context).getUserId() );
                }
            }).start();
        }
    }
}