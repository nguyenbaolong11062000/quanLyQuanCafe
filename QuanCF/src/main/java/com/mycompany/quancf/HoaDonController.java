/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.quancf;

import com.qcf.pojo.Ban;
import com.qcf.pojo.BanSP;
import com.qcf.pojo.BanSPToString;
import com.qcf.pojo.SanPham;
import com.qcf.pojo.TongKet;
import com.qcf.service.BanSPService;
import com.qcf.service.BanService;
import com.qcf.service.JdbcUtils;
import com.qcf.service.SPService;
import com.qcf.service.TongKetService;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime; 
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

/**
 *
 * @author Administrator
 */
public class HoaDonController implements Initializable{
    @FXML private TableView<Ban> tbBan;
    @FXML private TableView<BanSPToString> tbBanSP;
    @FXML private Button inHoaDon;
    @FXML private Label lbTongTien;
    private int maBan;
    BigDecimal tongTien = new BigDecimal(0);
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadColumnsBan();
        try {
            loadBan();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadColumnsBanSP();
        
        inHoaDon.setOnMouseClicked(e -> {
            try {
                Connection conn = JdbcUtils.getConn();
                BanService banSv = new BanService(conn);
                
            } catch (SQLException ex) {
                Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
    }
    
    private void loadColumnsBan(){
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("Id"));
        
        TableColumn colSucChua = new TableColumn("Suc chua");
        colSucChua.setCellValueFactory(new PropertyValueFactory("SucChua"));
        
        TableColumn colTinhTrang = new TableColumn("Tinh trang");
        colTinhTrang.setCellValueFactory(new PropertyValueFactory("TinhTrang"));
        
        TableColumn colTinhTien = new TableColumn();
        colTinhTien.setCellFactory(obj -> {
            Button btn = new Button("Tính Tiền");
            btn.setOnAction(evt -> {
                try {
                    Button bt = (Button)evt.getSource();
                    TableCell cell = (TableCell)bt.getParent();
                    Ban q = (Ban)cell.getTableRow().getItem();
                    Connection conn;
                    conn = JdbcUtils.getConn();
                    BanSPService s = new BanSPService(conn);
                    SPService spSer = new SPService(conn);
                    if(q.getTinhTrang() != 0){
                        List<BanSP> banSP = s.getBanSPById(q.getId());
                        List<BanSPToString> banSPTS = new ArrayList<>();
                        banSP.forEach(p -> {
                            try {
                                BanSPToString temp = new BanSPToString();
                                temp.setId(p.getId());
                                temp.setBanId(p.getBanId());
                                temp.setTenSanPham(spSer.getTenSanPhamById(p.getSanPhamId()));
                                temp.setSoLuong(p.getSoLuong());
                                temp.setTongTien(p.getTongTien());
                                banSPTS.add(temp);
                            } catch (SQLException ex) {
                                Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        tongTien = new BigDecimal(0);
                        tbBanSP.setItems(FXCollections.observableArrayList(banSPTS));
                        maBan = q.getId();
                        s.getBanSPById(q.getId()).forEach(p -> {
                            BigDecimal temp = new BigDecimal(p.getTongTien().floatValue());
                            tongTien = new BigDecimal(tongTien.floatValue() + temp.floatValue());
                        });
                        lbTongTien.setText(tongTien.toString());
                    }
                    else
                        Utils.getBox("Bàn này chưa có người đặt", Alert.AlertType.ERROR).show();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        }); 
        
        this.tbBan.getColumns().addAll(colId, colSucChua, colTinhTrang, colTinhTien);
    }
    
    private void loadColumnsBanSP(){
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("Id"));
        
        TableColumn colMaBan = new TableColumn("Mã bàn");
        colMaBan.setCellValueFactory(new PropertyValueFactory("BanId"));
        
        TableColumn colSanPham = new TableColumn("Sản phẩm");
        colSanPham.setCellValueFactory(new PropertyValueFactory("tenSanPham"));
        
        TableColumn colSoLuong = new TableColumn("Số lượng");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("SoLuong"));
        
        TableColumn colTongTien = new TableColumn("Tổng Tiền");
        colTongTien.setCellValueFactory(new PropertyValueFactory("TongTien"));
        
        this.tbBanSP.getColumns().addAll(colId, colMaBan, colSanPham, colSoLuong, colTongTien);
    }
    
    public void loadBan() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        
        BanService b = new BanService(conn);
        
        tbBan.setItems(FXCollections.observableArrayList(b.getBan()));
        conn.close();
    }
    
    public void addHoaDon(ActionEvent evt){
        if(tongTien.floatValue() == 0 || maBan == 0)
            Utils.getBox("Hãy chọn tính tiền 1 bàn", Alert.AlertType.ERROR).show();
        else
            try {
                Connection conn = JdbcUtils.getConn();
                TongKetService p = new TongKetService(conn);
                BanService banSv = new  BanService(conn);
                BanSPService banSPsv = new BanSPService(conn);
                banSv.updateTinhTrangBanTrong(maBan);
                banSPsv.updateBanSPTinhTien(maBan);

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                TongKet pro = new TongKet();
                pro.setBanId(maBan);
                pro.setNhanVienId(PrimaryController.maNv);
                pro.setTongTien(tongTien);
                pro.setThoiGian(dtf.format(now).toString());

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                if(p.addTongKet(pro)){
                    a.setContentText("Thanh toán thành công");
                    maBan = 0;
                    loadBan();
                }
                else
                    a.setContentText("Thanh toán thất bại");

                a.show();
            } catch (SQLException ex) {
                Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
