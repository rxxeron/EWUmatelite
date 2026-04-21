package com.ewumatelite.core.models;
public class SectionItem {
    private String cid;
    private String sectionNumber;
    public SectionItem(String cid, String sectionNumber) {
        this.cid = cid;
        this.sectionNumber = sectionNumber;
    }
    public String getCid() {
        return cid;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }
    public String getSectionNumber() {
        return sectionNumber;
    }
    public void setSectionNumber(String sectionNumber) {
        this.sectionNumber = sectionNumber;
    }
    @Override
    public String toString() {
        return "Section " + sectionNumber;
    }
}
