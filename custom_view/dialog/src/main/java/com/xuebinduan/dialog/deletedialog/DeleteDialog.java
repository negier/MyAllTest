package com.xuebinduan.dialog.deletedialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuebinduan.dialog.R;

public class DeleteDialog extends Dialog implements View.OnClickListener {
    private TextView mTextMsg;
    private TextView mTextCancel;
    private TextView mTextDelete;

    public DeleteDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题

        setContentView(R.layout.dialog_delete);

        //为了圆角
        Window window=this.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mTextMsg = findViewById(R.id.text_msg);
        mTextCancel = findViewById(R.id.text_cancel);
        mTextDelete = findViewById(R.id.text_delete);

        mTextCancel.setOnClickListener(this);
        mTextDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (listener!=null){
            if (v.getId()==R.id.text_cancel){
                listener.onCancel();
            }else if (v.getId() == R.id.text_delete){
                listener.onDelete();
            }
        }
    }

    public interface OnClickListener{
        void onDelete();
        void onCancel();
    }

    private OnClickListener listener;

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

}
