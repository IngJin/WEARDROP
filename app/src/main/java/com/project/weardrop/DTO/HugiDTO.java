package com.project.weardrop.DTO;


public class HugiDTO {
    private int drawableId;
    private String title;
    private String writer;

    public HugiDTO(int drawableId, String title, String writer){
        this.drawableId = drawableId;
        this.title = title;
        this.writer = writer;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
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
}
