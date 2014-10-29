package com.nwpu.chenzhongpu.talking;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.wandoujia.ads.sdk.AdListener;
import com.wandoujia.ads.sdk.Ads;
import com.wandoujia.ads.sdk.loader.Fetcher;

import java.util.ArrayList;
import java.util.List;


public class TalkMain extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


   private View container;

   private ChatFragment chatFragment;

   private QQChatFragment qqChatFragment;

   private AliFragment aliFragment;

   private FriendFragment friendFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_main);


      /*  final Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if(msg.what == 0x11){


                    Ads.showAppWall(TalkMain.this, TalkUtil.ADLISTID);

                }
            }
        };*/



/*        new Thread(new Runnable() {
            @Override
            public void run() {

                if(TalkUtil.canShowAD(TalkMain.this)){

                    try {

                        Ads.init(TalkMain.this, TalkUtil.APPKEYID, TalkUtil.APPSECRETKEY);
                    }catch (Exception e){

                        Log.d("CZP", "init ad error");
                    }


                    Ads.preLoad(TalkMain.this, Fetcher.AdFormat.appwall, "APP", TalkUtil.ADLISTID,
                            new AdListener() {

                                @Override
                                public void onAdReady() {

                                }

                                @Override
                                public void onLoadFailure() {

                                }

                                @Override
                                public void onAdPresent() {

                                }

                                @Override
                                public void onAdDismiss() {

                                }
                            });
                    handler.sendEmptyMessage(0x11);
                }


            }
        }).start();*/

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        container = findViewById(R.id.container);

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();

        switch(position){

            case 0:
                chatFragment = ChatFragment.newInstance(position + 1);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, chatFragment)
                        .commit();
                break;
            case 1:
                qqChatFragment = QQChatFragment.newInstance(position + 1);
                fragmentManager.beginTransaction()
                        .replace(R.id.container,qqChatFragment)
                        .commit();
                break;
            case 2:
                aliFragment = AliFragment.newInstance(position + 1);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, aliFragment)
                        .commit();
                break;
            case 3:
                friendFragment = FriendFragment.newInstance(position + 1);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, friendFragment)
                        .commit();
                break;
            case 4:

                Intent intent = new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts("mailto","chenloveit@gmail.com",null));

                intent.putExtra(Intent.EXTRA_SUBJECT, "反馈意见-对话");

                startActivity(Intent.createChooser(intent, "反馈"));


                break;

            default:
                chatFragment = ChatFragment.newInstance(position + 1);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, chatFragment)
                        .commit();
                break;

        }



    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_wechat);
                break;
            case 2:
                mTitle = getString(R.string.title_qq);
                break;
            case 3:
                mTitle = getString(R.string.title_alipay);
                break;
            case 4:
                mTitle = getString(R.string.title_friend);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.talk_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    /*    int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }*/

        int id = item.getItemId();
        if(id == R.id.action_save){

            TalkUtil.saveScreenCap(container, this);
            Log.d("CZP","run save...");
            return true;

        }

        if(id == R.id.action_share){

            Intent intent = new Intent(Intent.ACTION_SEND);
            Uri u = Uri.fromFile(TalkUtil.saveScreenCap(container, this));

            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, u);

            Intent choose = Intent.createChooser(intent, getResources().getString(R.string.share_title));

            if(intent.resolveActivity(getPackageManager()) != null)
            {
                startActivity(choose);
            }else{

                Toast.makeText(this, getResources().getString(R.string.share_error), Toast.LENGTH_SHORT).show();
            }


            return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == WeChatAdapter.LOAD_IMAGE_RESULT
                && resultCode == RESULT_OK && data != null){

           String imagePath = TalkUtil.getImagePath(data.getData(), this);

           chatFragment.mWeChatAdapter.setAvatar(imagePath);
        }


        if(requestCode == QQAdapter.QQ_LOAD_IMAGE_RESULT
                && resultCode == RESULT_OK && data != null){

            String imagePath = TalkUtil.getImagePath(data.getData(), this);

            qqChatFragment.qqAdapter.setQQAvatar(imagePath);
        }

        if(requestCode == FriendFragment.FRIEND_REQUEST_IMG
                && resultCode == RESULT_OK && data != null){

            String imagePath = TalkUtil.getImagePath(data.getData(), this);

           friendFragment.userAvata.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        }

        if(requestCode == AliAdapter.ALI_IMAGE_REQUEST
                && resultCode == RESULT_OK && data != null){

            String imagePath = TalkUtil.getImagePath(data.getData(), this);

            aliFragment.aliAdapter.setAvatar(imagePath);

        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ChatFragment extends Fragment implements View.OnClickListener{

        private List<WeChatMsgBean> mData;

        private ListView mWeChatListView;
        WeChatAdapter mWeChatAdapter;

        private LinearLayout bottom_two_layout;
        private ImageView add_menu;
        private ImageView from_txt_menu;
        private ImageView from_voice_menu;
        private ImageView to_txt_menu;
        private ImageView to_voice_menu;
        private ImageView time_menu;

        private View rootView;

        private boolean isHiden = true;

        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.wechat_add_menu:
                    if(isHiden)
                    {
                        bottom_two_layout.setVisibility(View.VISIBLE);
                    }else{
                        bottom_two_layout.setVisibility(View.GONE);
                    }

                    isHiden = !isHiden;
                    break;
                case R.id.wechat_from_text_menu:
                    mData.add(WeChatMsgFactory.getChatMsgBean(WeChatAdapter.WeChatMsgType.FROM_TXT));
                    mWeChatAdapter.notifyDataSetChanged();
                    break;
                case R.id.wechat_from_voice_menu:
                    mData.add(WeChatMsgFactory.getChatMsgBean(WeChatAdapter.WeChatMsgType.FROM_VOICE));
                    mWeChatAdapter.notifyDataSetChanged();
                    break;
                case R.id.wechat_to_text_menu:
                    mData.add(WeChatMsgFactory.getChatMsgBean(WeChatAdapter.WeChatMsgType.TO_TXT));
                    mWeChatAdapter.notifyDataSetChanged();
                    break;
                case R.id.wechat_to_voice_menu:
                    mData.add(WeChatMsgFactory.getChatMsgBean(WeChatAdapter.WeChatMsgType.TO_VOICE));
                    mWeChatAdapter.notifyDataSetChanged();
                    break;
                case R.id.wechat_time_menu:
                    mData.add(WeChatMsgFactory.getChatMsgBean(WeChatAdapter.WeChatMsgType.TIME_MSG));
                    mWeChatAdapter.notifyDataSetChanged();
                    break;


            }

        }

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ChatFragment newInstance(int sectionNumber) {
            ChatFragment fragment = new ChatFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public ChatFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_talk_main, container, false);

            initView();

            setHasOptionsMenu(true);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((TalkMain) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }


        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

            inflater.inflate(R.menu.wechat_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            if(item.getItemId() == R.id.action_discard){

                Log.d("CZP","run discard action in fragment..");

                mData.clear();
                mWeChatAdapter.notifyDataSetChanged();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        private void initView(){

            mData = new ArrayList<WeChatMsgBean>();

            mData.add(new WeChatMsgBean(WeChatAdapter.WeChatMsgType.FROM_TXT,"用户名可以编辑，图像长按可以修改,:)"));
            mData.add(new WeChatMsgBean(WeChatAdapter.WeChatMsgType.FROM_VOICE,"6''"));
            mData.add(new WeChatMsgBean(WeChatAdapter.WeChatMsgType.TIME_MSG,"晚上20:18"));
            mData.add(new WeChatMsgBean(WeChatAdapter.WeChatMsgType.TO_TXT,"长按时间标签，长按语音数字可以修改:)"));
            mData.add(new WeChatMsgBean(WeChatAdapter.WeChatMsgType.TO_TXT,"右下方的加号按钮可以调出或隐藏菜单"));
            mData.add(new WeChatMsgBean(WeChatAdapter.WeChatMsgType.FROM_TXT,"顶部分别是清除、保存图片和分享按钮"));
            mWeChatListView = (ListView) rootView.findViewById(R.id.wechat_main);

            mWeChatAdapter = new WeChatAdapter(mData, getActivity());
            mWeChatListView.setAdapter(mWeChatAdapter);

            bottom_two_layout = (LinearLayout) rootView.findViewById(R.id.bottom_two_layout);

            add_menu = (ImageView) rootView.findViewById(R.id.wechat_add_menu);
            add_menu.setOnClickListener(this);

            from_txt_menu = (ImageView) rootView.findViewById(R.id.wechat_from_text_menu);
            from_txt_menu.setOnClickListener(this);

            from_voice_menu = (ImageView) rootView.findViewById(R.id.wechat_from_voice_menu);
            from_voice_menu.setOnClickListener(this);

            to_txt_menu = (ImageView) rootView.findViewById(R.id.wechat_to_text_menu);
            to_txt_menu.setOnClickListener(this);

            to_voice_menu = (ImageView) rootView.findViewById(R.id.wechat_to_voice_menu);
            to_voice_menu.setOnClickListener(this);

            time_menu = (ImageView) rootView.findViewById(R.id.wechat_time_menu);
            time_menu.setOnClickListener(this);

        }
    }


    public static class QQChatFragment extends Fragment implements View.OnClickListener{

        private List<WeChatMsgBean> mData;
        private ListView qqChatListView;

        QQAdapter qqAdapter;

        private View rootview;

        private Button bottom_btn;

        private LinearLayout bottom_layout_two;
        private boolean isHide = true;

        private ImageView qq_from;
        private ImageView qq_to;
        private ImageView qq_time;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static QQChatFragment newInstance(int sectionNumber){

            QQChatFragment qqChatFragment = new QQChatFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            qqChatFragment.setArguments(args);

            return qqChatFragment;
        }

        public QQChatFragment(){

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            rootview = inflater.inflate(R.layout.qq_fragment_main, container, false);

            initView();

            setHasOptionsMenu(true);
            return rootview;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

            inflater.inflate(R.menu.wechat_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            if(item.getItemId() == R.id.action_discard){

                mData.clear();
                qqAdapter.notifyDataSetChanged();

                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        private void initView(){

            mData = new ArrayList<WeChatMsgBean>();

            mData.add(new WeChatMsgBean(QQAdapter.QQMsgType.QQ_FROM,"用户名，对话框都可以编辑:)"));
            mData.add(new WeChatMsgBean(QQAdapter.QQMsgType.QQ_TIME,"上午08:42"));
            mData.add(new WeChatMsgBean(QQAdapter.QQMsgType.QQ_TO,"长按头像可以编辑，长按时间标签也可以修改:)"));
            mData.add(new WeChatMsgBean(QQAdapter.QQMsgType.QQ_TO,"最重要的事没说，点击右下方的语音按钮可以调出或隐藏菜单"));
            mData.add(new WeChatMsgBean(QQAdapter.QQMsgType.QQ_FROM,"清除、保存、分享就不多说了:)"));
            qqChatListView = (ListView) rootview.findViewById(R.id.qq_main);

            qqAdapter = new QQAdapter(mData, getActivity());

            qqChatListView.setAdapter(qqAdapter);


            bottom_btn = (Button) rootview.findViewById(R.id.qq_btn_menu);
            bottom_btn.setOnClickListener(this);

            bottom_layout_two = (LinearLayout) rootview.findViewById(R.id.qq_bottom_two);

            qq_from = (ImageView) rootview.findViewById(R.id.qq_fromtxt);
            qq_to = (ImageView) rootview.findViewById(R.id.qq_totxt);
            qq_time = (ImageView) rootview.findViewById(R.id.qq_time);

            qq_from.setOnClickListener(this);
            qq_to.setOnClickListener(this);
            qq_time.setOnClickListener(this);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((TalkMain) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.qq_btn_menu:
                    if(isHide){

                        bottom_layout_two.setVisibility(View.VISIBLE);
                    }else{
                        bottom_layout_two.setVisibility(View.GONE);
                    }

                    isHide = !isHide;
                    break;
                case R.id.qq_fromtxt:

                    mData.add(new WeChatMsgBean(QQAdapter.QQMsgType.QQ_FROM, "QQ消息"));
                    qqAdapter.notifyDataSetChanged();
                    break;
                case R.id.qq_totxt:
                    mData.add(new WeChatMsgBean(QQAdapter.QQMsgType.QQ_TO, "QQ消息"));
                    qqAdapter.notifyDataSetChanged();
                    break;
                case R.id.qq_time:
                    mData.add(new WeChatMsgBean(QQAdapter.QQMsgType.QQ_TIME, "08:27"));
                    qqAdapter.notifyDataSetChanged();
                    break;

            }

        }
    }



    public static class AliFragment extends Fragment implements View.OnClickListener{

        private static final String ARG_SECTION_NUMBER = "section_number";

        private View rootView;


        private ListView listView;
        AliAdapter aliAdapter;

        private List<AliBean> data;

        private LinearLayout bottom;

        private ImageView fromMenu;
        private ImageView toMenu;
        private ImageView timeMenu;
        private Button payBtn;


        private boolean isHide = true;

        @Override
        public void onClick(View view) {


            switch (view.getId()){

                case R.id.ali_btn_pay:
                    if(isHide){

                        bottom.setVisibility(View.VISIBLE);
                    }else{
                        bottom.setVisibility(View.GONE);
                    }

                    isHide = !isHide;
                    break;
                case R.id.ali_from_menu:

                    data.add(new AliBean(AliAdapter.AliMsgType.ALI_FROM,"100.00","保养你"));
                    aliAdapter.notifyDataSetChanged();
                    break;
                case R.id.ali_to_menu:
                    data.add(new AliBean(AliAdapter.AliMsgType.ALI_TO,"100.00","保养你"));
                    aliAdapter.notifyDataSetChanged();
                    break;
                case R.id.ali_time_menu:
                    data.add(new AliBean(AliAdapter.AliMsgType.ALI_TIME,"","2014-10-01"));
                    aliAdapter.notifyDataSetChanged();
                    break;


            }
        }

        public static AliFragment newInstance(int sectionNumber){

            AliFragment aliFragment = new AliFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            aliFragment.setArguments(args);

            return aliFragment;
        }

        public AliFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.alipay_fragment_main, container, false);
            setHasOptionsMenu(true);

            initView();
            return rootView;

        }


        private void initView(){

            data = new ArrayList<AliBean>();

            data.add(new AliBean(AliAdapter.AliMsgType.ALI_TIME, "","2014-06-09"));

            data.add(new AliBean(AliAdapter.AliMsgType.ALI_FROM,"520.00","可修改用户名，长按可以修改图像"));

            data.add(new AliBean(AliAdapter.AliMsgType.ALI_TO, "0.20","长按修改时间、对话框内容:)"));
            data.add(new AliBean(AliAdapter.AliMsgType.ALI_FROM,"10.00","点击付款按钮可调出或隐藏菜单"));
            data.add(new AliBean(AliAdapter.AliMsgType.ALI_TO,"13.14","清除、保存和分享,你懂的:)"));

            listView = (ListView) rootView.findViewById(R.id.alipay_main);

            aliAdapter = new AliAdapter(data, getActivity());

            listView.setAdapter(aliAdapter);

            bottom = (LinearLayout) rootView.findViewById(R.id.ali_bottom_two);

            payBtn = (Button) rootView.findViewById(R.id.ali_btn_pay);
            payBtn.setOnClickListener(this);

            fromMenu = (ImageView) rootView.findViewById(R.id.ali_from_menu);
            toMenu = (ImageView) rootView.findViewById(R.id.ali_to_menu);
            timeMenu = (ImageView) rootView.findViewById(R.id.ali_time_menu);

            fromMenu.setOnClickListener(this);
            toMenu.setOnClickListener(this);
            timeMenu.setOnClickListener(this);




        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

            inflater.inflate(R.menu.wechat_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }



        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            if(item.getItemId() == R.id.action_discard){

                data.clear();
                aliAdapter.notifyDataSetChanged();

                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((TalkMain) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }


    public static class FriendFragment extends  Fragment{

        private static final String ARG_SECTION_NUMBER = "section_number";


        private View view;

        private List<FriendReplyBean> data;

        private ListView friendListView;

        private ImageView menuBtn;


        FriendAdapter friendAdapter;


        ImageView userAvata;


        public static final int FRIEND_REQUEST_IMG = 4;

        public static FriendFragment newInstance(int sectionNumber){

            FriendFragment fragment = new FriendFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }

        public FriendFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.friend_fragment, container, false);

            initView();

            setHasOptionsMenu(true);
            return view;
        }


        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

            inflater.inflate(R.menu.wechat_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            if(item.getItemId() == R.id.action_discard){

                data.clear();
                friendAdapter.notifyDataSetChanged();

                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        private void initView(){

            friendListView = (ListView) view.findViewById(R.id.friend_comment_main);

            data = new ArrayList<FriendReplyBean>();

            data.add(new FriendReplyBean("阿朱","","头像可以长按编辑，用户名，内容，发送时间以及点赞也可以编辑哦."));
            data.add(new FriendReplyBean("灭绝师太","阿朱","点击右边评论按钮有惊喜:)"));
            data.add(new FriendReplyBean("阿紫","","清除，保存，分享神马就不介绍了"));
            friendAdapter = new FriendAdapter(data, getActivity());

            friendListView.setAdapter(friendAdapter);

            userAvata = (ImageView) view.findViewById(R.id.friend_avatarIV);

            userAvata.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Intent itent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    getActivity().startActivityForResult(itent,FRIEND_REQUEST_IMG);

                    return true;
                }
            });


            menuBtn = (ImageView) view.findViewById(R.id.friend_menu_btn);



            menuBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog dialog = new Dialog(getActivity());

                    dialog.setTitle("选择添加类型");
                    dialog.setCancelable(true);
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View menuView = inflater.inflate(R.layout.friend_menu_dialog, null);
                    dialog.setContentView(menuView);

                    ImageView sayMenu = (ImageView) menuView.findViewById(R.id.friend_say_menu);

                    ImageView replyMenu = (ImageView) menuView.findViewById(R.id.friend_reply_menu);

                    sayMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                                Log.d("czp","click the friend menu 1");

                            final Dialog sayDig = new Dialog(getActivity());
                            View replyView = LayoutInflater.from(getActivity()).inflate(R.layout.say_dialog, null);

                            final EditText from = (EditText) replyView.findViewById(R.id.friend_fromET);

                            final EditText content = (EditText) replyView.findViewById(R.id.friend_say_content);
                            Button btn = (Button) replyView.findViewById(R.id.say_btn);

                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(TextUtils.isEmpty(from.getText())
                                            || TextUtils.isEmpty(content.getText())){

                                        return;
                                    }else{

                                        data.add(new FriendReplyBean(from.getText().toString(),
                                                "",
                                                content.getText().toString()));

                                        friendAdapter.notifyDataSetChanged();

                                        sayDig.dismiss();
                                    }
                                }
                            });

                            sayDig.setTitle("对话-朋友圈");
                            sayDig.setContentView(replyView);
                            sayDig.show();
                        }
                    });

                    replyMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Log.d("czp","click the friend menu 1");

                            final Dialog replyDig = new Dialog(getActivity());
                            View replyView = LayoutInflater.from(getActivity()).inflate(R.layout.reply_dialog, null);

                            final EditText froming = (EditText) replyView.findViewById(R.id.friend_fromET);
                            final EditText toing = (EditText) replyView.findViewById(R.id.friend_toET);

                            final EditText content = (EditText) replyView.findViewById(R.id.friend_reply_content);

                            Button btn = (Button) replyView.findViewById(R.id.reply_btn);

                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(TextUtils.isEmpty(froming.getText())
                                            || TextUtils.isEmpty(toing.getText())
                                            || TextUtils.isEmpty(content.getText()))
                                    {
                                        return;
                                    }
                                    else{

                                        data.add(new FriendReplyBean(froming.getText().toString(),
                                                toing.getText().toString(),
                                                content.getText().toString()));

                                        friendAdapter.notifyDataSetChanged();

                                        replyDig.dismiss();
                                    }
                                }
                            });

                            replyDig.setTitle("对话-朋友圈");
                            replyDig.setContentView(replyView);
                            replyDig.show();
                           // dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });


        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((TalkMain) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }
}
