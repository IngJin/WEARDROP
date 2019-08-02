package com.project.weardrop.DTO;

/**
 * Created by LG on 2017-02-08.
 */

public class MyItemDTO {
    public String id;
    public String title;    // 제목
    public String content;  // 내용
    public String name;
    public String date;
    public String image1;
    public String uploadType;
    public String videoimage;

       public MyItemDTO(String id, String title, String content, String name, String date, String image1, String uploadType, String videoimage) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.name = name;
        this.date = date;
        this.image1 = image1;
        this.uploadType = uploadType;
        this.videoimage = videoimage;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public String getVideoimage() {
        return videoimage;
    }

    public void setVideoimage(String videoimage) {
        this.videoimage = videoimage;
    }
}
