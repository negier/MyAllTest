package com.negier.contactlistview;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.negier.contactlistview.adapter.ContactsRecyclerView;
import com.negier.contactlistview.bean.Contact;
import com.negier.contactlistview.util.PinyinComparator;
import com.negier.contactlistview.view.SideBar;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 联系人页面Activity
 */
public class ContactsActivity extends AppCompatActivity {
    private SideBar mSideBar;
    private TextView mTvLetter;
    private ArrayList<Contact> mContactLists;
    private RecyclerView mRvContacts;
    private ContactsRecyclerView mContactsRecyclerViewAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    /**
     * 这个是防止一些特殊的事情，比如突然锁屏，sidebar还是触摸状态，屏幕中间还有一个大大的A-Z的字母
     */
    @Override
    protected void onResume() {
        super.onResume();
        mSideBar.restore();
    }

    private void initView() {
        setContentView(R.layout.activity_contacts);
        mSideBar = (SideBar) findViewById(R.id.sidebar);
        mTvLetter = (TextView) findViewById(R.id.tv_letter);
        mRvContacts = (RecyclerView) findViewById(R.id.rv_contacts);
        mLinearLayoutManager = new LinearLayoutManager(this);
        //这个一定要设置，我以前以为其默认有个LinearLayoutManager，但事实不是。
        mRvContacts.setLayoutManager(mLinearLayoutManager);
    }

    private void initData() {
        mSideBar.setTextView(mTvLetter);
        //联系人数据集合，数据源集合
        mContactLists = new ArrayList<>();
        //获取联系人姓名
        String[] contacts = getResources().getStringArray(R.array.contactname);
        for (int i = 0; i < contacts.length; i++) {
            Contact contact = new Contact();
            String contactName = contacts[i];
            String firstName = contactName.substring(0, 1);
            String firstLetter;
            try {
                byte[] gbkBytes = firstName.getBytes("GBK");
                if (gbkBytes.length==2){
                    //这里用到了pinyin4j这个类
                    String[] pinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(firstName.charAt(0));
                    //首字母
                    firstLetter = pinyinStringArray[0].toUpperCase().substring(0,1);
                }else{
                    firstLetter=firstName.substring(0,1);
                }
                // 正则表达式，判断首字母是否是英文字母
                if (firstLetter.matches("[A-Z]")) {
                    contact.setFirstLetter(firstLetter);
                } else {
                    contact.setFirstLetter("#");
                }
                contact.setName(contactName);
//                contact.setHeadUrl();//这里装载你用户的头像url地址。
                mContactLists.add(contact);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //对mContactLists进行排序
        Collections.sort(mContactLists,new PinyinComparator());
        mContactsRecyclerViewAdapter = new ContactsRecyclerView(this, mContactLists);
        mRvContacts.setAdapter(mContactsRecyclerViewAdapter);
    }

    private void initListener() {
        mSideBar.setOnTouchLetterListener(new SideBar.OnTouchLetterListener() {
            @Override
            public void OnTouchLetter(String letter) {
                int position = mContactsRecyclerViewAdapter.getFirstPosition(letter);
                mLinearLayoutManager.scrollToPositionWithOffset(position,0);
            }
        });
        mContactsRecyclerViewAdapter.setOnContactsItemClickListener(new ContactsRecyclerView.OnContactsItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                /**
                 * 这里可以处理你的跳转逻辑，是要跳到资料页，还是聊天页，随你愿
                 */
                Toast.makeText(ContactsActivity.this,mContactLists.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
