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
public class DBPlayerInv {
    private modelPlayerInv dt = new modelPlayerInv();

    public modelPlayerInv getDt() {
        return dt;
    }

    public void setDt(modelPlayerInv dt) {
        this.dt = dt;
    }
    
    public ObservableList<modelPlayerInv> Load(){
        try{
            ObservableList<modelPlayerInv> TableData = FXCollections.observableArrayList();
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("Select * from playerinventory");
            while(rs.next()){
                modelPlayerInv d = new modelPlayerInv();
                d.setPlayer_id(Integer.parseInt(rs.getString("player_id")));
                d.setItem_id(Integer.parseInt(rs.getString("item_id")));
                d.setItem_quantity(Integer.parseInt(rs.getString("item_quantity")));
                TableData.add(d);
            }
            con.clCon();
            return TableData;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public int validasi(int p, int i) {
        int val = 0;
        try {
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from playerinventory where player_id = '" + p + "' and item_id= '" + i + "'");
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
            con.preparedStatement = con.dbCon.prepareStatement("insert into playerinventory (player_id, item_id, item_quantity) values (?,?,?)");
            con.preparedStatement.setString(1, String.valueOf(this.getDt().getPlayer_id()));
            con.preparedStatement.setString(2, String.valueOf(this.getDt().getItem_id()));
            con.preparedStatement.setString(3, String.valueOf(this.getDt().getItem_quantity()));
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
    
    public boolean delete(int p, int i) {
        boolean berhasil = false;
        connection con = new connection();
        try {
            con.opCon();;
            con.preparedStatement = con.dbCon.prepareStatement("delete from playerinventory where player_id  = ? and item_id  = ?");
            con.preparedStatement.setString(1, String.valueOf(p));
            con.preparedStatement.setString(2, String.valueOf(i));
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
            con.preparedStatement = con.dbCon.prepareStatement("update playerinventory set item_quantity = ? where player_id = ? and item_id = ?;");
            con.preparedStatement.setString(1, String.valueOf(this.getDt().getItem_quantity()));
            con.preparedStatement.setString(2, String.valueOf(this.getDt().getPlayer_id()));
            con.preparedStatement.setString(3, String.valueOf(this.getDt().getItem_id()));
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
