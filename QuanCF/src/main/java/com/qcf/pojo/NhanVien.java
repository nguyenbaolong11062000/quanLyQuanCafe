/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcf.pojo;

import java.sql.Date;

/**
 *
 * @author Administrator
 */
public class NhanVien {

    /**
     * @return the online
     */
    public int getOnline() {
        return online;
    }

    /**
     * @param online the online to set
     */
    public void setOnline(int online) {
        this.online = online;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the ten
     */
    public String getTen() {
        return ten;
    }

    /**
     * @param ten the ten to set
     */
    public void setTen(String ten) {
        this.ten = ten;
    }

    /**
     * @return the tuoi
     */
    public int getTuoi() {
        return tuoi;
    }

    /**
     * @param tuoi the tuoi to set
     */
    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    /**
     * @return the gioiTinh
     */
    public String getGioiTinh() {
        return gioiTinh;
    }

    /**
     * @param gioiTinh the gioiTinh to set
     */
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    private int id;
    private String ten;
    private int tuoi;
    private String gioiTinh;
    private int online;
}