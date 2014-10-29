package com.nwpu.chenzhongpu.talking;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.List;

/**
 * Created by chenzhongpu on 10/21/14.
 */
public class QQAdapter extends BaseAdapter {

    public static interface QQMsgType{

        public int QQ_FROM = 1;
        public int QQ_TO = 2;
        public int QQ_TIME = 3;
    }

    private List<WeChatMsgBean> mData;
    private Context mContext;
    private LayoutInflater mInflater;


    public static  final int QQ_LOAD_IMAGE_RESULT = 2;

    private ImageView lastSelectedIV;

    public QQAdapter(List<WeChatMsgBean> mData, Context mContext) {
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

        final WeChatMsgBean weChatMsgBean = mData.get(position);

        final ViewHolder viewHolder;

        if(view == null){

            switch (weChatMsgBean.getType()){

                case QQMsgType.QQ_FROM:
                    view = mInflater.inflate(R.layout.qq_item_from_txt, null);
                    break;
                case QQMsgType.QQ_TO:
                    view = mInflater.inflate(R.layout.qq_item_to_txt, null);
                    break;
                case QQMsgType.QQ_TIME:
                    view = mInflater.inflate(R.layout.qq_item_time, null);
                    break;

            }

            viewHolder = new ViewHolder();
            viewHolder.contentTV = (TextView) view.findViewById(R.id.qqchat_content);

            if(weChatMsgBean.getType() != QQMsgType.QQ_TIME){

                viewHolder.userAvatar = (ImageView) view.findViewById(R.id.qq_avatarIV);
            }
            view.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.contentTV.setText(weChatMsgBean.getContent());


        viewHolder.contentTV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {

                if(!focus){

                    weChatMsgBean.setContent(viewHolder.contentTV.getText().toString());
                }

            }
        });

        if(weChatMsgBean.getType() == QQMsgType.QQ_TIME){

            viewHolder.contentTV.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    TimePickerDialog timePickerDialog =
                            new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                                         String content = convertTime(hour, minute);
                                         viewHolder.contentTV.setText(content);

                                         weChatMsgBean.setContent(content);


                                }
                            }, 13,12,true);

                    timePickerDialog.show();

                    return true;
                }
            });
        }


        if(weChatMsgBean.getType() != QQMsgType.QQ_TIME){

          viewHolder.userAvatar.setOnLongClickListener(new View.OnLongClickListener() {
              @Override
              public boolean onLongClick(View view) {


                  Intent itent = new Intent(Intent.ACTION_PICK,
                          MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                  lastSelectedIV = viewHolder.userAvatar;

                  ((Activity)(mContext)).startActivityForResult(itent, QQ_LOAD_IMAGE_RESULT);
                  return true;
              }
          });

        }

        return view;
    }



    public void setQQAvatar(String imagePath){


        lastSelectedIV.setImageBitmap(BitmapFactory.decodeFile(imagePath));
    }

    private String convertTime(int hour, int minute){

        StringBuilder sb = new StringBuilder();

        if(hour < 10){
            sb.append("0");
        }
        sb.append(hour);
        sb.append(":");

        if(minute < 10){

            sb.append("0");
        }

        sb.append(minute);

        return sb.toString();
    }

    private static class ViewHolder{

        public TextView contentTV;

        public ImageView userAvatar;

    }
}
