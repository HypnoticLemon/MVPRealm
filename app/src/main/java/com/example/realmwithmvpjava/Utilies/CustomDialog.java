package com.example.realmwithmvpjava.Utilies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.realmwithmvpjava.R;

public class CustomDialog extends Dialog {

    private ButtonClickListener clickListener;
    private Context context;
    private TextView dialogTitleText;
    private Button dialogOkButton;


    public CustomDialog(Context context, ButtonClickListener listener) {
        super(context);
        this.clickListener = listener;
        this.context = context;


    }

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    public interface ButtonClickListener {
        void onButtonClick();
    }

    public void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        setCancelable(false);
        Window window = getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogTitleText = findViewById(R.id.dialogTitleText);
        dialogOkButton = findViewById(R.id.dialogOkButton);
        show();

        dialogOkButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                clickListener.onButtonClick(); // I am giving the click to the
                // interface function which we need
                // to implements where we call this
                // class

            }
        });
    }


    public void setTitleText(String titleText) {
        dialogTitleText.setText(titleText);
    }


}
