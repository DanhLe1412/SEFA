package com.tangex.admin.sexology_text;

public class Comment_db {
    private String iduserCmt = "";
    private String noidungCmt = "";
    private long thoigianCmt;

    public Comment_db() {
    }

    public Comment_db(String iduserCmt, String noidungCmt, long thoigianCmt) {
        this.iduserCmt = iduserCmt;
        this.noidungCmt = noidungCmt;
        this.thoigianCmt = thoigianCmt;
    }

    public String getIduserCmt() {
        return iduserCmt;
    }

    public void setIduserCmt(String iduserCmt) {
        this.iduserCmt = iduserCmt;
    }

    public String getNoidungCmt() {
        return noidungCmt;
    }

    public void setNoidungCmt(String noidungCmt) {
        this.noidungCmt = noidungCmt;
    }

    public long getThoigianCmt() {
        return thoigianCmt;
    }

    public void setThoigianCmt(long thoigianCmt) {
        this.thoigianCmt = thoigianCmt;
    }
}
