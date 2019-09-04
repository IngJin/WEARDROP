package com.project.weardrop.DTO;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.Date;

public class NoticeDTO implements Serializable {
    private String id, title, writer, content;
    private String writedate;

    public NoticeDTO() {
    }

    public NoticeDTO(String id, String title, String writer, String content, String writedate) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.writedate = writedate;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWritedate() { return writedate; }

    public void setWritedate(String writedate) { this.writedate = writedate; }
}