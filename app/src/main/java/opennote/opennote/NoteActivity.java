package opennote.opennote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import opennote.database.NoteGet;
import opennote.navigation.Model;
import opennote.navigation.MyAdapter;


public class NoteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        setTitle("Notes");

        Intent intent = getIntent();
        int idNote = intent.getIntExtra("id", 0);
        String title = intent.getStringExtra("titre");

        TextView titre = (TextView)findViewById(R.id.textView2);
        titre.setText(title);

        NoteGet noteGet = new NoteGet(this);
        noteGet.execute(idNote);

        final ListView noteList = (ListView)findViewById(R.id.listView1);
        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mainframe = new Intent();
                mainframe.setClass(noteList.getContext(), MainFrame.class);
                mainframe.putExtra("id", ((Model) noteList.getItemAtPosition(position)).getId());
                mainframe.putExtra("titre", (((Model) noteList.getItemAtPosition(position)).getTitle()));
                startActivity(mainframe);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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

    public void updateContent(ArrayList<String> modelList) {
        ListView noteList = (ListView)findViewById(R.id.listView1);
        ArrayList<Model> models = new ArrayList<Model>();
        if(modelList.size() > 1) {
            for (int i = 0; i < modelList.size(); i = i+2){
                models.add(new Model(R.drawable.ic_action_view_as_list, modelList.get(i), Integer.parseInt(modelList.get(i + 1))));
            }
        }
        MyAdapter adapter = new MyAdapter(this, models);
        noteList.setAdapter(adapter);
    }
}
