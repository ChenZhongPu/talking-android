package com.nwpu.chenzhongpu.talking;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.List;

/**
 * Created by chenzhongpu on 10/19/14.
 */
public class WeChatAdapter extends BaseAdapter {

    public static interface WeChatMsgType{

        public int FROM_TXT = 0;
        public int FROM_VOICE = 1;
        public int TO_TXT = 2;
        public int TO_VOICE = 3;
        public int TIME_MSG = 4;
    }

    public static final int LOAD_IMAGE_RESULT = 1;

    // change the avatar
    private int lastSelectedAvatarPosition;

    private ImageView lastSelectedAvatar;

    private List<WeChatMsgBean> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;



    // store the position : swipe to  delete
    private float x1;
    private float x2;

    public WeChatAdapter(List<WeChatMsgBean> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
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
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final WeChatMsgBean weChatMsgBean = mData.get(position);

        final ViewHolder viewHolder;

        if(view == null){

            switch (weChatMsgBean.getType()){

                case WeChatMsgType.FROM_TXT:
                    view = mLayoutInflater.inflate(R.layout.wechat_item_from_txt, null);
                    break;
                case WeChatMsgType.FROM_VOICE:
                    view = mLayoutInflater.inflate(R.layout.wechat_item_from_voice, null);
                    break;
                case WeChatMsgType.TO_TXT:
                    view = mLayoutInflater.inflate(R.layout.wechat_item_to_txt, null);
                    break;
                case WeChatMsgType.TO_VOICE:
                    view = mLayoutInflater.inflate(R.layout.wechat_item_to_voice, null);
                    break;
                case WeChatMsgType.TIME_MSG:
                    view = mLayoutInflater.inflate(R.layout.wechat_item_time, null);
                    break;
            }

            viewHolder = new ViewHolder();
            viewHolder.contentTV = (TextView) view.findViewById(R.id.wechat_content);
            viewHolder.swipeLayout = (LinearLayout) view.findViewById(R.id.wechat_swipe_layout);
            viewHolder.delBtn = (ImageView) view.findViewById(R.id.wechat_swipe_btn);
            viewHolder.undoBtn = (ImageView) view.findViewById(R.id.wechat_undoswipe_btn);

            if(weChatMsgBean.getType() != WeChatMsgType.TIME_MSG){

                viewHolder.avatarIV = (ImageView) view.findViewById(R.id.wechat_avatarIV);
            }

            view.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder)view.getTag();
        }


        viewHolder.contentTV.setText(weChatMsgBean.getContent());

        // avatarIV is not null, can change it's avatar (->no the time msg)
        if(viewHolder.avatarIV != null)
        {
            viewHolder.avatarIV.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Intent itent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


                  //  lastSelectedAvatarPosition = position;

                    lastSelectedAvatar = viewHolder.avatarIV;

                    ((Activity)(mContext)).startActivityForResult(itent, LOAD_IMAGE_RESULT);

                    return false;
                }
            });
        }

        // if msg is voice, can adjust time length
        if(weChatMsgBean.getType() == WeChatMsgType.TO_VOICE
                || weChatMsgBean.getType() == WeChatMsgType.FROM_VOICE)
        {
           viewHolder.contentTV.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View view) {

                   Dialog dialog = new Dialog(mContext);
                   View dialog_layout = mLayoutInflater.inflate(R.layout.seekbar_dialog, null);
                   SeekBar seekBar = (SeekBar) dialog_layout.findViewById(R.id.seeKbar_seconds);

                   seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                       @Override
                       public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                           if(progress == 0) progress = 1;
                           viewHolder.contentTV.setText(progress+"''");
                           weChatMsgBean.setContent(progress+"''");
                       }

                       @Override
                       public void onStartTrackingTouch(SeekBar seekBar) {

                       }

                       @Override
                       public void onStopTrackingTouch(SeekBar seekBar) {

                       }
                   });

                   dialog.setContentView(dialog_layout);
                   dialog.setTitle("选择语音的时间长度");

                   dialog.show();

                   return false;
               }
           });
        }


        viewHolder.contentTV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {

                if(!focus){

                    weChatMsgBean.setContent(viewHolder.contentTV.getText().toString());
                }

            }
        });

        //if the time msg, can adjust the time (date)
        if(weChatMsgBean.getType() == WeChatMsgType.TIME_MSG){

              viewHolder.contentTV.setOnLongClickListener(new View.OnLongClickListener() {
                  @Override
                  public boolean onLongClick(View view) {

                      TimePickerDialog timePickerDialog =
                              new TimePickerDialog(mContext,new TimePickerDialog.OnTimeSetListener() {
                                  @Override
                                  public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                                      String content = convertTimeFormat(hour, minute);
                                      viewHolder.contentTV.setText(content);

                                      weChatMsgBean.setContent(content);
                                  }
                              },12, 12, true);

                      timePickerDialog.show();
                      return false;
                  }
              });

        }

        // swipe delete operation
        viewHolder.contentTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                    x1 = motionEvent.getX();
                    return false;
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    x2 = motionEvent.getX();

                   if(Math.abs(x2 - x1) > 20)
                   {
                       viewHolder.swipeLayout.setVisibility(View.VISIBLE);
                       viewHolder.contentTV.setAlpha(0.5f);
                   }
                }
                return false;
            }
        });


        // del:
        viewHolder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewHolder.swipeLayout.setVisibility(View.GONE);
                viewHolder.contentTV.setAlpha(1.0f);
                mData.remove(position);
                notifyDataSetChanged();

            }
        });


        // undo del
        viewHolder.undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewHolder.swipeLayout.setVisibility(View.GONE);
                viewHolder.contentTV.setAlpha(1.0f);
            }
        });


        return view;
    }


    private String convertTimeFormat(int hour, int minute){

        StringBuilder sb = new StringBuilder();
        if(hour >= 0 && hour<6) sb.append("凌晨");
        else if(hour >= 6 && hour < 12) sb.append("上午");
        else if(hour == 12) sb.append("中午");
        else if(hour > 12 && hour < 18) sb.append("下午");
        else sb.append("晚上");

        sb.append(hour);
        sb.append(":");
        sb.append(minute);

        return sb.toString();
    }

    public void setAvatar(String imagePath){


        lastSelectedAvatar.setImageBitmap(BitmapFactory.decodeFile(imagePath));
    }

   private static class ViewHolder{

       public TextView contentTV;
       public ImageView avatarIV;


       public LinearLayout swipeLayout;
       public ImageView delBtn;
       public ImageView undoBtn;

    }
}
