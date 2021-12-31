package com.negier.coverbaidulangbottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by NEGIER on 2017/10/31.
 */

public class TabIndicatorView extends RelativeLayout {
    private ImageView ivTabNormalIcon;
    private ImageView ivTabFocusIcon;
    private TextView tvTabHint;

    public TabIndicatorView(Context context) {
        this(context,null);
    }

    public TabIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context,R.layout.tab_indicator,this);

        ivTabNormalIcon = (ImageView) findViewById(R.id.tab_indicator_normal_icon);
        ivTabFocusIcon = (ImageView) findViewById(R.id.tab_indicator_focus_icon);

        tvTabHint = (TextView) findViewById(R.id.tab_indicator_hint);
    }

    public void setTabIcon(int normalIconId){
        ivTabNormalIcon.setImageResource(normalIconId);
        ivTabFocusIcon.setImageResource(normalIconId);
    }

    public void setTabSelected(boolean selected) {
        if (selected) {
            ivTabNormalIcon.setVisibility(View.GONE);
            ivTabFocusIcon.setVisibility(View.VISIBLE);
            tvTabHint.setTextColor(Color.parseColor("#17A554"));
        } else {
            ivTabNormalIcon.setVisibility(View.VISIBLE);
            ivTabFocusIcon.setVisibility(View.GONE);
            tvTabHint.setTextColor(Color.parseColor("#BFBFBF"));
        }
    }

    public void setTabHint(String hint){
        if (hint==""){
            tvTabHint.setVisibility(View.GONE);
        }else{
            tvTabHint.setText(hint);
        }
    }
}
