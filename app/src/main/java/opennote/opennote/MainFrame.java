package opennote.opennote;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

/**
 * Created by Bwandy on 26/11/14.
 */
public class MainFrame extends Fragment {

    private TabHost mTabHost;

    public static Fragment newInstance(Context context, String cat) {
        MainFrame f = new MainFrame();
        Bundle args = new Bundle();
        args.putString("Category", cat);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.main_frame, null);
        return root;
    }
}
