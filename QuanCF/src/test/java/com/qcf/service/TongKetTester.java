/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcf.service;

import com.qcf.pojo.TongKet;
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
public class TongKetTester {
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
            Logger.getLogger(TongKetTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testGetTongKet() throws SQLException{
        TongKetService tk = new TongKetService(conn);
        List<TongKet> listTongKet = tk.getTongKet();
        
        Assertions.assertTrue(listTongKet.size() >= 1);
    }
}
