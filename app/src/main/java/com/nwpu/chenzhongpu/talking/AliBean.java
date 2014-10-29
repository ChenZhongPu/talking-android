package com.nwpu.chenzhongpu.talking;

/**
 * Created by chenzhongpu on 10/25/14.
 */
public class AliBean {

    private int alitype;

    private String money;

    private String chat;

    public AliBean() {
    }

    public AliBean(int alitype, String money, String chat) {
        this.alitype = alitype;
        this.money = money;
        this.chat = chat;
    }

    public int getAlitype() {
        return alitype;
    }

    public void setAlitype(int alitype) {
        this.alitype = alitype;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}
