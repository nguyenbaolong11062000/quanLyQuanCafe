/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcf.service;

import com.qcf.pojo.NhanVien;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author Administrator
 */
public class NhanVienTester {
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
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testDangNhap() throws SQLException{
        NhanVienService nvS = new NhanVienService(conn);
        List<NhanVien> listNv = nvS.getNhanVien();
        String pw = "nv01";
        listNv.forEach(p ->{
            if(pw == "nv" + p.getId())
                Assertions.assertTrue(true);
        });
    }
    
    @Test
    public void testDangNhapFail() throws SQLException{
        NhanVienService nvS = new NhanVienService(conn);
        List<NhanVien> listNv = nvS.getNhanVien();
        String pw = "nv01";
        listNv.forEach(p ->{
            Assertions.assertFalse(pw == "nv" + p.getId());
        });
    }
    
    @Test
    public void testUpdateNhanVienOnline(){
        NhanVienService nvS = new NhanVienService(conn);
        boolean s = nvS.updateNhanVienOnline(1, 1);
        Assertions.assertTrue(s);
    }
    
    @Test
    public void testUpdateNhanVienOffline(){
        NhanVienService nvS = new NhanVienService(conn);
        boolean s = nvS.updateNhanVienOnline(0, 1);
        
        Assertions.assertTrue(s);
    }
    
    @Test
    public void testGetNhanVienOnline() throws SQLException{
        NhanVienService nvS = new NhanVienService(conn);
        NhanVien rs = nvS.getNhanVienOnline();
        
        Assertions.assertTrue(rs.getOnline() == 1);
    }
    
    @Test
    public void testOnlyNhanVienOnline() throws SQLException{
        NhanVienService nvS = new NhanVienService(conn);
        NhanVien rs = nvS.getNhanVienOnline();
        
        boolean temp = nvS.updateNhanVienOnline(0, rs.getId());
        NhanVien rs1 = nvS.getNhanVienOnline();
        Assertions.assertFalse(rs1.getId() == 1);
    }
}
