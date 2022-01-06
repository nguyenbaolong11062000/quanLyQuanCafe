package com.mycompany.quancf;

import com.qcf.pojo.NhanVien;
import com.qcf.service.JdbcUtils;
import com.qcf.service.NhanVienService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable{
    @FXML private TextField password;
    @FXML private Button clickMe; 
    @FXML private Button login;
    @FXML private Button logout;
    @FXML private Button datBan;
    @FXML private Button datMon;
    @FXML private Button inHoaDon;
    @FXML private Label session;
    public static boolean online = false;
    public static boolean daDatBan = false;
    public static int maNv;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    private void switchToDatMon() throws IOException {
        App.setRoot("datmon");
    }
    
    @FXML
    private void switchToInHoaDon() throws IOException {
        App.setRoot("hoadon");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inHoaDon.setVisible(false);
        session.setVisible(online);
        logout.setVisible(online);
        switchLogin(false);
        clickMe.setVisible(!online);
        if(daDatBan){
            datBan.setVisible(false);
            datMon.setVisible(true);
        }
        else{
            datBan.setVisible(true);
            datMon.setVisible(false);
        }
        if(online){
            session.setText("Phiên làm việc của nhân viên số " + maNv);
            datBan.setVisible(online);
            datMon.setVisible(online);
            inHoaDon.setVisible(online);
        }
        
        login.setOnMouseClicked(e -> {
            try {
                Connection conn = JdbcUtils.getConn();
                NhanVienService nv = new NhanVienService(conn);
                List<NhanVien> lNv = nv.getNhanVien();
                List<Integer> a = new ArrayList<>();
                lNv.forEach(p -> {
                    a.add(p.getId());
                });
                a.forEach(b -> {
                    if(password.getText().equals("nv" + b)){
                        nv.updateNhanVienOnline(1, b);
                        Utils.getBox("Đăng nhập thành công ", Alert.AlertType.INFORMATION).show();
                        session.setText("Phiên làm việc của nhân viên số " + b);
                        maNv = b;
                        online = true;
                        datMon.setVisible(online);
                        logout.setVisible(online);
                        clickMe.setVisible(!online);
                        switchLogin(!online);
                        inHoaDon.setVisible(online);
                        session.setVisible(online);
                    }
                });
                if(!online)
                    Utils.getBox("Sai mật khẩu", Alert.AlertType.ERROR).show();
                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        logout.setOnMouseClicked(e -> {
            try {
                Connection conn = JdbcUtils.getConn();
                NhanVienService nv = new NhanVienService(conn);
                nv.updateNhanVienOnline(0, nv.getNhanVienOnline().getId());
                Utils.getBox("Hẹn gặp lại", Alert.AlertType.INFORMATION).show();
                online = false;
                session.setVisible(online);
                logout.setVisible(online);
                switchLogin(!online);
                datMon.setVisible(online);
                inHoaDon.setVisible(online);
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void switchLogin(boolean a){
        login.setVisible(a);
        password.setVisible(a);
    }
    
    @FXML
    private void showDangNhap(){
        login.setVisible(true);
        password.setVisible(true);
    }
    
    
}