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
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLEmuController implements Initializable {

    @FXML
    private Label tfname;
    @FXML
    private ComboBox<String> cbpet;
    @FXML
    private Label tfstat;
    @FXML
    private TableView<modelPlayerInv> tbvinv;

    modelPlayer p = new modelPlayer();
    HashMap<String,Integer> petList = new HashMap();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("select pet_id, pet_name from pet;");
            while (rs.next()) {
                petList.put(rs.getString("pet_name"),Integer.parseInt(rs.getString("pet_id")));
                cbpet.getItems().addAll(rs.getString("pet_name"));
            }
            con.clCon();
            System.out.println(petList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getData(modelPlayer a) {
        p.setPlayerId(a.getPlayerId());
        p.setName(a.getName());
        p.setMaxItemWeight(a.getMaxItemWeight());
        p.setEquipedPet(a.getEquipedPet());
        tfname.setText(p.getName());
        if(p.getEquipedPet()!=0){
            System.out.println("heyas");
            cbpet.getSelectionModel().select(p.getEquipedPet()-1);
        }
        updateEncumbered(a);
        showData();

    }

    private void updateEncumbered(modelPlayer a) {
        ArrayList<Integer> iQ = new ArrayList();
        ArrayList<Float> iW = new ArrayList();
        int quan = 0;
        float w = 0;
        float tot = 0;
        float maxW = getMaxItemWeight(a, getPetWeight());

        try {
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("select i.item_quantity quan, t.item_weight weight from playerinventory i left join items t on i.item_id = t.item_id where i.player_id = '" + a.getPlayerId() + "'");
            while (rs.next()) {
                if (rs.getString("quan") == null) {
                    quan = 0;
                    w = 0;
                } else {
                    quan = Integer.parseInt(rs.getString("quan"));
                    w = Float.parseFloat(rs.getString("weight"));
                }
                iQ.add(quan);
                iW.add(w);
            }

            int size = iQ.size();

            for (int i = size-1; i >= 0; i--) {
                tot += (iQ.get(i) * iW.get(i));
                iQ.remove(i);
                iW.remove(i);
            }

            tfstat.setText(tot + "/" + maxW);

            if (tot > maxW) {
                tfstat.setStyle("-fx-text-fill: #ff0000;");
            } else {
                tfstat.setStyle("-fx-text-fill: #000000;");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private float getMaxItemWeight(modelPlayer a, float b){
        float res = a.getMaxItemWeight()+b;
        return res;
    }

    private void showData() {
        ObservableList<modelPlayerInv> data = FXMLMainController.dtPlayerInv.Load(p);
        if (data != null) {
            tbvinv.getColumns().clear();
            tbvinv.getItems().clear();

            TableColumn col = new TableColumn("item_id");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayerInv, String>("item_id"));
            tbvinv.getColumns().addAll(col);

            col = new TableColumn("item_name");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayerInv, String>("item_name"));
            tbvinv.getColumns().addAll(col);

            col = new TableColumn("item_weight");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayerInv, String>("item_w"));
            tbvinv.getColumns().addAll(col);

            col = new TableColumn("item_happy");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayerInv, String>("item_hp"));
            tbvinv.getColumns().addAll(col);

            col = new TableColumn("item_hunger");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayerInv, String>("item_hg"));
            tbvinv.getColumns().addAll(col);

            col = new TableColumn("item_quantity");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayer, String>("item_quantity"));
            tbvinv.getColumns().addAll(col);

            tbvinv.setItems(data);
        } else {
            tbvinv.getColumns().clear();
            tbvinv.getItems().clear();

            TableColumn col = new TableColumn("item_id");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayerInv, String>("item_id"));
            tbvinv.getColumns().addAll(col);

            col = new TableColumn("item_name");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayerInv, String>("item_name"));
            tbvinv.getColumns().addAll(col);

            col = new TableColumn("item_weight");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayerInv, String>("item_w"));
            tbvinv.getColumns().addAll(col);

            col = new TableColumn("item_happy");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayerInv, String>("item_hp"));
            tbvinv.getColumns().addAll(col);

            col = new TableColumn("item_hunger");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayerInv, String>("item_hg"));
            tbvinv.getColumns().addAll(col);

            col = new TableColumn("item_quantity");
            col.setCellValueFactory(new PropertyValueFactory<modelPlayer, String>("item_quantity"));
            tbvinv.getColumns().addAll(col);
