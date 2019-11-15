package com.example.projecta;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;

public class CustomDialog extends Dialog {

    TextView positive,negative;
    EditText name,date,time,desc;


    View.OnClickListener positiveListener;


    public String getName(){
        if(name != null)
            return name.getText().toString();
        else
            return "";
    }
//    public String getTime(){
//        if(time != null)
//            return time.getText().toString();
//        else
//            return "";
//    }
    public String getDesc(){
        if(desc != null)
            return desc.getText().toString();
        else
            return "";
    }
    View.OnClickListener negativeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
        //layoutParams.flags=WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount=0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.dialog);

        positive=findViewById(R.id.text_ok);
        negative=findViewById(R.id.text_no);
        name=findViewById(R.id.edit_name);
//        date=findViewById(R.id.edit_date);
        //time=findViewById(R.id.edit_time);
        desc=findViewById(R.id.edit_desc);

        positive.setOnClickListener(positiveListener);
        negative.setOnClickListener(negativeListener);
    }

    public void setPositiveListener(View.OnClickListener positiveListener) {
        this.positiveListener = positiveListener;
    }

    public CustomDialog(@NonNull Context context, View.OnClickListener negativeListener) {
        super(context);
        this.negativeListener=negativeListener;
    }
}
