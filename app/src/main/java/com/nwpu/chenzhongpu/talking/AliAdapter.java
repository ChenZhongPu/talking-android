package com.nwpu.chenzhongpu.talking;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import javax.xml.transform.Templates;

/**
 * Created by chenzhongpu on 10/24/14.
 */
public class AliAdapter extends BaseAdapter{


    public static interface AliMsgType{

        public int ALI_FROM = 0;
        public int ALI_TO = 1;
        public int ALI_TIME = 2;

    }

    private List<AliBean> mData;

    private Context mContext;

    private LayoutInflater mInflater;

    private ImageView selectedIV;

    public static final int ALI_IMAGE_REQUEST = 6;

    public static final String MONEY_FORMAT = " <big>%s</big>元<br/>%s";

    public AliAdapter(List<AliBean> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;

        this.mInflater = LayoutInflater.from(mContext);
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        final AliBean aliBean = mData.get(i);
        final ViewHolder viewHolder;

        if(view == null){

            switch (aliBean.getAlitype()){

                case AliMsgType.ALI_FROM:
                    view = mInflater.inflate(R.layout.ali_item_from, null);
                    break;
                case AliMsgType.ALI_TO:
                    view = mInflater.inflate(R.layout.ali_item_to, null);
                    break;
                case AliMsgType.ALI_TIME:
                    view = mInflater.inflate(R.layout.ali_item_time, null);
                    break;
            }

            viewHolder = new ViewHolder();
            viewHolder.tv = (TextView) view.findViewById(R.id.alichat_money);

            if(aliBean.getAlitype() != AliMsgType.ALI_TIME){

                viewHolder.imageView = (ImageView) view.findViewById(R.id.ali_avatarIV);
            }

            view.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder) view.getTag();

        }


        if(aliBean.getAlitype() != AliMsgType.ALI_TIME){

            String html = String.format(MONEY_FORMAT, aliBean.getMoney(), aliBean.getChat());

            Spanned spanned = Html.fromHtml(html);

            viewHolder.tv.setText(spanned);

            // not the time msg, can change the avatar:
            viewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Intent itent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    selectedIV = viewHolder.imageView;

                    ((Activity)(mContext)).startActivityForResult(itent, ALI_IMAGE_REQUEST);
                    return true;
                }
            });


            // change the content

           viewHolder.tv.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View view) {

                   final Dialog dialog = new Dialog(mContext);

                   final View diaView = mInflater.inflate(R.layout.ali_money_dialog, null);

                   dialog.setTitle("编辑");
                   dialog.setContentView(diaView);

                   dialog.show();

                   final EditText money = (EditText) diaView.findViewById(R.id.ali_money);
                   final EditText txt = (EditText) diaView.findViewById(R.id.ali_money_txt);

                   Button ok = (Button) diaView.findViewById(R.id.ali_input_ok);

                   ok.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {

                           aliBean.setMoney(money.getText().toString());
                           aliBean.setChat(txt.getText().toString());

                           notifyDataSetChanged();

                           dialog.dismiss();
                       }
                   });

                   return true;
               }
           });

        }else{

            viewHolder.tv.setText(aliBean.getChat());

            viewHolder.tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {


                    Calendar calendar = Calendar.getInstance();

                    DatePickerDialog datePickerDialog =
                            new DatePickerDialog(mContext,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                                aliBean.setChat(convertDate(year, month + 1, day));
                                                notifyDataSetChanged();

                                        }
                                    },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));


                    datePickerDialog.show();

                    return true;
                }
            });
        }

        return view;
    }


    public void setAvatar(String path){

        selectedIV.setImageBitmap(BitmapFactory.decodeFile(path));

    }

    private String convertDate(int year, int month, int day){

        StringBuilder sb = new StringBuilder();

        sb.append(year);
        sb.append("-");

        if(month < 10){

            sb.append("0");
        }
        sb.append(month);

        sb.append("-");
        if(day < 10){

            sb.append("0");
        }

        sb.append(day);

        return sb.toString();

    }

    private static class ViewHolder{

        public TextView tv;

        public ImageView imageView;


    }
}
