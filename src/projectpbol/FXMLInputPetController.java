/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectpbol;

import java.net.URL;
import java.sql.ResultSet;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLInputPetController implements Initializable {

    boolean editdata = false;
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
    @FXML
    private TextField tfname;
    @FXML
    private TextField tfspec;
    @FXML
    private TextField tfcarry;
    @FXML
    private ChoiceBox<String> chsplayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("select player_name from player;");
            while (rs.next()) {
                chsplayer.getItems().addAll(rs.getString("player_name"));
            }
            con.clCon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelClick(ActionEvent event) {
        tfspec.clear();
        tfcarry.clear();
        tfname.clear();
        chsplayer.getSelectionModel().selectFirst();
    }

    @FXML
    private void submitClick(ActionEvent event) {
        modelPet m = new modelPet();
        int id = checkHighestId() + 1;
        if (id <= 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Database Error", ButtonType.OK);
            a.showAndWait();
            return;
        }
        m.setPetId(id);

        try {
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("select player_id from player where player_name = '" + chsplayer.getValue() + "'");
            while (rs.next()) {
                m.setPlayerId(Integer.parseInt(rs.getString("player_id")));
            }
            con.clCon();
        } catch (Exception e) {
            e.printStackTrace();
        }

        m.setPetName(tfname.getText());
        m.setPetSpecies(tfspec.getText());
        m.setPetHunger(0);
        m.setPetHappy(50);
        
        if(tfcarry.getText().equals("")||tfcarry.getText().isEmpty()||tfcarry.getText()==null){
            Random rd = new Random();
            m.setPetCarry((rd.nextFloat()*70)+30);
        }
        
        try {
            m.setPetCarry(Float.parseFloat(tfcarry.getText()));
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Field Carry hanya bisa menerima angka!", ButtonType.OK);
            a.showAndWait();
            return;
        }

        FXMLMainController.dtPet.setDt(m);

        if (editdata) {
            if (FXMLMainController.dtPet.update()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil diubah", ButtonType.OK);
                a.showAndWait();
                tfname.setEditable(true);
                cancelClick(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal diubah", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            if (FXMLMainController.dtPet.validasi(m.getPetId(),m.getPetName()) <= 0) {
                if (FXMLMainController.dtPet.insert()) {
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

    public void execute(modelPet a) {
        if (a.getPetId() != 0) {
            String nm = "";
            
            editdata = true;
            tfname.setText(String.valueOf(a.getPetName()));
            tfspec.setText(String.valueOf(a.getPetSpecies()));
            tfcarry.setText(String.valueOf(a.getPetCarry()));
            chsplayer.getSelectionModel().selectFirst();
            
            String comp = chsplayer.getValue();

            try {
                connection con = new connection();
                con.opCon();
                con.statement = con.dbCon.createStatement();
                ResultSet rs = con.statement.executeQuery("select player_name from player where player_id = '" + a.getPlayerId() + "'");
                while (rs.next()) {
                    nm = rs.getString("player_name");
                }
                con.clCon();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            if(chsplayer.getValue().equalsIgnoreCase(nm)){
                return;
            }
            
            chsplayer.getSelectionModel().selectNext();

            while (!chsplayer.getValue().equalsIgnoreCase(nm)&&!chsplayer.getValue().equalsIgnoreCase(comp)) {
                chsplayer.getSelectionModel().selectNext();
            }
        }
    }

    public int checkHighestId() {
        try {
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("select pet_id from pet order by pet_id desc");
            while (rs.next()) {
                int a = rs.getInt("pet_id");
                return a;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

}
