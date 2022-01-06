/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcf.service;

import com.qcf.pojo.Ban;
import com.qcf.pojo.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
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
public class BanService {
    private Connection conn;
    
    public BanService(Connection conn){
        this.conn = conn;
    }
    
    public List<Ban> getBan() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM ban");
        
        List<Ban> ban = new ArrayList<>();
        while(rs.next()){
            Ban s = new Ban();
            s.setId(rs.getInt("id"));
            s.setSucChua(rs.getInt("SucChua"));
            s.setTinhTrang(rs.getInt("TinhTrang"));
            ban.add(s);
        }
        return ban;
    }
    
    public List<Ban> getSucChua(int sucChua) throws SQLException{
        String sql = "SELECT * FROM ban WHERE SucChua >= ?";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, sucChua);
        ResultSet rs = stm.executeQuery();
        
        
        List<Ban> ban = new ArrayList<>();
        while(rs.next()){
            Ban s = new Ban();
            s.setId(rs.getInt("id"));
            s.setSucChua(rs.getInt("SucChua"));
            s.setTinhTrang(rs.getInt("TinhTrang"));
            ban.add(s);
        }
        return ban;
    }
    
    public boolean updateBan(Ban p){
        try {
            String sql = "UPDATE ban SET SucChua = ?, TinhTrang = ? WHERE id = ?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, p.getSucChua());
            stm.setInt(2, p.getTinhTrang());
            stm.setInt(3, p.getId());
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean updateTinhTrangBan(int id){
        try {
            String sql = "UPDATE ban SET TinhTrang = ? WHERE id = ?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, 1);
            stm.setInt(2, id);
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean updateTinhTrangBanTrong(int id){
        try {
            String sql = "UPDATE ban SET TinhTrang = ? WHERE id = ?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, 0);
            stm.setInt(2, id);
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
