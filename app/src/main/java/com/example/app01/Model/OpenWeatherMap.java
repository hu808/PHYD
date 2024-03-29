package com.example.app01.Model;

import java.util.List;

/**
 * Created by reale on 05/10/2016.
 */

public class OpenWeatherMap {

    private List<Weather> weather;
    private String base;
    private  Main main;

    private int dt;
    private Sys sys;
    private int id;
    private String name;
    private int cod;

    public OpenWeatherMap() {
    }

    public OpenWeatherMap( List<Weather> weatherList, String base, Main main,int dt, Sys sys, int id, String name, int cod) {
        this.weather = weatherList;
        this.base = base;
        this.main = main;
        this.dt = dt;
        this.sys = sys;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }



    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }


    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
}
