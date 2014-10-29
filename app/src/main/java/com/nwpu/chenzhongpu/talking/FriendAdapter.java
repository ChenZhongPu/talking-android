package com.nwpu.chenzhongpu.talking;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chenzhongpu on 10/23/14.
 */
public class FriendAdapter extends BaseAdapter{

    private List<FriendReplyBean> mData;

    private Context mContext;

    private LayoutInflater mInflater;


    public static final String SAYFORMAT = "<font color='#576b95'>%s:</font>%s";

    public static final String REPLYFORMAT = "<font color='#576b95'>%s</font>回复<font color='#576b95'>%s:</font>%s";

    public FriendAdapter(List<FriendReplyBean> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;

        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        FriendReplyBean replyBean = mData.get(position);

        ViewHolder viewHolder;

        if(view == null){

            view = mInflater.inflate(R.layout.friend_item_say, null);

            viewHolder = new ViewHolder();
            viewHolder.tv = (TextView) view.findViewById(R.id.friend_comment_edittext);

            view.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder) view.getTag();
        }


        if(replyBean.getTo() == null||replyBean.getTo().equals("")){

           String friend = replyBean.getFrom();

           String content = replyBean.getContent();

           String txt = String.format(SAYFORMAT, friend, content);

            Spanned spanned = Html.fromHtml(txt);

            viewHolder.tv.setText(spanned);

        }
        else{

            String friendFrom = replyBean.getFrom();

            String friendTo = replyBean.getTo();

            String content = replyBean.getContent();

            String txt = String.format(REPLYFORMAT, friendFrom, friendTo, content);

            Spanned spanned = Html.fromHtml(txt);

            viewHolder.tv.setText(spanned);
        }

        return view;
    }

    private static class ViewHolder{

        public TextView tv;
    }
}
