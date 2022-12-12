/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectpbol;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.*;
import javafx.collections.FXCollections;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLEmuController implements Initializable {

    @FXML
    private TextField tfname;
    @FXML
    private ComboBox<String> cbpet;
    @FXML
    private TextField tfstat;
    @FXML
    private TableView<modelPlayerInv> tbvinv;
    
    modelPlayer  p = new modelPlayer();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("select pet_name from pet;");
            while (rs.next()) {
                cbpet.getItems().addAll(rs.getString("pet_name"));
            }
            
//            rs = con.statement.executeQuery("select sum()")
            
            con.clCon();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        tfname.setText(p.getName());
        tfstat.setText("false");
    }

    public void getData(modelPlayer a) {
        p.setPlayerId(a.getPlayerId());
        p.setName(a.getName());
        p.setMaxItemWeight(a.getMaxItemWeight());
        p.setEquipedPet(a.getEquipedPet());
    }

    private void showData() {
        ObservableList<modelPlayerInv> data = FXMLMainController.dtPlayerInv.Load();
        if (data != null) {
            tbvinv.getColumns().clear();
            tbvinv.getItems().clear();

            TableColumn col = new TableColumn("player_id");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayer, String>("player_id"));
            tbvinv.getColumns().addAll(col);

            col = new TableColumn("item_id");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayer, String>("item_id"));
            tbvinv.getColumns().addAll(col);

            col = new TableColumn("item_quantity");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayer, String>("item_quantity"));
            tbvinv.getColumns().addAll(col);

            tbvinv.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "DATA kosong", ButtonType.OK);
            a.showAndWait();
            tbvinv.getScene().getWindow().hide();
        }
    }

    @FXML
    private void addRandomItem(ActionEvent event) {
        Random rd = new Random();
        modelPlayerInv m = new modelPlayerInv();
        m.setPlayer_id(p.getPlayerId());
        
        int max = checkItemHighestId();
        
        if(max<0){
            Alert a = new Alert(Alert.AlertType.ERROR, "Database Error", ButtonType.OK);
            a.showAndWait();
            tbvinv.getScene().getWindow().hide();
        }
        
        m.setItem_id(rd.nextInt(max+1)+1);
        m.setItem_quantity(1);

        FXMLMainController.dtPlayerInv.setDt(m);
        if (FXMLMainController.dtPlayerInv.validasi(m.getPlayer_id(),m.getItem_id()) <= 0) {
            if (FXMLMainController.dtPlayerInv.insert()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Random Item telah dimasukkan", ButtonType.OK);
                a.showAndWait();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Random Item gagal dimasukkan", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Error: unknown", ButtonType.OK);
            a.showAndWait();
            tfname.requestFocus();
        }

    }

    @FXML
    private void deleteItem(ActionEvent event) {
        modelPlayerInv s = new modelPlayerInv();
        s = tbvinv.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            if (FXMLMainController.dtPlayerInv.delete(s.getPlayer_id(),s.getItem_id())) {
                Alert b = new Alert(Alert.AlertType.INFORMATION, "Item telah dihapus", ButtonType.OK);
                b.showAndWait();
            } else {
                Alert b = new Alert(Alert.AlertType.ERROR, "Item gagal dihapus", ButtonType.OK);
                b.showAndWait();
            }
            showData();
        }
    }

    public int checkItemHighestId() {
        try {
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("select item_id from items order by item_id desc");
            while (rs.next()) {
                int a = rs.getInt("item_id");
                return a;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }
    
}
