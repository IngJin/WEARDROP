package com.project.weardrop.DTO;

public class FreeDTO {
    private String id;
    private String title;
    private String writer;
    private String writedate;
    private String content;


    public FreeDTO(String id, String title, String writer, String writedate, String content) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.writedate = writedate;
        this.content = content;
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

}
