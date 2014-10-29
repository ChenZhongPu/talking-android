package com.nwpu.chenzhongpu.talking;

/**
 * Created by chenzhongpu on 10/19/14.
 */
public class WeChatMsgBean {

    private int type;
    private String content;


    public WeChatMsgBean() {
    }

    public WeChatMsgBean(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
