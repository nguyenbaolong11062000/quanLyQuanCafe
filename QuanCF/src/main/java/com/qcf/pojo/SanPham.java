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
public class SanPham {

    @Override
    public String toString() {
        return this.tenSanPham;
    }

    
    
    /**
     * @return the thoiDiemBan
     */
    public String getThoiDiemBan() {
        return thoiDiemBan;
    }

    /**
     * @param thoiDiemBan the thoiDiemBan to set
     */
    public void setThoiDiemBan(String thoiDiemBan) {
        this.thoiDiemBan = thoiDiemBan;
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
     * @return the tinhTrang
     */
    public int getTinhTrang() {
        return tinhTrang;
    }

    /**
     * @param tinhTrang the tinhTrang to set
     */
    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
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
     * @return the giaBan
     */
    public BigDecimal getGiaBan() {
        return giaBan;
    }

    /**
     * @param giaBan the giaBan to set
     */
    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    /**
     * @return the tinhTrang
     */
    
    private int id;
    private String tenSanPham;
    private BigDecimal giaBan;
    private int tinhTrang;
    private String thoiDiemBan;
}
