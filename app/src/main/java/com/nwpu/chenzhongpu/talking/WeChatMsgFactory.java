package com.nwpu.chenzhongpu.talking;

/**
 * Created by chenzhongpu on 10/20/14.
 */
public class WeChatMsgFactory {

    public static WeChatMsgBean getChatMsgBean(int i) {

        switch (i) {
            case WeChatAdapter.WeChatMsgType.FROM_TXT:
                return new WeChatMsgBean(WeChatAdapter.WeChatMsgType.FROM_TXT, "消息");
            case WeChatAdapter.WeChatMsgType.FROM_VOICE:
                return new WeChatMsgBean(WeChatAdapter.WeChatMsgType.FROM_VOICE, "5''");
            case WeChatAdapter.WeChatMsgType.TO_TXT:
                return new WeChatMsgBean(WeChatAdapter.WeChatMsgType.TO_TXT, "消息");
            case WeChatAdapter.WeChatMsgType.TO_VOICE:
                return new WeChatMsgBean(WeChatAdapter.WeChatMsgType.TO_VOICE, "5''");
            case WeChatAdapter.WeChatMsgType.TIME_MSG:
                return new WeChatMsgBean(WeChatAdapter.WeChatMsgType.TIME_MSG, "中午12:14");
            default:
                return null;
        }
    }
}
