package opennote.opennote;

import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.concurrent.ExecutionException;

import opennote.database.NotePrintFromURL;


public class MainFrame extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frame);

        Intent intent = getIntent();

        int id = intent.getIntExtra("id", 0);
        String titre = intent.getStringExtra("titre");

        setTitle(titre);

        final WebView content = (WebView)findViewById(R.id.webview);

        content.getSettings().setBuiltInZoomControls(true);
        content.getSettings().setLoadWithOverviewMode(false);
        content.getSettings().setUseWideViewPort(true);
        final Activity activity = this;
        content.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);
            }
        });

        content.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(final WebView view, final SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        NotePrintFromURL printFromURL = new NotePrintFromURL();
        try {
            content.loadData(printFromURL.execute(id).get(), "text/html", "UTF-8");
        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_frame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
