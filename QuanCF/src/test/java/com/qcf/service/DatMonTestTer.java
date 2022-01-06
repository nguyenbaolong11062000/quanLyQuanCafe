/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcf.service;

import com.qcf.pojo.BanSP;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class DatMonTestTer {
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
            Logger.getLogger(DatMonTestTer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testGetBanSPById() throws SQLException{
        BanSPService banSp = new BanSPService(conn);
        List<BanSP> listBanSP = banSp.getBanSPById(9);
        listBanSP.forEach(p -> {
            Assertions.assertTrue(p.getId() == 9);
        });
    }
    
    @Test
    public void testAddBanSP(){
        BanSPService banSp = new BanSPService(conn);
        boolean rs = banSp.addBanSP(2);
        Assertions.assertTrue(rs);
    }
    
    @Test
    public void testAddSPBan() throws SQLException{
        BanSPService banSp = new BanSPService(conn);
        boolean rs = banSp.addSPBan(2, 3, 0003, new BigDecimal(35000));
        Assertions.assertTrue(rs);
    }
    
    @Test
    public void testUpdateBanSPDaTinhTien(){
        BanSPService banSp = new BanSPService(conn);
        boolean rs = banSp.updateBanSPTinhTien(2);
        Assertions.assertTrue(rs);
    }
    
    @Test
    public void testUpdateBanSP(){
        BanSPService banSp = new BanSPService(conn);
        boolean rs = banSp.updateBanSP(2, 3, new BigDecimal(25000), 24);
        Assertions.assertTrue(rs);
    }
    
    @Test
    public void testTinhTienBanChuaCoMon() throws SQLException{
        BanSPService banSp = new BanSPService(conn);
        List<BanSP> listBanSP = banSp.getBanSPById(2);
        listBanSP.forEach(p -> {
            Assertions.assertNull(p.getTongTien());
        });
    }
}
