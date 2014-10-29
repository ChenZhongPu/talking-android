package com.nwpu.chenzhongpu.talking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chenzhongpu on 10/19/14.
 */
public class DrawerItemAdapter extends BaseAdapter{

    private List<DrawerItemBean> mData;
    private Context mContext;
    private LayoutInflater mLayoutInfalter;

    public DrawerItemAdapter(List<DrawerItemBean> data, Context context)
    {
        this.mData = data;
        this.mContext = context;
        this.mLayoutInfalter = LayoutInflater.from(context);
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

        DrawerItemBean itemBean = mData.get(position);
        ViewHolder viewHolder;

        if(view == null){

            view = mLayoutInfalter.inflate(R.layout.drawer_item, null);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.drawer_menu_tv);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.drawer_menu_iv);
            view.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(itemBean.getContent());
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), itemBean.getImg());
        viewHolder.imageView.setImageBitmap(bitmap);

        return view;
    }


   private static class ViewHolder{

        public TextView textView;
        public ImageView imageView;
    }
}
