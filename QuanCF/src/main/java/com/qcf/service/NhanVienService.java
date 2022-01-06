/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcf.service;

import com.qcf.pojo.NhanVien;
import com.qcf.pojo.SanPham;
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
public class NhanVienService {
    private Connection conn;
    
    public NhanVienService(Connection conn){
        this.conn = conn;
    }
    
    public List<NhanVien> getNhanVien() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM nhanvien");
        
        List<NhanVien> sp = new ArrayList<>();
        while(rs.next()){
            NhanVien s = new NhanVien();
            s.setId(rs.getInt("id"));
            s.setTen(rs.getString("Ten"));
            s.setTuoi(rs.getInt("Tuoi"));
            s.setGioiTinh(rs.getString("GioiTinh"));
            s.setOnline(rs.getInt("Online"));
            sp.add(s);
        }
        return sp;
    }
    
    public boolean updateNhanVienOnline(int a, int id){
        try {
            String sql = "UPDATE nhanvien SET Online = ? WHERE id = ?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, a);
            stm.setInt(2, id);
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SPService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public NhanVien getNhanVienOnline() throws SQLException{
        String sql = "SELECT * FROM nhanvien WHERE Online = 1";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        
        ResultSet rs = stm.executeQuery();
        NhanVien s = new NhanVien();
        while(rs.next()){
            s.setId(rs.getInt("id"));
            s.setTen(rs.getString("Ten"));
            s.setTuoi(rs.getInt("Tuoi"));
            s.setGioiTinh(rs.getString("GioiTinh"));
            s.setOnline(rs.getInt("Online"));
        }
        return s;
    }
}
