package com.nwpu.chenzhongpu.talking;

/**
 * Created by chenzhongpu on 10/23/14.
 */
public class FriendSayBean {

    private String name;
    private String content;

    public FriendSayBean() {
    }

    public FriendSayBean(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
