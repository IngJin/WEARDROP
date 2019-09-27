package com.project.weardrop.DTO;

import java.io.Serializable;

public class MirrorDTO  implements Serializable {
    String userid, time, weather;

    public MirrorDTO() {}

    public MirrorDTO(String userid, String time, String weather) {
        this.userid = userid;
        this.time = time;
        this.weather = weather;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

}
