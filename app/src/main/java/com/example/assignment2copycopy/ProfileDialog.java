package com.example.assignment2copycopy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ProfileDialog extends AppCompatDialogFragment {
    private TextView nameTxt, studentIDTxt, mgTxt, literalMGTxt, literalStudentIDTxt;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_profile, null);
        nameTxt = view.findViewById(R.id.nameText);
        mgTxt = view.findViewById(R.id.mgText);
        studentIDTxt = view.findViewById(R.id.studentIDText);
        literalMGTxt = view.findViewById(R.id.literalMGTxt);
        literalStudentIDTxt = view.findViewById(R.id.literalStudentIDTxt);

        Account user = Database.user;
        nameTxt.setText(user.getName());
        mgTxt.setText(user.getMentor_group());
        studentIDTxt.setText(user.getStudentID());
        if (user.getMentor_group().equals("null"))
        {
            mgTxt.setVisibility(View.GONE);
            literalMGTxt.setVisibility(View.GONE);
            literalStudentIDTxt.setText("NRIC:");

        }

        builder.setView(view);
        builder.setTitle("Profile");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
