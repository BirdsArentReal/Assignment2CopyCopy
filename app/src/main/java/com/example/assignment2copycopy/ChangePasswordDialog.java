package com.example.assignment2copycopy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ChangePasswordDialog extends AppCompatDialogFragment {
    EditText IDTxt, Password1Txt;
    passwordChangeListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_change_password, null);
        IDTxt= view.findViewById(R.id.changePasswordStudentID);
        Password1Txt = view.findViewById(R.id.changePasswordPassword1);

        Account user = Database.user;
        final String studentID = user.getStudentID();

        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(IDTxt.getText().equals(studentID)){
                    listener.changePassword(Password1Txt.getText().toString());
                }
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (passwordChangeListener) context;
        } catch (Exception e) {
            Log.d("password change error", e.getMessage());
        }
    }

    public interface passwordChangeListener{
        void changePassword(String newPassword);
    }
}
