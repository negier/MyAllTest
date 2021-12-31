package com.negier.coverbaidulangbottom;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import com.negier.coverbaidulangbottom.fragments.DictFragment;
import com.negier.coverbaidulangbottom.fragments.DiscoverFragment;
import com.negier.coverbaidulangbottom.fragments.MeFragment;
import com.negier.coverbaidulangbottom.fragments.SpeechFragment;
import com.negier.coverbaidulangbottom.fragments.VocabFragment;

public class CoverBaiduLangBottomMainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {

    private static final String TAB_DICT = "dict";
    private static final String TAB_DISCOVER = "discover";
    private static final String TAB_VOCAB = "vocab";
    private static final String TAB_ME = "me";
    private static final String TAB_SPEECH = "speech";
    private FragmentTabHost tabHost;
    private TabIndicatorView discoverIndicatorView;
    private TabIndicatorView vocabIndicatorView;
    private TabIndicatorView meIndicatorView;
    private TabIndicatorView dictIndicatorView;
    private TabIndicatorView speechIndicatorView;
    private ImageView ivSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cover_baidu_lang_bottom);

        ivSpeech = (ImageView)findViewById(R.id.iv_speech);
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.fl_container);

        initTab();
    }

    private void initTab() {
        TabHost.TabSpec dictSpec = tabHost.newTabSpec(TAB_DICT);
        dictIndicatorView = new TabIndicatorView(this);
        dictIndicatorView.setTabIcon(R.mipmap.tabbar_dict_normal);
        dictIndicatorView.setTabHint("首页");
        dictSpec.setIndicator(dictIndicatorView);
        tabHost.addTab(dictSpec, DictFragment.class, null);

        TabHost.TabSpec discoverSpec = tabHost.newTabSpec(TAB_DISCOVER);
        discoverIndicatorView = new TabIndicatorView(this);
        discoverIndicatorView.setTabIcon(R.mipmap.tabbar_discover_normal);
        discoverIndicatorView.setTabHint("发现");
        discoverSpec.setIndicator(discoverIndicatorView);
        tabHost.addTab(discoverSpec, DiscoverFragment.class, null);

        TabHost.TabSpec speechSpec = tabHost.newTabSpec(TAB_SPEECH);
        speechIndicatorView = new TabIndicatorView(this);
        speechIndicatorView.setTabIcon(R.mipmap.tabbar_speech_normal);
        speechIndicatorView.setTabHint("语音");
        speechSpec.setIndicator(speechIndicatorView);
        tabHost.addTab(speechSpec, SpeechFragment.class, null);

        TabHost.TabSpec vocabSpec = tabHost.newTabSpec(TAB_VOCAB);
        vocabIndicatorView = new TabIndicatorView(this);
        vocabIndicatorView.setTabIcon(R.mipmap.tabbar_vocab_normal);
        vocabIndicatorView.setTabHint("收藏");
        vocabSpec.setIndicator(vocabIndicatorView);
        tabHost.addTab(vocabSpec, VocabFragment.class, null);

        TabHost.TabSpec meSpec = tabHost.newTabSpec(TAB_ME);
        meIndicatorView = new TabIndicatorView(this);
        meIndicatorView.setTabIcon(R.mipmap.tabbar_me_normal);
        meIndicatorView.setTabHint("个人");
        meSpec.setIndicator(meIndicatorView);
        tabHost.addTab(meSpec, MeFragment.class, null);

        tabHost.setOnTabChangedListener(this);

        //去掉那些竖线
        tabHost.getTabWidget().setDividerDrawable(android.R.color.white);
        initTabIndicatorSelect();
        dictIndicatorView.setTabSelected(true);
    }

    @Override
    public void onTabChanged(String tabId) {
        initTabIndicatorSelect();
        switch (tabId) {
            case TAB_DICT:
                dictIndicatorView.setTabSelected(true);
                break;
            case TAB_DISCOVER:
                discoverIndicatorView.setTabSelected(true);
                break;
            case TAB_SPEECH:
                speechIndicatorView.setTabSelected(true);
                ivSpeech.setImageResource(R.mipmap.tabbar_speech_active);
                break;
            case TAB_VOCAB:
                vocabIndicatorView.setTabSelected(true);
                break;
            case TAB_ME:
                meIndicatorView.setTabSelected(true);
                break;
            default:
                break;
        }
    }

    /**
     * 将所有的TabIndicatorView设置为未选择
     */
    public void initTabIndicatorSelect(){
        dictIndicatorView.setTabSelected(false);
        discoverIndicatorView.setTabSelected(false);
        speechIndicatorView.setTabSelected(false);
        vocabIndicatorView.setTabSelected(false);
        meIndicatorView.setTabSelected(false);
        ivSpeech.setImageResource(R.mipmap.tabbar_speech_normal);
    }
}
