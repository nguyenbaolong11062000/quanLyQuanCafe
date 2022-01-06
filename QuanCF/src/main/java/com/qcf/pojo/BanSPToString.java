/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcf.pojo;

import java.math.BigDecimal;

/**
 *
 * @author Administrator
 */
public class BanSPToString {

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
     * @return the banId
     */
    public int getBanId() {
        return banId;
    }

    /**
     * @param banId the banId to set
     */
    public void setBanId(int banId) {
        this.banId = banId;
    }

    /**
     * @return the tenSanPham
     */
    public String getTenSanPham() {
        return tenSanPham;
    }

    /**
     * @param tenSanPham the tenSanPham to set
     */
    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    /**
     * @return the soLuong
     */
    public int getSoLuong() {
        return soLuong;
    }

    /**
     * @param soLuong the soLuong to set
     */
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    /**
     * @return the tongTien
     */
    public BigDecimal getTongTien() {
        return tongTien;
    }

    /**
     * @param tongTien the tongTien to set
     */
    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }
    private int id;
    private int banId;
    private String tenSanPham;
    private int soLuong;
    private BigDecimal tongTien;
}
