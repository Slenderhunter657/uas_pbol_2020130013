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
public class FXMLDisplayPlayerController implements Initializable {

    @FXML
    private TableView<modelPlayer> tbvplayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showData();
    }    
    
    private void showData() {
        ObservableList<modelPlayer> data = FXMLMainController.dtPlayer.Load();
        if (data != null) {
            tbvplayer.getColumns().clear();
            tbvplayer.getItems().clear();

            TableColumn col = new TableColumn("player_id");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayer, String>("playerId"));
            tbvplayer.getColumns().addAll(col);

            col = new TableColumn("player_name");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayer, String>("name"));
            tbvplayer.getColumns().addAll(col);
            
            col = new TableColumn("max_item_weight");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayer, String>("maxItemWeight"));
            tbvplayer.getColumns().addAll(col);
            
            col = new TableColumn("equiped_pet");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayer, String>("equipedPet"));
            tbvplayer.getColumns().addAll(col);

            tbvplayer.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "DATA kosong", ButtonType.OK);
            a.showAndWait();
            tbvplayer.getScene().getWindow().hide();
        }
    }

    @FXML
    private void pickFirst(ActionEvent event) {
        tbvplayer.getSelectionModel().selectFirst();
        tbvplayer.requestFocus();
    }

    @FXML
    private void pickPrev(ActionEvent event) {
        tbvplayer.getSelectionModel().selectAboveCell();
        tbvplayer.requestFocus();
    }

    @FXML
    private void pickNext(ActionEvent event) {
        tbvplayer.getSelectionModel().selectBelowCell();
        tbvplayer.requestFocus();
    }

    @FXML
    private void pickLast(ActionEvent event) {
        tbvplayer.getSelectionModel().selectLast();
        tbvplayer.requestFocus();
    }

    @FXML
    private void addClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputPlayer.fxml"));
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
        modelPlayer s = new modelPlayer();
        s = tbvplayer.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputPlayer.fxml"));
            Parent root = (Parent) loader.load();
            FXMLInputPlayerController isidt = (FXMLInputPlayerController) loader.getController();
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
        modelPlayer s = new modelPlayer();
        s = tbvplayer.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            if (FXMLMainController.dtPlayer.delete(s.getPlayerId())) {
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
