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
public class DBPet {
    private modelPet dt = new modelPet();

    public modelPet getDt() {
        return dt;
    }

    public void setDt(modelPet dt) {
        this.dt = dt;
    }
    
    public ObservableList<modelPet> Load(){
        try{
            ObservableList<modelPet> TableData = FXCollections.observableArrayList();
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("Select * from pet");
            while(rs.next()){
                modelPet d = new modelPet();
                d.setPetId(rs.getInt("pet_id"));
                d.setPlayerId(rs.getInt("player_id"));
                d.setPetName(rs.getString("pet_name"));
                d.setPetSpecies(rs.getString("pet_species"));
                d.setPetHappy(rs.getInt("pet_happy"));
                d.setPetHunger(rs.getInt("pet_hunger"));
                d.setPetCarry(rs.getInt("pet_carry"));
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
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from pet where pet_id = '" + nomor + "'");
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
            con.preparedStatement = con.dbCon.prepareStatement("insert into pet (pet_id, player_id, pet_name, pet_species, pet_happy,pet_hunger,pet_carry) values (?,?,?,?,?,?,?)");
            con.preparedStatement.setString(1, String.valueOf(this.getDt().getPetId()));
            con.preparedStatement.setString(2, String.valueOf(this.getDt().getPlayerId()));
            con.preparedStatement.setString(3, this.getDt().getPetName());
            con.preparedStatement.setString(4, this.getDt().getPetSpecies());
            con.preparedStatement.setString(5, String.valueOf(this.getDt().getPetHappy()));
            con.preparedStatement.setString(6, String.valueOf(this.getDt().getPetHunger()));
            con.preparedStatement.setString(7, String.valueOf(this.getDt().getPetCarry()));
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
            con.preparedStatement = con.dbCon.prepareStatement("delete from pet where pet_id  = ? ");
            con.preparedStatement.setString(1, String.valueOf(nomor));
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
            con.preparedStatement = con.dbCon.prepareStatement("update pet set player_id = ?, pet_name = ?, pet_species = ?, pet_happy = ?, pet_hunger = ?, pet_carry = ? where pet_id = ? ;");
            con.preparedStatement.setString(1, String.valueOf(this.getDt().getPlayerId()));
            con.preparedStatement.setString(2, this.getDt().getPetName());
            con.preparedStatement.setString(3, this.getDt().getPetSpecies());
            con.preparedStatement.setString(4, String.valueOf(this.getDt().getPetHappy()));
            con.preparedStatement.setString(5, String.valueOf(this.getDt().getPetHunger()));
            con.preparedStatement.setString(6, String.valueOf(this.getDt().getPetCarry()));
            con.preparedStatement.setString(7, String.valueOf(this.getDt().getPetId()));
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
