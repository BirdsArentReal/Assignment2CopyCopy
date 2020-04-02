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

public class NewsDialog  extends AppCompatDialogFragment {
    EditText titleTxt, bodyTxt;
    NewsDialog.newsChangeListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_news, null);
        titleTxt= view.findViewById(R.id.dialogNewsTitle);
        bodyTxt = view.findViewById(R.id.dialogNewsBody);

        builder.setView(view);
        builder.setTitle("Change News");
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.newNews(titleTxt.getText().toString(),bodyTxt.getText().toString());

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
            listener = (NewsDialog.newsChangeListener) context;
        } catch (Exception e) {
            Log.d("news change error", e.getMessage());
        }
    }

    public interface newsChangeListener{
        void newNews(String newTitle, String newBody);
    }
}
