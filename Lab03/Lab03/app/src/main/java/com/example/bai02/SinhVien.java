package com.example.bai02;

public class SinhVien {
    private int id;
    private String name;
    private String khoa;
    private String mssv;

    public SinhVien(String name, String khoa,String mssv) {
        this.name = name;
        this.khoa = khoa;
        this.mssv = mssv;
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
    public String getKhoa() {
        return khoa;
    }
    public String getMssv() {
        return mssv;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public void setMssv(String mssv) { this.mssv = mssv; }

    public SinhVien(int id, String name, String khoa, String mssv) {
        this.id = id;
        this.name = name;
        this.khoa = khoa;
        this.mssv = mssv;
    }

    @Override
    public String toString() {
        return "SinhVien{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mssv='" + mssv + '\'' +
                ", khoa='" + khoa + '\'' +
                '}';
    }
}
