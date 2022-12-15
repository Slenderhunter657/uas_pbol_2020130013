/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectpbol;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLInputItemController implements Initializable {

    @FXML
    private TextField tfname;
    @FXML
    private TextField tfweight;
    @FXML
    private TextField tfhappy;
    @FXML
    private TextField tfhungger;

    @FXML
    private Label title;
    @FXML
    private Label lname;
    @FXML
    private Label lweight;
    @FXML
    private Label lhappy;
    @FXML
    private Label lhunger;

    boolean editdata = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cancelClick(ActionEvent event) {
        tfhappy.clear();
        tfhungger.clear();
        tfname.clear();
        tfweight.clear();
    }

    @FXML
    private void submitClick(ActionEvent event) {
        modelItem m = new modelItem();
        int id = checkHighestId() + 1;
        if (id <= 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Database Error", ButtonType.OK);
            a.showAndWait();
            return;
        }
        m.setItemId(id);
        m.setItemName(tfname.getText());
        try {
            m.setItemWeight(Float.parseFloat(tfweight.getText()));
            m.setItemHappy(Integer.parseInt(tfhappy.getText()));
            m.setItemHunger(Integer.parseInt(tfhungger.getText()));
        }catch(NumberFormatException e){
            Alert a = new Alert(Alert.AlertType.ERROR, "Field Happy, Hunger, dan Weight hanya bisa menerima angka!", ButtonType.OK);
            a.showAndWait();
            return;
        }
        
        if(m.getItemHappy()>0||m.getItemHunger()>0){
                m.setConsumable(true);
            }else{
                m.setConsumable(false);
            }

        FXMLMainController.dtItem.setDt(m);
        if (editdata) {
            if (FXMLMainController.dtItem.update()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil diubah", ButtonType.OK);
                a.showAndWait();
                tfname.setEditable(true);
                cancelClick(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal diubah", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            if (FXMLMainController.dtItem.validasi(m.getItemId()) <= 0) {
                if (FXMLMainController.dtItem.insert()) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil disimpan", ButtonType.OK);
                    a.showAndWait();
                    cancelClick(event);
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal disimpan", ButtonType.OK);
                    a.showAndWait();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data sudah ada", ButtonType.OK);
                a.showAndWait();
                tfname.requestFocus();
            }
        }
    }

    public void execute(modelItem a) {
        if (a.getItemId() != 0) {
            editdata = true;
            tfhappy.setText(String.valueOf(a.getItemHappy()));
            tfhungger.setText(String.valueOf(a.getItemHunger()));
            tfname.setText(a.getItemName());
            tfweight.setText(String.valueOf(a.getItemWeight()));
        }
    }

    public int checkHighestId() {
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
