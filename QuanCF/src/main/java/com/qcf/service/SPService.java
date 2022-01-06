/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcf.service;

import com.qcf.pojo.SanPham;
import java.math.BigDecimal;
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
public class SPService {
    private Connection conn;
    
    public SPService(Connection conn){
        this.conn = conn;
    }
    
    public List<SanPham> getSanPham() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM sanpham");
        
        List<SanPham> sp = new ArrayList<>();
        while(rs.next()){
            SanPham s = new SanPham();
            s.setId(rs.getInt("id"));
            s.setGiaBan(rs.getBigDecimal("GiaBan"));
            s.setTenSanPham(rs.getString("TenSanPham"));
            s.setTinhTrang(rs.getInt("TinhTrang"));
            s.setThoiDiemBan(rs.getString("ThoiDiemBan"));
            sp.add(s);
        }
        return sp;
    }
    
    public String getTenSanPhamById(int id) throws SQLException{
        String temp = "mandeptrai";
        String sql = "SELECT TenSanPham FROM sanpham WHERE id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            temp = rs.getString("TenSanPham");
        }
        return temp;
    }
    
    public boolean addSanPham(SanPham p){
        try {
            String sql = "INSERT INTO sanpham(TenSanPham, GiaBan, TinhTrang, ThoiDiemBan) VALUE(?, ?, 1, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, p.getTenSanPham());
            stm.setBigDecimal(2, p.getGiaBan());
            stm.setString(3, p.getThoiDiemBan());
            
            int kq = stm.executeUpdate();
            
            return kq > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(SPService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public List<SanPham> getTenSanPham(String kw, String thoiDiem) throws SQLDataException, SQLException{
        if(kw == null)
            throw new SQLDataException();
        
        int a = 0;
        ArrayList<String> thoiDiem1 = new ArrayList<>();
        thoiDiem1.add("Sáng");
        thoiDiem1.add("Chiều");
        String sql = "SELECT * FROM sanpham WHERE TenSanPham like concat('%', ?, '%')";
        if(thoiDiem == thoiDiem1.get(0) || thoiDiem == thoiDiem1.get(1)){
            a = 1;
            sql = "SELECT * FROM sanpham WHERE (ThoiDiemBan like concat('%', ?, '%')"
                    + " OR ThoiDiemBan like concat('%', ?, '%')) AND "
                    + "TenSanPham like concat('%', ?, '%')";
        }
        PreparedStatement stm = this.conn.prepareStatement(sql);
        if(a == 1){
            stm.setString(1, thoiDiem);
            stm.setString(2, "Cả ngày");
            stm.setString(3, kw);
        }
        else if(a == 0)
            stm.setString(1, kw);
        
        ResultSet rs = stm.executeQuery();
        List<SanPham> products = new ArrayList<>();
        while(rs.next()){
            SanPham p = new SanPham();
            p.setId(rs.getInt("Id"));
            p.setTenSanPham(rs.getString("TenSanPham"));
            p.setGiaBan(rs.getBigDecimal("GiaBan"));
            p.setThoiDiemBan(rs.getString("ThoiDiemBan"));
            p.setTinhTrang(rs.getInt("TinhTrang"));
            
            products.add(p);
        }
        return products;
    }
    
    public List<SanPham> getThoiDiemSanPham(String kw) throws SQLDataException, SQLException{
        if(kw == null)
            throw new SQLDataException();
        int a = 0;
        ArrayList<String> thoiDiem = new ArrayList<>();
        thoiDiem.add("Sáng");
        thoiDiem.add("Chiều");
        String sql = "SELECT * FROM sanpham";
        if(kw == thoiDiem.get(0) || kw == thoiDiem.get(1)){
            a = 1;
            sql = "SELECT * FROM sanpham WHERE ThoiDiemBan like concat('%', ?, '%')"
                    + " OR ThoiDiemBan like concat('%', ?, '%')";
        }
        PreparedStatement stm = this.conn.prepareStatement(sql);
        if(a == 1){
            stm.setString(1, kw);
            stm.setString(2, "Cả ngày");
        }
        ResultSet rs = stm.executeQuery();;
        
        List<SanPham> products = new ArrayList<>();
        while(rs.next()){
            SanPham p = new SanPham();
            p.setId(rs.getInt("Id"));
            p.setTenSanPham(rs.getString("TenSanPham"));
            p.setGiaBan(rs.getBigDecimal("GiaBan"));
            p.setThoiDiemBan(rs.getString("ThoiDiemBan"));
            p.setTinhTrang(rs.getInt("TinhTrang"));
            
            products.add(p);
        }
        return products;
    }
    
    public List<SanPham> getGiaSanPham(BigDecimal gia, String thoiDiem) throws SQLException{
        if(gia == null)
            throw new SQLDataException();
        
        int a = 0;
        ArrayList<String> thoiDiem1 = new ArrayList<>();
        thoiDiem1.add("Sáng");
        thoiDiem1.add("Chiều");
        String sql = "SELECT * FROM sanpham WHERE GiaBan <= ?";
        if(thoiDiem == thoiDiem1.get(0) || thoiDiem == thoiDiem1.get(1)){
            a = 1;
            sql = "SELECT * FROM sanpham WHERE (ThoiDiemBan like concat('%', ?, '%')"
                    + " OR ThoiDiemBan like concat('%', ?, '%')) AND "
                    + "GiaBan <= ?";
        }
        PreparedStatement stm = this.conn.prepareStatement(sql);
        if(a == 1){
            stm.setString(1, thoiDiem);
            stm.setString(2, "Cả ngày");
            stm.setBigDecimal(3, gia);
        }
        else if(a == 0)
            stm.setBigDecimal(1, gia);
        
        ResultSet rs = stm.executeQuery();
        List<SanPham> products = new ArrayList<>();
        while(rs.next()){
            SanPham p = new SanPham();
            p.setId(rs.getInt("Id"));
            p.setTenSanPham(rs.getString("TenSanPham"));
            p.setGiaBan(rs.getBigDecimal("GiaBan"));
            p.setThoiDiemBan(rs.getString("ThoiDiemBan"));
            p.setTinhTrang(rs.getInt("TinhTrang"));
            
            products.add(p);
        }
        return products;
    }
    
    public boolean xoaSanPham(int productId){
        try {
            String sql = "DELETE FROM sanpham WHERE id=?";
            PreparedStatement stm = this.conn.prepareCall(sql);
            stm.setInt(1, productId);
            
            int row = stm.executeUpdate() ;
            return row > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(SPService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean updateSanPham(SanPham p){
        try {
            String sql = "UPDATE sanpham SET TenSanPham = ?, GiaBan = ?, TinhTrang = ?,"
                    + " ThoiDiemBan = ? WHERE id = ?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, p.getTenSanPham());
            stm.setBigDecimal(2, p.getGiaBan());
            stm.setInt(3, p.getTinhTrang());
            stm.setString(4, p.getThoiDiemBan());
            stm.setInt(5, p.getId());
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SPService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
