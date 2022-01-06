package com.mycompany.quancf;

import com.qcf.pojo.Ban;
import com.qcf.pojo.BanSP;
import com.qcf.pojo.SanPham;
import com.qcf.service.BanSPService;
import com.qcf.service.BanService;
import com.qcf.service.JdbcUtils;
import com.qcf.service.SPService;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SecondaryController implements Initializable{
    @FXML private TableView<Ban> tbBan;
    @FXML private TextField sucChua;
    @FXML private TextField themSucChua;
    @FXML private ComboBox<String> themTinhTrang;
    @FXML private Button btCapNhat;
    @FXML private Label lbSucChua;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        themTinhTrang.getItems().addAll("0", "1");
        checkOnline(PrimaryController.online);
        
        
        loadColumns();
        try {
            loadBan();
        } catch (SQLException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sucChua.textProperty().addListener(obj -> {
            try {
                loadSucChua(Integer.parseInt(sucChua.getText()));
            } catch (SQLException ex) {
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.tbBan.setRowFactory(obj -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(e ->{
                Ban p = this.tbBan.getSelectionModel().getSelectedItem();
                themSucChua.setText(String.valueOf(p.getSucChua()));
                themTinhTrang.getSelectionModel().select(p.getTinhTrang());
            });
           
            return row;
        });
    }
    
    public void checkOnline(boolean a){
        themSucChua.setVisible(a);
        themTinhTrang.setVisible(a);
        btCapNhat.setVisible(a);
    }
    
    public void updateBan() throws SQLException{
        Ban p = this.tbBan.getSelectionModel().getSelectedItem();
        p.setSucChua(Integer.parseInt(themSucChua.getText()));
        p.setTinhTrang(Integer.parseInt(themTinhTrang.getValue()));
        
        Connection conn = JdbcUtils.getConn();
        
        BanService s = new BanService(conn);
        if(s.updateBan(p)){
            Utils.getBox("Cập nhật thành công", Alert.AlertType.INFORMATION).show();
            loadBan();
        }
        else
            Utils.getBox("Cập nhật thất bại", Alert.AlertType.ERROR).show();
        conn.close();
    }
    
    public void loadColumns(){
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("Id"));
        
        TableColumn colSucChua = new TableColumn("Suc chua");
        colSucChua.setCellValueFactory(new PropertyValueFactory("SucChua"));
        
        TableColumn colTinhTrang = new TableColumn("Tinh trang");
        colTinhTrang.setCellValueFactory(new PropertyValueFactory("TinhTrang"));
        
        TableColumn colDatBan = new TableColumn("Dat ban");
        colDatBan.setCellFactory(obj -> {
            Button btn = new Button("Dat Ban");
            btn.setOnAction(evt -> {
                try {
                    Button bt = (Button)evt.getSource();
                    TableCell cell = (TableCell)bt.getParent();
                    Ban q = (Ban)cell.getTableRow().getItem();
                    Connection conn;
                    conn = JdbcUtils.getConn();
                    BanSPService s = new BanSPService(conn);
                    BanService banSv = new BanService(conn);
                    if(q.getTinhTrang() == 0){
                        if(s.addBanSP(q.getId())){
                            Utils.getBox("Đặt bàn thành công", Alert.AlertType.INFORMATION).show();
                            banSv.updateTinhTrangBan(q.getId());
                            PrimaryController.daDatBan = true;
                            tbBan.setVisible(!PrimaryController.daDatBan);
                            sucChua.setVisible(!PrimaryController.daDatBan);
                            lbSucChua.setVisible(!PrimaryController.daDatBan);
                        }
                        else
                            Utils.getBox("Đặt bàn thất bại", Alert.AlertType.ERROR).show();
                    }
                    else
                        Utils.getBox("Bàn này đã có người đặt", Alert.AlertType.ERROR).show();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });
        
        if(!PrimaryController.online)
            this.tbBan.getColumns().addAll(colId, colSucChua, colTinhTrang, colDatBan);
        else
            this.tbBan.getColumns().addAll(colId, colSucChua, colTinhTrang);
    }
    
    public void loadBan() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        
        BanService b = new BanService(conn);
        
        tbBan.setItems(FXCollections.observableArrayList(b.getBan()));
        conn.close();
    }
    
    public void loadSucChua(int sucChua) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        
        BanService b = new BanService(conn);
        
        tbBan.setItems(FXCollections.observableArrayList(b.getSucChua(sucChua)));
        conn.close();
    }
}