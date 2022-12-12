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
public class FXMLInputPlayerController implements Initializable {
    
    private boolean editdata = false;

    @FXML
    private Label title;
    @FXML
    private Label lname;
    @FXML
    private TextField tfname;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void cancelClick(ActionEvent event) {
        tfname.clear();
    }

    @FXML
    private void submitClick(ActionEvent event) {
        modelPlayer m = new modelPlayer();
        int id = checkHighestId() + 1;
        if (id <= 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Database Error", ButtonType.OK);
            a.showAndWait();
            return;
        }
        m.setPlayerId(id);
        m.setName(tfname.getText());
        m.setMaxItemWeight(32);
        m.setEquipedPet(0);

        FXMLMainController.dtPlayer.setDt(m);

        if (editdata) {
            if (FXMLMainController.dtPlayer.update()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil diubah", ButtonType.OK);
                a.showAndWait();
                tfname.setEditable(true);
                cancelClick(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal diubah", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            if (FXMLMainController.dtPlayer.validasi(m.getPlayerId()) <= 0) {
                if (FXMLMainController.dtPlayer.insert()) {
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

    public void execute(modelPlayer a) {
        if (a.getPlayerId()!= 0) {
            String nm = "";
            
            editdata = true;
            tfname.setText(String.valueOf(a.getName()));
        }
    }

    public int checkHighestId() {
        try {
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("select player_id from player order by player_id desc");
            while (rs.next()) {
                int a = rs.getInt("player_id");
                return a;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }
}
