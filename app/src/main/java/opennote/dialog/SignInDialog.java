package opennote.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import opennote.opennote.R;

/**
 * Created by Bwandy on 3/11/14.
 */
public class SignInDialog extends DialogFragment {

    public interface SignInDialogListener {
        public void OnPositiveClickListener(DialogFragment dialog);
        public void OnNegativeClickListener(DialogFragment dialog);
    }

    SignInDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (SignInDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " doit implémenter SignInDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_signin, null))
                // Add action buttons
                .setPositiveButton(R.string.connect, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.OnPositiveClickListener(SignInDialog.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.OnNegativeClickListener(SignInDialog.this);
                    }
                });
        return builder.create();
    }
}
