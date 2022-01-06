/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcf.service;

import com.qcf.pojo.Ban;
import com.qcf.pojo.BanSP;
import com.qcf.pojo.SanPham;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class BanSPService {
    private Connection conn;
    
    public BanSPService(Connection conn){
        this.conn = conn;
    }
    
    public List<BanSP> getBanSP() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM bansanpham");
        
        List<BanSP> ban = new ArrayList<>();
        while(rs.next()){
            BanSP s = new BanSP();
            s.setId(rs.getInt("Id"));
            s.setBanId(rs.getInt("BanId"));
            s.setSanPhamId(rs.getInt("SanPhamId"));
            s.setSoLuong(rs.getInt("SoLuong"));
            s.setTongTien(rs.getBigDecimal("TongTien"));
            ban.add(s);
        }
        return ban;
    }
    
    public List<BanSP> getBanSPById(int id) throws SQLException{
        String sql = "SELECT * FROM bansanpham WHERE BanId = ? AND DaTinhTien = 1";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        
        List<BanSP> ban = new ArrayList<>();
        while(rs.next()){
            BanSP s = new BanSP();
            s.setId(rs.getInt("Id"));
            s.setBanId(rs.getInt("BanId"));
            s.setSanPhamId(rs.getInt("SanPhamId"));
            s.setSoLuong(rs.getInt("SoLuong"));
            s.setTongTien(rs.getBigDecimal("TongTien"));
            ban.add(s);
        }
        return ban;
    }
    
    public BanSP getLastBanSP() throws SQLException{
        String sql = "SELECT * FROM bansanpham ORDER BY ID DESC LIMIT 1";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        
        ResultSet rs = stm.executeQuery();
        BanSP p = new BanSP();
        while(rs.next()){
            p.setId(rs.getInt("Id"));
            p.setBanId(rs.getInt("BanId"));
            p.setSanPhamId(rs.getInt("SanPhamId"));
            p.setSoLuong(rs.getInt("SoLuong"));
            p.setTongTien(rs.getBigDecimal("TongTien"));
        }
        return p;
    }
    
    public boolean addBanSP(int id){
        try {
            String sql = "INSERT INTO bansanpham(BanId, SanPhamId, SoLuong, TongTien) VALUE(?, 1, null, null)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, id);
            
            int kq = stm.executeUpdate();
            
            return kq > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BanSPService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean addSPBan(int banId, int sanPhamId, int soLuong, BigDecimal tongTien) throws SQLException{
        String sql = "INSERT INTO bansanpham(BanId, SanPhamId, SoLuong, TongTien, DaTinhTien)"
                + " VALUE(?, ?, ?, ?, 1)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, banId);
        stm.setInt(2, sanPhamId);
        stm.setInt(3, soLuong);
        stm.setBigDecimal(4, tongTien);
            
        int kq = stm.executeUpdate();
        
        return kq>0;
    }
    
    public boolean updateBanSP(int sanPhamId, int soLuong, BigDecimal tongTien, int id){
        try {
            String sql = "UPDATE bansanpham SET SanPhamId = ?, SoLuong = ?, TongTien = ?,"
                    + " DaTinhTien = 1 WHERE id = ? ";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, sanPhamId);
            stm.setInt(2, soLuong);
            stm.setBigDecimal(3, tongTien);
            stm.setInt(4, id);
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BanSPService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean updateBanSPTinhTien(int BanId){
        try {
            String sql = "UPDATE bansanpham SET DaTinhTien = 0 WHERE BanId = ? ";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, BanId);
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BanSPService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
