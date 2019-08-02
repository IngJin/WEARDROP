package com.project.weardrop.DTO;

public class GalleryDTO {
    //일단 요것만 적어둠
    private String filepath;
    private String id;
    private String writer;
    private String title;
    private String content;
    private String readcnt;
    private String writedate;

    public GalleryDTO(String id, String writer, String title, String content, String readcnt, String filepath, String writedate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.readcnt = readcnt;
        this.filepath = filepath;
        this.writedate = writedate;
    }

    public String getWritedate() {
        return writedate;
    }

    public void setWritedate(String writedate) {
        this.writedate = writedate;
    }

    public String getFilepath() {

        return filepath;
    }

    public void setFilepath(String filepath) {

        this.filepath = filepath;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getWriter() {

        return writer;
    }

    public void setWriter(String writer) {

        this.writer = writer;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {

        this.content = content;
    }

    public String getReadcnt() {

        return readcnt;
    }

    public void setReadcnt(String readcnt) {

        this.readcnt = readcnt;
    }
}
