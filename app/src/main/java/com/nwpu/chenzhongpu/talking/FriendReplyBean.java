package com.nwpu.chenzhongpu.talking;

/**
 * Created by chenzhongpu on 10/23/14.
 */
public class FriendReplyBean {


    private String from;
    private String to;

    private String content;

    public FriendReplyBean() {
    }

    public FriendReplyBean(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
