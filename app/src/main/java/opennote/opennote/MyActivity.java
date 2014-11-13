package opennote.opennote;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;


public class MyActivity extends FragmentActivity implements SignInDialog.SignInDialogListener, connectionErrorDialog.connectionErrorDialogListener{

    private TextView mLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        SignInDialog signInDialog = new SignInDialog();
        signInDialog.show(getSupportFragmentManager(), "Login");
        mLabel = (TextView)findViewById(R.id.label);
    }

    @Override
    public void OnPositiveClickListener(final DialogFragment dialog) {
        // TODO lancer la méthode de connexion
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Connexion");
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String username = ((EditText)dialog.getDialog().findViewById(R.id.username)).getText().toString();
                String password = ((EditText)dialog.getDialog().findViewById(R.id.password)).getText().toString();
                //TODO Check connectivity

                new SignInActivity(dialog.getActivity(), mLabel).execute(username, password);
                progressDialog.dismiss();
            }
        }).start();
    }

    @Override
    public void onPositiveClickListener(DialogFragment dialog){
        dialog.dismiss();
        SignInDialog signInDialog = new SignInDialog();
        signInDialog.show(getSupportFragmentManager(), "Login");
    }

    @Override
    public void OnNegativeClickListener(DialogFragment dialog) {
        // TODO Récupérer les fichers stocké
        dialog.dismiss();
        this.startActivity(new Intent(this, menu.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
