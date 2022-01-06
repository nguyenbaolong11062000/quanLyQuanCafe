/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.quancf;

import com.qcf.pojo.BanSP;
import com.qcf.pojo.NhanVien;
import com.qcf.pojo.SanPham;
import com.qcf.service.BanSPService;
import com.qcf.service.JdbcUtils;
import com.qcf.service.NhanVienService;
import com.qcf.service.SPService;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Administrator
 */
public class DatMon implements Initializable{
    @FXML private ComboBox<String> cbThoiDiem;
    @FXML private ComboBox<String> cbHinhThuc;
    @FXML private Label lbNhapTen;
    @FXML private Label lbNhapGia;
    @FXML private TableView<SanPham> tbSanPham;
    @FXML private TextField txtKeyword;
    @FXML private TextField txtGia;
    @FXML private TextField themTen;
    @FXML private TextField themGia;
    @FXML private ComboBox<String> themThoiDiem;
    @FXML private ComboBox<String> themTinhTrang;
    @FXML private ComboBox<String> cbHinhThucChinhSua;
    @FXML private Button btThemSanPham;
    @FXML private Button btCapNhat;
    @FXML private Button btThemMon;
    @FXML private TextField chonTenSP;
    @FXML private TextField chonSoLuongSP;
    @FXML private Label lbHinhThucChinhSua;
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkOnline(PrimaryController.online);
        
        cbThoiDiem.getItems().addAll("Cả ngày", "Sáng", "Chiều");
        cbHinhThuc.getItems().addAll("Theo tên", "Theo giá");
        themThoiDiem.getItems().addAll("Cả ngày", "Sáng", "Chiều");
        themTinhTrang.getItems().addAll("0", "1");
        cbHinhThucChinhSua.getItems().addAll("Thêm sản phẩm", "Cập nhật sản phẩm");
        loadColumns();
        
