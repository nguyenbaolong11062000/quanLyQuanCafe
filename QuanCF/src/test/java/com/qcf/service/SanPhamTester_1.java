/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcf.service;
import com.qcf.pojo.SanPham;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author Administrator
 */
public class SanPhamTester_1 {
    private static Connection conn;
    
    @BeforeAll
    public static void setUpClass() throws SQLException {
        conn = JdbcUtils.getConn();
    }
    
    @AfterAll
    public static void tearDownClass() {
        if(conn != null)
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamTester_1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testQuantity() throws SQLException{
        SPService sp = new SPService(conn);
        List<SanPham> rs = sp.getSanPham();
        
        Assertions.assertTrue(rs.size() >= 11);
        
    }
    
    @Test
    public void testGetTenSPById() throws SQLException{
        SPService sp = new SPService(conn);
        String tenSP = sp.getTenSanPhamById(1);
        
        Assertions.assertTrue(tenSP.equals("Cà phê đen"));
    }
    
    @Test
    public void testAddSP() throws SQLException{
        SPService sp = new SPService(conn);
        SanPham p = new SanPham();
        p.setTenSanPham("Bắp Xào");
        p.setGiaBan(new BigDecimal(10000));
        p.setTinhTrang(1);
        p.setThoiDiemBan("Sáng");
        boolean addSP = sp.addSanPham(p);
        
        Assertions.assertTrue(addSP);
    }
    
    @Test
    public void testTimKiemSP() throws SQLException{
        SPService sp = new SPService(conn);
        List<SanPham> rs = sp.getTenSanPham("Cà phê đen", "Sáng");
        SanPham p = new SanPham();
        rs.forEach(t -> {
            p.setTenSanPham(t.getTenSanPham());
            p.setGiaBan(t.getGiaBan());
            p.setTinhTrang(t.getTinhTrang());
            p.setThoiDiemBan(t.getThoiDiemBan());
        });
        Assertions.assertTrue(p.getTenSanPham().equals("Cà phê đen"));
    }
    
    @Test
    public void testGetTenSP() throws SQLException{
        SPService sp = new SPService(conn);
        List<SanPham> products = sp.getSanPham();
        products.forEach(p -> {
            if(p.getId() == 2)
                Assertions.assertTrue(p.getTenSanPham().contains("sữa"));
        });
    }
    
    @Test
    public void testGetThoiDiemSPSang() throws SQLException{
        SPService sp = new SPService(conn);
        List<SanPham> rs = sp.getThoiDiemSanPham("Sáng");
        rs.forEach(p -> {
            Assertions.assertTrue(p.getThoiDiemBan().equals("Sáng") || p.getThoiDiemBan().equals("Cả ngày"));
        });
    }
    
    @Test
    public void testGetThoiDiemSPCaNgay() throws SQLException{
        SPService sp = new SPService(conn);
        List<SanPham> rs = sp.getThoiDiemSanPham("Cả ngày");
        List<SanPham> full = sp.getSanPham();
        Assertions.assertTrue(rs.size() == full.size());
    }
    
    @Test
    public void testGetThoiDiemSPChieu() throws SQLException{
        SPService sp = new SPService(conn);
        List<SanPham> rs = sp.getThoiDiemSanPham("Chiều");
        rs.forEach(p -> {
            Assertions.assertTrue(p.getThoiDiemBan().equals("Chiều") || p.getThoiDiemBan().equals("Cả ngày"));
        });
    }
    
    @Test
    public void testGetGiaSanPham() throws SQLException{
        SPService sp = new SPService(conn);
        List<SanPham> rs = sp.getGiaSanPham(new BigDecimal(10000), "Cả ngày");
        rs.forEach(p ->{
            Assertions.assertTrue(p.getGiaBan().floatValue() <= new BigDecimal(10000).floatValue());
        });
    }
    
    //Khi test nhớ thay id sản phẩm
    @Test
    public void testXoaSanPham(){
        SPService sp = new SPService(conn);
        boolean rs = sp.xoaSanPham(9);
        Assertions.assertTrue(rs);
    }
    
    public void testXoaSanPhamFail(){
        SPService sp = new SPService(conn);
        boolean rs = sp.xoaSanPham(9);
        Assertions.assertFalse(rs);
    }
    
    //khi test nhớ thay id sản phẩm
    @Test
    public void testUpdateSanPham(){
        SPService sp = new SPService(conn);
        SanPham p = new SanPham();
        p.setId(3);
        p.setTenSanPham("Cà pháo");
        p.setGiaBan(new BigDecimal(17000));
        p.setTinhTrang(1);
        p.setThoiDiemBan("Chiều");
        boolean rs = sp.updateSanPham(p);
        Assertions.assertTrue(rs);
    }
}
