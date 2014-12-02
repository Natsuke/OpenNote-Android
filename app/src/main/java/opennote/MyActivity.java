package opennote;

import android.app.ProgressDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import opennote.database.CategoryGet;
import opennote.database.SignInActivity;
import opennote.dialog.SignInDialog;
import opennote.dialog.connectionErrorDialog;
import opennote.navigation.Model;
import opennote.navigation.MyAdapter;
import opennote.opennote.R;


public class MyActivity extends FragmentActivity implements SignInDialog.SignInDialogListener, connectionErrorDialog.connectionErrorDialogListener{

    private String[] data;
    private ArrayList<String> myData;
    private ArrayList<Model> itemList;
    private int userId;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        data = getResources().getStringArray(R.array.items);

        myData = new ArrayList<String>();
        myData.addAll(Arrays.asList(data));
        itemList = new ArrayList<Model>();
        adapter = new MyAdapter(this, itemList);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ListView navList = (ListView) findViewById(R.id.drawer);
        navList.setAdapter(adapter);
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int pos,long id) {
                if (pos != 0) {
                    mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                        @Override
                        public void onDrawerClosed(View drawerView) {
                            super.onDrawerClosed(drawerView);
                            String string = ((Model) navList.getItemAtPosition(pos)).getTitle();
                            ((TextView) findViewById(R.id.textView1)).setText(string);
                            CategoryGet cat = new CategoryGet(navList.getContext(), ((Model)navList.getItemAtPosition(pos)).getId());
                            cat.execute(((MyActivity)navList.getContext()).getUserId());
                        }
                    });
                    mDrawerLayout.closeDrawer(navList);
                }
            }
        });

        SignInDialog signInDialog = new SignInDialog();
        signInDialog.show(getSupportFragmentManager(), "Login");
    }

    @Override
    public void OnPositiveClickListener(final DialogFragment dialog) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Connexion");
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String username = ((EditText)dialog.getDialog().findViewById(R.id.username)).getText().toString();
                String password = ((EditText)dialog.getDialog().findViewById(R.id.password)).getText().toString();
                //TODO Check connectivity

                SignInActivity connexion = new SignInActivity(dialog.getActivity());
                connexion.execute(username, password);
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

    public void setUserId(int id){
        this.userId = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setData(String[] newData) {
        this.data = newData;
    }

    public ArrayList<Model> setDataModel(ArrayList<String> data) {
        itemList.clear();
        itemList.add(new Model("Catégories"));
        if (data.size() > 1) {
            for (int i = 0; i < data.size(); i = i + 2) {
                itemList.add(new Model(R.drawable.ic_action_labels, data.get(i), "1", Integer.parseInt(data.get(i + 1))));
            }
        }
        adapter.notifyDataSetChanged();
        return itemList;
    }

    public void updateContent(ArrayList<String> modelList) {
        ListView content = (ListView) findViewById(R.id.mainContent);
        ArrayList<Model> list = new ArrayList<Model>();
        if (modelList.size() > 1) {
            for(int i = 0; i < modelList.size(); i = i+2) {
                list.add(new Model(R.drawable.ic_action_labels, modelList.get(i), "1", Integer.parseInt(modelList.get(i+1))));
            }
        }
        list.add(new Model(R.drawable.ic_action_view_as_list, "Titre de note", "0", 0));
        MyAdapter myAdapter = new MyAdapter(this, list);
        content.setAdapter(myAdapter);
    }
}
