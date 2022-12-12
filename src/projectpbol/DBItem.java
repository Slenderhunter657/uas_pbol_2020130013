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
public class DBItem {
    private modelItem dt = new modelItem();

    public modelItem getDt() {
        return dt;
    }

    public void setDt(modelItem dt) {
        this.dt = dt;
    }
    
    public ObservableList<modelItem> Load(){
        try{
            ObservableList<modelItem> TableData = FXCollections.observableArrayList();
            connection con = new connection();
            con.opCon();
            con.statement = con.dbCon.createStatement();
            ResultSet rs = con.statement.executeQuery("Select item_id,item_name,item_weight,item_fun,item_hunger from items");
            while(rs.next()){
                modelItem d = new modelItem();
                d.setItemId(Integer.parseInt(rs.getString("item_id")));
                d.setItemName(rs.getString("item_name"));
                d.setItemWeight(Float.parseFloat(rs.getString("item_weight")));
                d.setItemHappy(Integer.parseInt(rs.getString("item_fun")));
                d.setItemHunger(Integer.parseInt(rs.getString("item_hunger")));
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
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from items where item_id = '" + nomor + "'");
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
            con.preparedStatement = con.dbCon.prepareStatement("insert into items (item_id, item_name, item_weight, item_fun, item_hunger) values (?,?,?,?,?)");
            con.preparedStatement.setString(1, String.valueOf(this.getDt().getItemId()));
            con.preparedStatement.setString(2, this.getDt().getItemName());
            con.preparedStatement.setString(3, String.valueOf(this.getDt().getItemWeight()));
            con.preparedStatement.setString(4, String.valueOf(this.getDt().getItemHappy()));
            con.preparedStatement.setString(5, String.valueOf(this.getDt().getItemHunger()));
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
            con.preparedStatement = con.dbCon.prepareStatement("delete from items where item_id  = ? ");
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
            con.preparedStatement = con.dbCon.prepareStatement("update items set item_name = ?, item_weight = ?, item_fun = ?, item_hunger = ? where item_id = ? ;");
            con.preparedStatement.setString(1, this.getDt().getItemName());
            con.preparedStatement.setString(2, String.valueOf(this.getDt().getItemWeight()));
            con.preparedStatement.setString(3, String.valueOf(this.getDt().getItemHappy()));
            con.preparedStatement.setString(4, String.valueOf(this.getDt().getItemHunger()));
            con.preparedStatement.setString(5, String.valueOf(this.getDt().getItemId()));
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
