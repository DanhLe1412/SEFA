package com.tangex.admin.sexology_text;

public class ThongtinLienhe {
    private String image;
    private String sdt;

    public ThongtinLienhe() {
    }

    public ThongtinLienhe(String image, String sdt, String ten) {
        this.image = image;
        this.sdt = sdt;
        this.ten = ten;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    private String ten;
}
