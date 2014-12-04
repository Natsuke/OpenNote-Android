package opennote.database;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Bwandy on 3/12/14.
 */
public class NotePrintFromURL extends AsyncTask<Integer, Void, String> {

    private final String link = "https://open-note.ddns.net/android/get_path.php";

    @Override
    protected String doInBackground(Integer... params) {
        String result[] = new String[] {"", ""};
        try{
            int category = params[0];
            String data  =
                    "id=" + category;

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
            result = sb.toString().split(";");
            System.out.println(Arrays.asList(result));
        }catch(Exception e){
            System.out.println("Erreur");
        }

        StringBuilder sb = new StringBuilder();
        try {
            String path = "https://open-note.ddns.net/";
            path += result[0];
            path += URLEncoder.encode(result[1], "UTF-8").replace("+", "%20");

            URL url = new URL(path);
            System.out.println(path);
            trustEveryone();
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            sb.append("<html><body><div align=\"justify\"");
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            sb.append("</div></body></html>");
        }catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String list) {

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
}
