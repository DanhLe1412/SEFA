package com.tangex.admin.sexology_text;

public class Notification_Obj_Handle {
    private String NgayGanDay;
    private int TheoDoi;
    private int KinhNguyetCoDeu;
    private int SoNgayDeu;
    private int NuiDoiCangTuc;
    private int DauBungDuoi;
    private int DauLung;
    private int NoiNhieuMun;

    public Notification_Obj_Handle() {
    }

    public Notification_Obj_Handle(String ngayGanDay, int theoDoi, int kinhNguyetCoDeu, int soNgayDeu, int nuiDoiCangTuc, int dauBungDuoi, int dauLung, int noiNhieuMun) {
        NgayGanDay = ngayGanDay;
        TheoDoi = theoDoi;
        KinhNguyetCoDeu = kinhNguyetCoDeu;
        SoNgayDeu = soNgayDeu;
        NuiDoiCangTuc = nuiDoiCangTuc;
        DauBungDuoi = dauBungDuoi;
        DauLung = dauLung;
        NoiNhieuMun = noiNhieuMun;
    }

    public String getNgayGanDay() {
        return NgayGanDay;
    }

    public void setNgayGanDay(String ngayGanDay) {
        NgayGanDay = ngayGanDay;
    }

    public int getTheoDoi() {
        return TheoDoi;
    }

    public void setTheoDoi(int theoDoi) {
        TheoDoi = theoDoi;
    }

    public int getKinhNguyetCoDeu() {
        return KinhNguyetCoDeu;
    }

    public void setKinhNguyetCoDeu(int kinhNguyetCoDeu) {
        KinhNguyetCoDeu = kinhNguyetCoDeu;
    }

    public int getSoNgayDeu() {
        return SoNgayDeu;
    }

    public void setSoNgayDeu(int soNgayDeu) {
        SoNgayDeu = soNgayDeu;
    }

    public int getNuiDoiCangTuc() {
        return NuiDoiCangTuc;
    }

    public void setNuiDoiCangTuc(int nuiDoiCangTuc) {
        NuiDoiCangTuc = nuiDoiCangTuc;
    }

    public int getDauBungDuoi() {
        return DauBungDuoi;
    }

    public void setDauBungDuoi(int dauBungDuoi) {
        DauBungDuoi = dauBungDuoi;
    }

    public int getDauLung() {
        return DauLung;
    }

    public void setDauLung(int dauLung) {
        DauLung = dauLung;
    }

    public int getNoiNhieuMun() {
        return NoiNhieuMun;
    }

    public void setNoiNhieuMun(int noiNhieuMun) {
        NoiNhieuMun = noiNhieuMun;
    }
}
