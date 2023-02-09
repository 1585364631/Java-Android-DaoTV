package com.dao.daotv.function;

public class gbook_context {
    private String gbook_id,gbook_name,gbook_ip,gbook_time,gbook_reply_time,gbook_content,gbook_reply;

    public gbook_context(String gbook_id,String gbook_name,String gbook_ip,String gbook_time,String gbook_reply_time,String gbook_content,String gbook_reply){
        this.gbook_id = gbook_id;
        this.gbook_name = gbook_name;
        this.gbook_ip = gbook_ip;
        this.gbook_time = gbook_time;
        this.gbook_reply_time = gbook_reply_time;
        this.gbook_content = gbook_content;
        this.gbook_reply = gbook_reply;
    }

    public String getall(){
        return gbook_id + gbook_name + gbook_ip + gbook_time + gbook_reply_time + gbook_content +gbook_reply;
    }

    public String getGbook_content() {
        return gbook_content;
    }

    public String getGbook_id() {
        return gbook_id;
    }

    public String getGbook_ip() {
        return gbook_ip;
    }

    public String getGbook_name() {
        return gbook_name;
    }

    public String getGbook_reply() {
        return gbook_reply;
    }

    public String getGbook_reply_time() {
        return gbook_reply_time;
    }

    public String getGbook_time() {
        return gbook_time;
    }

}
