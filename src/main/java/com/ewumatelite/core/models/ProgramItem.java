package com.ewumatelite.core.models;
public class ProgramItem {
    private String code;
    private String name;
    private String track;
    public ProgramItem(String code, String name, String track) {
        this.code = code;
        this.name = name;
        this.track = track;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTrack() {
        return track;
    }
    public void setTrack(String track) {
        this.track = track;
    }
    @Override
    public String toString() {
        return name;
    }
}