        try {
            loadProducts("", cbThoiDiem.getValue());
        } catch (SQLException ex) {
            Logger.getLogger(DatMon.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lbNhapTen.setVisible(false);
        txtKeyword.setVisible(false);
        lbNhapGia.setVisible(false);
        txtGia.setVisible(false);
        btThemSanPham.setVisible(false);
        btCapNhat.setVisible(false);
        
        cbHinhThuc.valueProperty().addListener((obj) -> {
            if(cbHinhThuc.getValue() == "Theo tên")
                switchHinhThuc(true);
            else if(cbHinhThuc.getValue() == "Theo giá")
                switchHinhThuc(false);
        });
        
        cbHinhThucChinhSua.valueProperty().addListener(obj -> {
            if(cbHinhThucChinhSua.getValue() == "Thêm sản phẩm")
                switchHinhThucChinhSua(true);
            else
                switchHinhThucChinhSua(false);
        });
            

        cbThoiDiem.valueProperty().addListener((obj) -> {
            try {
                loadTheoThoiDiem(cbThoiDiem.getValue());
            } catch (SQLException ex) {
                Logger.getLogger(DatMon.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        txtKeyword.textProperty().addListener((obj) -> {
            try {
                loadProducts(txtKeyword.getText(), cbThoiDiem.getValue());
            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        txtGia.textProperty().addListener((obj) -> {
            if(txtGia.getText().length() > 0)
            {
                try {
                    loadTheoGia(new BigDecimal(txtGia.getText()), cbThoiDiem.getValue());
                } catch (SQLException ex) {
                    Logger.getLogger(DatMon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
                try {
                    loadProducts("", cbThoiDiem.getValue());
            } catch (SQLException ex) {
                Logger.getLogger(DatMon.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.tbSanPham.setRowFactory(obj -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(e ->{
                SanPham p = this.tbSanPham.getSelectionModel().getSelectedItem();
                chonTenSP.setText(p.getTenSanPham());
                themTen.setText(p.getTenSanPham());
                themGia.setText((p.getGiaBan()).toString());
                themThoiDiem.getSelectionModel().select(p.getThoiDiemBan());
                themTinhTrang.getSelectionModel().select(p.getTinhTrang());
            });
           
            return row;
        });
    };
    
    private void checkOnline(boolean a){
        lbHinhThucChinhSua.setVisible(a);
        cbHinhThucChinhSua.setVisible(a);
        themTen.setVisible(a);
        themGia.setVisible(a);
        themThoiDiem.setVisible(a);
        themTinhTrang.setVisible(a);
        btThemSanPham.setVisible(a);
        btCapNhat.setVisible(a);
        btThemMon.setVisible(!a);
        chonTenSP.setVisible(!a);
        chonSoLuongSP.setVisible(!a);
    }
    
    private void switchHinhThuc(boolean a){
        lbNhapTen.setVisible(a);
        txtKeyword.setVisible(a);
        lbNhapGia.setVisible(!a);
        txtGia.setVisible(!a);
    }
    
    private void switchHinhThucChinhSua(boolean a){
        btThemSanPham.setVisible(a);
        btCapNhat.setVisible(!a);
    }
    
    public void updateSanPham() throws SQLException{
        SanPham p = this.tbSanPham.getSelectionModel().getSelectedItem();
        p.setTenSanPham(themTen.getText());
        p.setGiaBan(new BigDecimal(themGia.getText()));
        p.setTinhTrang(Integer.parseInt(themTinhTrang.getValue()));
        p.setThoiDiemBan(themThoiDiem.getValue());
        
        Connection conn = JdbcUtils.getConn();
        
        SPService s = new SPService(conn);
        if(s.updateSanPham(p)){
            Utils.getBox("Đã cập nhật món thành công", Alert.AlertType.INFORMATION).show();
            loadProducts("", "Cả ngày");
            themTen.setText("");
            themGia.setText("");
        }
        else
            Utils.getBox("Cập nhật thất bại", Alert.AlertType.ERROR).show();
        conn.close();
    }
    
    public void addSanPham(ActionEvent evt){
        try {
            Connection conn = JdbcUtils.getConn();
            SPService p = new SPService(conn);
            
            SanPham pro = new SanPham();
            pro.setTenSanPham(themTen.getText());
            pro.setGiaBan(new BigDecimal(themGia.getText()));
            pro.setThoiDiemBan(themThoiDiem.getValue());
            
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            if(p.addSanPham(pro)){
                a.setContentText("Đã thêm món thành công");
                loadProducts("", "Cả ngày");
                
            }
            else
                a.setContentText("Thêm thất bại");
            
            a.show();
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadColumns(){
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("Id"));
        
        TableColumn colTen = new TableColumn("Ten san pham");
        colTen.setCellValueFactory(new PropertyValueFactory("TenSanPham"));
        
        TableColumn colGiaBan = new TableColumn("Gia ban");
        colGiaBan.setCellValueFactory(new PropertyValueFactory("GiaBan"));
        
        TableColumn colTinhTrang = new TableColumn("Tinh trang");
        colTinhTrang.setCellValueFactory(new PropertyValueFactory("TinhTrang"));
        
        TableColumn colThoiDiem = new TableColumn("Thoi diem ban");
        colThoiDiem.setCellValueFactory(new PropertyValueFactory("ThoiDiemBan"));
        
        TableColumn colXoaSP = new TableColumn();
        colXoaSP.setCellFactory(obj -> {
            Button btn = new Button("Xoa");
            
            btn.setOnAction(evt ->{
                Utils.getBox("Bạn có chắc chắn xóa không ?", Alert.AlertType.CONFIRMATION)
                        .showAndWait().ifPresent(b -> {
                            if(b == ButtonType.OK){
                                Button bt = (Button)evt.getSource();
                                TableCell cell = (TableCell)bt.getParent();
                                SanPham q = (SanPham)cell.getTableRow().getItem();
                                
                                Connection conn;
                                try {
                                    conn = JdbcUtils.getConn();
                                    SPService s = new SPService(conn);
                                    
                                    if(s.xoaSanPham(q.getId())){
                                        Utils.getBox("Xóa món thành công", Alert.AlertType.INFORMATION).show();
                                        loadProducts("", cbThoiDiem.getValue());
                                    }
                                    else
                                        Utils.getBox("Xóa món thất bại", Alert.AlertType.ERROR).show();
                                    conn.close();
                                } catch (SQLException ex) {
                                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                });
            });
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });
        
        if(PrimaryController.online)
            this.tbSanPham.getColumns().addAll(colId, colTen, colGiaBan, colTinhTrang,
                colThoiDiem, colXoaSP);
        else
            this.tbSanPham.getColumns().addAll(colId, colTen, colGiaBan, colTinhTrang,
                colThoiDiem);
    }
    
    @FXML
    private void themMon() throws SQLException{
        SanPham s = this.tbSanPham.getSelectionModel().getSelectedItem();
        Connection conn = JdbcUtils.getConn();
        BanSPService bSP = new BanSPService(conn);
        BanSP temp = bSP.getLastBanSP();
        int sL = Integer.parseInt(chonSoLuongSP.getText());
        BigDecimal tongTien = new BigDecimal(new BigDecimal(sL).floatValue() * s.getGiaBan().floatValue());
        if(temp.getTongTien() == null){
            bSP.updateBanSP(s.getId(), sL, tongTien, temp.getId());
            Utils.getBox("Đặt món thành công", Alert.AlertType.INFORMATION).show();
            PrimaryController.daDatBan = false;
        }
        else{
            bSP.addSPBan(temp.getBanId(), s.getId(), sL, tongTien);
            Utils.getBox("Đặt món thành công", Alert.AlertType.INFORMATION).show();
            PrimaryController.daDatBan = false;
        }
        conn.close();
            
    }
    
    private void loadProducts(String kw, String thoiDiem) throws SQLException{
        if(kw != null){
            Connection conn = JdbcUtils.getConn();

            SPService s = new SPService(conn);

            tbSanPham.setItems(FXCollections.observableArrayList(s.getTenSanPham(kw, thoiDiem)));

            conn.close();
        }
    }
    
    private void loadTheoThoiDiem(String kw) throws SQLException{
        
        Connection conn = JdbcUtils.getConn();

        SPService s = new SPService(conn);

        tbSanPham.setItems(FXCollections.observableArrayList(s.getThoiDiemSanPham(kw)));

        conn.close();
    }
    
    private void loadTheoGia(BigDecimal gia, String thoiDiem) throws SQLException{
        Connection conn = JdbcUtils.getConn();

        SPService s = new SPService(conn);

        tbSanPham.setItems(FXCollections.observableArrayList(s.getGiaSanPham(gia, thoiDiem)));

        conn.close();
    }
}
