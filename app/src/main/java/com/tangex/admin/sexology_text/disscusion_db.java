package com.tangex.admin.sexology_text;

import java.util.Date;

public class disscusion_db {
    private String tieude;
    private String noidung;
    private long thoigianDang;
    private String iduserDang = "";
    private String idBaiDang = "";
    private int nam =0;
    private int nu=0;
    private int lgbt=0;

    public disscusion_db() {
    }

    public disscusion_db(String tieude, String noidung, long thoigianDang, String iduserDang, String idBaiDang, int nam, int nu, int lgbt) {
        this.tieude = tieude;
        this.noidung = noidung;
        this.thoigianDang = thoigianDang;
        this.iduserDang = iduserDang;
        this.idBaiDang = idBaiDang;
        this.nam = nam;
        this.nu = nu;
        this.lgbt = lgbt;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public long getThoigianDang() {
        return thoigianDang;
    }

    public void setThoigianDang(long thoigianDang) {
        this.thoigianDang = thoigianDang;
    }

    public String getIduserDang() {
        return iduserDang;
    }

    public void setIduserDang(String iduserDang) {
        this.iduserDang = iduserDang;
    }

    public String getIdBaiDang() {
        return idBaiDang;
    }

    public void setIdBaiDang(String idBaiDang) {
        this.idBaiDang = idBaiDang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public int getNu() {
        return nu;
    }

    public void setNu(int nu) {
        this.nu = nu;
    }

    public int getLgbt() {
        return lgbt;
    }

    public void setLgbt(int lgbt) {
        this.lgbt = lgbt;
    }
}
