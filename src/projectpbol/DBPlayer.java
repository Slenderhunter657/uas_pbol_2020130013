/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpbol;


import java.sql.*;
import javafx.collections.*;
/**
 *
 * @author ASUS
 */
public class DBPlayer {
    private modelPlayer dt = new modelPlayer();

    public modelPlayer getDt() {
        return dt;
    }

    public void setDt(modelPlayer dt) {
        this.dt = dt;
    }
    
    public ObservableList<modelPlayer> Load(){
        try{
            ObservableList<modelPlayer> TableData = FXCollections.observableArrayList();
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("Select * from player");
            while(rs.next()){
                modelPlayer d = new modelPlayer();
                d.setPlayerId(rs.getInt("player_id"));
                d.setName(rs.getString("player_name"));
                d.setMaxItemWeight(rs.getFloat("max_item_weight"));
                d.setEquipedPet(rs.getInt("equiped_pet"));
                TableData.add(d);
            }
            con.clCon();
            return TableData;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public int validasi(int nomor) {
        int val = 0;
        try {
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from player where player_id = '" + nomor + "'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.clCon();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }
    
    public boolean insert() {
        boolean berhasil = false;
        connection con = new connection();
        try {
            con.opCon();
            con.preparedStatement = con.dbCon.prepareStatement("insert into player (player_id, player_name, max_item_weight, equiped_pet) values (?,?,?,?)");
            con.preparedStatement.setString(1, String.valueOf(this.getDt().getPlayerId()));
            con.preparedStatement.setString(2, this.getDt().getName());
            con.preparedStatement.setString(3, String.valueOf(this.getDt().getMaxItemWeight()));
            con.preparedStatement.setString(4, String.valueOf(this.getDt().getEquipedPet()));
            con.preparedStatement.executeUpdate();
            berhasil = true;
        }catch (Exception e){
            e.printStackTrace();
            berhasil = false;
        }finally{
            con.clCon();
            return berhasil;
        }
    }
    
    public boolean delete(int nomor) {
        boolean berhasil = false;
        connection con = new connection();
        try {
            con.opCon();;
            con.preparedStatement = con.dbCon.prepareStatement("delete from player where player_id  = ? ");
            con.preparedStatement.setInt(1, nomor);
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.clCon();
            return berhasil;
        }
    }
    
    public boolean update() {
        boolean berhasil = false;
        connection con = new connection();
        try {
            con.opCon();
            con.preparedStatement = con.dbCon.prepareStatement("update player set player_name = ?, max_item_weight = ?, equiped_pet = ? where player_id = ? ;");
            con.preparedStatement.setString(1, this.getDt().getName());
            con.preparedStatement.setString(2, String.valueOf(this.getDt().getMaxItemWeight()));
            con.preparedStatement.setString(3, String.valueOf(this.getDt().getEquipedPet()));
            con.preparedStatement.setString(4, String.valueOf(this.getDt().getPlayerId()));
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            berhasil = false;
        } finally {
            con.clCon();
            return berhasil;
        }
    }
    
    
}
