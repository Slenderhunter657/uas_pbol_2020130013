/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectpbol;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLDisplayPetController implements Initializable {

    @FXML
    private TableView<modelPet> tbvpet;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showData();
    }    
    
    private void showData() {
        ObservableList<modelPet> data = FXMLMainController.dtPet.Load();
        if (data != null) {
            tbvpet.getColumns().clear();
            tbvpet.getItems().clear();

            TableColumn col = new TableColumn("pet_id");
            col.setCellValueFactory(new PropertyValueFactory<modelPet, String>("petId"));
            tbvpet.getColumns().addAll(col);
            
            col = new TableColumn("player_id");
            col.setCellValueFactory(new PropertyValueFactory<modelPet, String>("playerId"));
            tbvpet.getColumns().addAll(col);

            col = new TableColumn("pet_name");
            col.setCellValueFactory(new PropertyValueFactory<modelPet, String>("petName"));
            tbvpet.getColumns().addAll(col);
            
            col = new TableColumn("pet_species");
            col.setCellValueFactory(new PropertyValueFactory<modelPet, String>("petSpecies"));
            tbvpet.getColumns().addAll(col);
            
            col = new TableColumn("pet_happy");
            col.setCellValueFactory(new PropertyValueFactory<modelPet, String>("petHappy"));
            tbvpet.getColumns().addAll(col);
            
            col = new TableColumn("pet_hunger");
            col.setCellValueFactory(new PropertyValueFactory<modelPet, String>("petHunger"));
            tbvpet.getColumns().addAll(col);
            
            col = new TableColumn("pet_carry");
            col.setCellValueFactory(new PropertyValueFactory<modelPet, String>("petCarry"));
            tbvpet.getColumns().addAll(col);

            tbvpet.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "DATA kosong", ButtonType.OK);
            a.showAndWait();
            tbvpet.getScene().getWindow().hide();
        }
    }

    @FXML
    private void pickFirst(ActionEvent event) {
        tbvpet.getSelectionModel().selectFirst();
        tbvpet.requestFocus();
    }

    @FXML
    private void pickPrev(ActionEvent event) {
        tbvpet.getSelectionModel().selectAboveCell();
        tbvpet.requestFocus();
    }

    @FXML
    private void pickNext(ActionEvent event) {
        tbvpet.getSelectionModel().selectBelowCell();
        tbvpet.requestFocus();
    }

    @FXML
    private void pickLast(ActionEvent event) {
        tbvpet.getSelectionModel().selectLast();
        tbvpet.requestFocus();
    }

    @FXML
    private void addClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputPet.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showData();
        pickFirst(event);
    }

    @FXML
    private void updateClick(ActionEvent event) {
        modelPet s = new modelPet();
        s = tbvpet.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputPet.fxml"));
            Parent root = (Parent) loader.load();
            FXMLInputPetController isidt = (FXMLInputPetController) loader.getController();
            isidt.execute(s);
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showData();
        pickFirst(event);
    }

    @FXML
    private void deleteClick(ActionEvent event) {
        modelPet s = new modelPet();
        s = tbvpet.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            if (FXMLMainController.dtPet.delete(s.getPetId())) {
                Alert b = new Alert(Alert.AlertType.INFORMATION, "Data berhasil dihapus", ButtonType.OK);
                b.showAndWait();
            } else {
                Alert b = new Alert(Alert.AlertType.ERROR, "Data gagal dihapus", ButtonType.OK);
                b.showAndWait();
            }
            showData();
            pickFirst(event);
        }
    }   
    
}
