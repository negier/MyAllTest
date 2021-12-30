package com.negier.contactlistview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.negier.contactlistview.R;
import com.negier.contactlistview.bean.Contact;

import java.util.ArrayList;



/**
 * Created by Administrator on 2017/1/26 0026.
 */

public class ContactsRecyclerView extends RecyclerView.Adapter<ContactsRecyclerView.ContactViewHolder>{
    private final Context context;
    private ArrayList<Contact> contacts;

    public ContactsRecyclerView(Context context, ArrayList<Contact> contacts) {
        this.context=context;
        this.contacts=contacts;
    }
    public void updateRecyclerView(ArrayList<Contact> contacts){
        this.contacts=contacts;
        notifyDataSetChanged();
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactItem = View.inflate(context, R.layout.item_recyclerview_contacts, null);
        ContactViewHolder contactViewHolder = new ContactViewHolder(contactItem);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {
        Contact contact = contacts.get(position);
        String currentFirstLetter = contacts.get(position).getFirstLetter();
        if ( getFirstPosition(currentFirstLetter)==position){
            holder.tvFirstLetter.setText(contact.getFirstLetter());
            holder.tvFirstLetter.setVisibility(View.VISIBLE);
        }else{
            holder.tvFirstLetter.setVisibility(View.GONE);
        }
        holder.tvName.setText(contact.getName());
        if (holder.ivHead==null){
            holder.ivHead.setImageResource(R.mipmap.head);//默认的头像
        }else{
            //这里用你的逻辑，获取网络上的图片显示
        }
        holder.llClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnContactsItemClickListener!=null){
                    mOnContactsItemClickListener.OnItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvFirstLetter;
        private final TextView tvName;
        private final ImageView ivHead;
        private final LinearLayout llClick;

        public ContactViewHolder(View itemView) {
            super(itemView);
            tvFirstLetter = (TextView) itemView.findViewById(R.id.tv_first_letter);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            llClick = (LinearLayout) itemView.findViewById(R.id.ll_click);
        }
    }

    /**
     * 输入firstLetter，即首字母，返回他第一次出现的位置
     * @param currentFirstLetter
     * @return
     */
    public int getFirstPosition(String currentFirstLetter){
        for (int i = 0; i < contacts.size(); i++) {
            String firstLetter = contacts.get(i).getFirstLetter();
            if (firstLetter.equals(currentFirstLetter)){
                    return i;
                }
            }
        return -1;
    }
    /**
     * 对外的接口
     * 点击事件的传递
     */
    private OnContactsItemClickListener mOnContactsItemClickListener;
    public void setOnContactsItemClickListener(OnContactsItemClickListener mOnContactsItemClickListener){
        this.mOnContactsItemClickListener=mOnContactsItemClickListener;
    }
    public interface OnContactsItemClickListener{
        void OnItemClick(int position);
    }
}
