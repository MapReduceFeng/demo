package com.example.demo.system.entity;

public class ReqPage {
    private int pageCurrent;
    private int pageSize;

    public int getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "ReqPage{" +
                "pageCurrent=" + pageCurrent +
                ", pageSize=" + pageSize +
                '}';
    }
}
