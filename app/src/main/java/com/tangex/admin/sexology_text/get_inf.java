package com.tangex.admin.sexology_text;

public class get_inf {

    public String mota;
    public String news_name;
    public String file;
    public String ima;
    public String title;
    public int stt;
    private String important;
    private int tile;
    private String type;

    public get_inf() {
    }

    public get_inf(String mota, String news_name, String file, String ima, String title, int stt, String important, int tile, String type) {
        this.mota = mota;
        this.news_name = news_name;
        this.file = file;
        this.ima = ima;
        this.title = title;
        this.stt = stt;
        this.important = important;
        this.tile = tile;
        this.type = type;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getNews_name() {
        return news_name;
    }

    public void setNews_name(String news_name) {
        this.news_name = news_name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getIma() {
        return ima;
    }

    public void setIma(String ima) {
        this.ima = ima;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public int getTile() {
        return tile;
    }

    public void setTile(int tile) {
        this.tile = tile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
