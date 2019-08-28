package com.project.weardrop.DTO;

import java.io.Serializable;

public class MemberDTO  implements Serializable {
    String userid, writer, userpw, email, admin;

    public MemberDTO() {}

    public MemberDTO(String userid, String writer, String userpw, String email, String admin) {
        this.userid = userid;
        this.writer = writer;
        this.userpw = userpw;
        this.email = email;
        this.admin = admin;
    }

    public MemberDTO(String userid, String writer, String email) {
        this.userid = userid;
        this.writer = writer;
        this.email = email;
    }

    public MemberDTO(String userid, String writer) {
        this.userid = userid;
        this.writer = writer;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getUserpw() {
        return userpw;
    }

    public void setUserpw(String userpw) {
        this.userpw = userpw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
