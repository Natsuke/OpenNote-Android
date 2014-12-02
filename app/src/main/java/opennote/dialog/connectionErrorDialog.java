package opennote.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import opennote.opennote.R;

/**
 * Created by Bwandy on 6/11/14.
 */
public class connectionErrorDialog extends DialogFragment {

    public interface connectionErrorDialogListener {
        public void onPositiveClickListener(DialogFragment dialog);
    }

    private connectionErrorDialogListener mListener;
    private String message;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (connectionErrorDialogListener) activity;
        } catch (Exception e) {
            throw new ClassCastException(activity.toString() + " doit implémenter connectionErrorListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.connection_error)
                .setTitle(R.string.error)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onPositiveClickListener(connectionErrorDialog.this);
                    }
                });
        return builder.create();
    }
}
