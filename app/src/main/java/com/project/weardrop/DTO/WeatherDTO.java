package com.project.weardrop.DTO;

public class WeatherDTO {

  String Temperature, weather, name;

    public WeatherDTO(String weather, String Temperature, String name) {
    //    Temperature = temperature;
        this.weather = weather;
        this.Temperature = Temperature;
        this.name = name;
    }


    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
