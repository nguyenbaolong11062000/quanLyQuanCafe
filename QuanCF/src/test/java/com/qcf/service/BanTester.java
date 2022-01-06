/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcf.service;

import com.qcf.pojo.Ban;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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
public class BanTester {
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
            Logger.getLogger(BanTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testGetSucChua() throws SQLException{
        BanService b = new BanService(conn);
        List<Ban> listBan = b.getSucChua(5);
        
        listBan.forEach(p -> {
            Assertions.assertTrue(p.getSucChua() >= 5);
        });
    }
    
    @Test
    public void testGetSucChuaQuaLon() throws SQLException{
        BanService b = new BanService(conn);
        List<Ban> listBan = b.getSucChua(999);
        Assertions.assertTrue(listBan.size() < 1);
    }
    
    @Test
    public void testUpdateBan() throws SQLException{
        BanService b = new BanService(conn);
        Ban ban = new Ban();
        ban.setId(3);
        ban.setSucChua(7);
        ban.setTinhTrang(1);
        boolean listBan = b.updateBan(ban);
        
        Assertions.assertTrue(listBan);
    }
    
    //khi test nhớ thay id
    @Test
    public void testUpdateTinhTrangBan(){
        BanService b = new BanService(conn);
        boolean ban = b.updateTinhTrangBanTrong(1);
        Assertions.assertTrue(ban);
    }
    
    @Test
    public void testUpdateTinhTrangBanFail(){
        BanService b = new BanService(conn);
        boolean ban = b.updateTinhTrangBanTrong(999);
        Assertions.assertFalse(ban);
    }
}
