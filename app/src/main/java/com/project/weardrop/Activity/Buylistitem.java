package com.project.weardrop.Activity;

public class Buylistitem {
    private String id;
    private String title;
    private String writer;
    private String writedate;
    private String price;
    private String content;
    private String code;
    private String filepath;
    private String userid;


    public Buylistitem(String id, String title, String writer, String writedate, String price, String content, String code, String filepath, String userid){
        this.id=id;
        this.title=title;
        this.writer=writer;
        this.writedate=writedate;
        this.price=price;
        this.content=content;
        this.code=code;
        this.filepath=filepath;
        this.userid=userid;
    }

    public Buylistitem(String id, String title, String writer, String writedate, String price, String content, String code, String userid){
        this.id=id;
        this.title=title;
        this.writer=writer;
        this.writedate=writedate;
        this.price=price;
        this.content=content;
        this.code=code;
        this.userid=userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWritedate() {
        return writedate;
    }

    public void setWritedate(String writedate) {
        this.writedate = writedate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

}
