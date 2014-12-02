package opennote.database;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Bwandy on 20/11/14.
 */
public class SyncDataBase extends AsyncTask {

    private Context mContext;

    public SyncDataBase(Context context) {
        mContext = context;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        db.getReadableDatabase();
        return null;
    }
}
