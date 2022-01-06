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
public class TongKet {

    /**
     * @return the thoiGian
     */
    public String getThoiGian() {
        return thoiGian;
    }

    /**
     * @param thoiGian the thoiGian to set
     */
    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
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
     * @return the nhanVienId
     */
    public int getNhanVienId() {
        return nhanVienId;
    }

    /**
     * @param nhanVienId the nhanVienId to set
     */
    public void setNhanVienId(int nhanVienId) {
        this.nhanVienId = nhanVienId;
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
    private int nhanVienId;
    private BigDecimal tongTien;
    private String thoiGian;
}