//            Alert a = new Alert(Alert.AlertType.ERROR, "DATA kosong", ButtonType.OK);
//            a.showAndWait();
//            tbvinv.getScene().getWindow().hide();
        }
    }

    @FXML
    private void addRandomItem(ActionEvent event) {
        Random rd = new Random();
        modelPlayerInv m = new modelPlayerInv();
        m.setPlayer_id(p.getPlayerId());

        int max = checkItemHighestId();

        if (max < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Database Error", ButtonType.OK);
            a.showAndWait();
            tbvinv.getScene().getWindow().hide();
        }

        m.setItem_id(rd.nextInt(max) + 1);
        m.setItem_quantity(rd.nextInt(10));

        FXMLMainController.dtPlayerInv.setDt(m);
        if (FXMLMainController.dtPlayerInv.validasi(m.getPlayer_id(), m.getItem_id()) <= 0) {
            if (FXMLMainController.dtPlayerInv.insert()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Random Item telah dimasukkan", ButtonType.OK);
                a.showAndWait();
                showData();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Random Item gagal dimasukkan", ButtonType.OK);
                a.showAndWait();
                showData();
            }
        } else {
//            Alert a = new Alert(Alert.AlertType.ERROR, "Error: unknown", ButtonType.OK);
//            a.showAndWait();
//            tfname.requestFocus();
            if (FXMLMainController.dtPlayerInv.updateAdd()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Random Item telah dimasukkan", ButtonType.OK);
                a.showAndWait();
                showData();
            }
        }
        updateEncumbered(p);
        showData();

    }

    @FXML
    private void deleteItem(ActionEvent event) {
        modelPlayerInv s = new modelPlayerInv();
        s = tbvinv.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            if (FXMLMainController.dtPlayerInv.delete(s.getPlayer_id(), s.getItem_id())) {
                Alert b = new Alert(Alert.AlertType.INFORMATION, "Item telah dihapus", ButtonType.OK);
                b.showAndWait();
            } else {
                Alert b = new Alert(Alert.AlertType.ERROR, "Item gagal dihapus", ButtonType.OK);
                b.showAndWait();
            }
            updateEncumbered(p);
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

    @FXML
    private void useItem(ActionEvent event) {
        modelPlayerInv s = new modelPlayerInv();
        s = tbvinv.getSelectionModel().getSelectedItem();
        boolean consumable = false;

        try {
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("select consumable from items where item_id = '" + s.getItem_id() + "'");
            if (rs.next()) {
                consumable = rs.getBoolean("consumable");
            }
            con.clCon();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (!consumable) {
            Alert b = new Alert(Alert.AlertType.WARNING, "Item bukan barang habis pakai", ButtonType.OK);
            b.showAndWait();
            return;
        }

        if (FXMLMainController.dtPlayerInv.delete(s.getPlayer_id(), s.getItem_id())) {
            Alert b = new Alert(Alert.AlertType.INFORMATION, "Item telah dipakai", ButtonType.OK);
            b.showAndWait();
        } else {
            Alert b = new Alert(Alert.AlertType.ERROR, "Item kosong/tidak ada", ButtonType.OK);
            b.showAndWait();
        }
        updateEncumbered(p);
        showData();
    }

    @FXML
    private void equipPet(ActionEvent event) {
        float a = 0;
        try {
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("select pet_carry from pet where pet_id = '"+petList.get(cbpet.getValue())+"'");
            while (rs.next()) {
                a = rs.getFloat("pet_carry");
            }
            p.setEquipedPet(petList.get(cbpet.getValue()));
            FXMLMainController.dtPlayer.setDt(p);
            FXMLMainController.dtPlayer.update();
            con.clCon();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        updateEncumbered(p);
        
    }
    
    private float getPetWeight() {
        if(cbpet.getValue() == null||cbpet.getValue().isEmpty()||cbpet.getValue().equalsIgnoreCase("")){
            return 0;
        }
        float a = 0;
        try {
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("select pet_carry from pet where pet_id = '"+petList.get(cbpet.getValue())+"'");
            while (rs.next()) {
                a = rs.getFloat("pet_carry");
                return a;
            }
            con.clCon();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return a;
    }

}
