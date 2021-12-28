package com.xuebinduan.base64;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.xuebinduan.base64.databinding.ActivityBase64Binding;

public class Base64Activity extends BackActivity {

    private ActivityBase64Binding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolBar().setTitle("Base64编码转换");
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base64);
    }

    public void onClickConvert(View view){
        try {
            String str = mBinding.editContent.getText().toString().trim();
            String decodeContent = new String(Base64.decode(str, Base64.NO_WRAP));
            mBinding.textResult.setText(decodeContent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
