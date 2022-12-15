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
public class FXMLDisplayItemController implements Initializable {

    @FXML
    private TableView<modelItem> tbvitem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showData();
    }    
    
    private void showData() {
        ObservableList<modelItem> data = FXMLMainController.dtItem.Load();
        if (data != null) {
            tbvitem.getColumns().clear();
            tbvitem.getItems().clear();

            TableColumn col = new TableColumn("item_id");
            col.setCellValueFactory(new PropertyValueFactory<modelItem, String>("itemId"));
            tbvitem.getColumns().addAll(col);
            
            col = new TableColumn("item_name");
            col.setCellValueFactory(new PropertyValueFactory<modelItem, String>("itemName"));
            tbvitem.getColumns().addAll(col);
            
            col = new TableColumn("item_weight");
            col.setCellValueFactory(new PropertyValueFactory<modelItem, String>("itemWeight"));
            tbvitem.getColumns().addAll(col);
            
            col = new TableColumn("item_fun");
            col.setCellValueFactory(new PropertyValueFactory<modelItem, String>("itemHappy"));
            tbvitem.getColumns().addAll(col);
            
            col = new TableColumn("item_hunger");
            col.setCellValueFactory(new PropertyValueFactory<modelItem, String>("itemHunger"));
            tbvitem.getColumns().addAll(col);
            
            col = new TableColumn("consumable");
            col.setCellValueFactory(new PropertyValueFactory<modelItem, String>("consumable"));
            tbvitem.getColumns().addAll(col);

            tbvitem.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "DATA kosong", ButtonType.OK);
            a.showAndWait();
            tbvitem.getScene().getWindow().hide();
        }
    }

    @FXML
    private void pickFirst(ActionEvent event) {
        tbvitem.getSelectionModel().selectFirst();
        tbvitem.requestFocus();
    }

    @FXML
    private void pickPrev(ActionEvent event) {
        tbvitem.getSelectionModel().selectAboveCell();
        tbvitem.requestFocus();
    }

    @FXML
    private void pickNext(ActionEvent event) {
        tbvitem.getSelectionModel().selectBelowCell();
        tbvitem.requestFocus();
    }

    @FXML
    private void pickLast(ActionEvent event) {
        tbvitem.getSelectionModel().selectLast();
        tbvitem.requestFocus();
    }

    @FXML
    private void addClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputItem.fxml"));
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
        modelItem s = new modelItem();
        s = tbvitem.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputItem.fxml"));
            Parent root = (Parent) loader.load();
            FXMLInputItemController isidt = (FXMLInputItemController) loader.getController();
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
        modelItem s = new modelItem();
        s = tbvitem.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            if (FXMLMainController.dtItem.delete(s.getItemId())) {
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
