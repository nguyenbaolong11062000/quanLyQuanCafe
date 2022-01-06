/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcf.service;

import com.qcf.pojo.SanPham;
import com.qcf.pojo.TongKet;
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
public class TongKetService {
    private Connection conn;
    
    public TongKetService(Connection conn){
        this.conn = conn;
    }
    
    public List<TongKet> getTongKet() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM tongket");
        
        List<TongKet> sp = new ArrayList<>();
        while(rs.next()){
            TongKet s = new TongKet();
            s.setId(rs.getInt("id"));
            s.setBanId(rs.getInt("BanId"));
            s.setNhanVienId(rs.getInt("NhanVienId"));
            s.setTongTien(rs.getBigDecimal("TongTien"));
            s.setThoiGian(rs.getString("ThoiGian"));
            sp.add(s);
        }
        return sp;
    }
    
    public boolean addTongKet(TongKet p){
        try {
            String sql = "INSERT INTO tongket(BanId, NhanVienId, TongTien, ThoiGian) VALUE(?, ?, ?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, p.getBanId());
            stm.setInt(2, p.getNhanVienId());
            stm.setBigDecimal(3, p.getTongTien());
            stm.setString(4, p.getThoiGian());
            
            int kq = stm.executeUpdate();
            
            return kq > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TongKetService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
