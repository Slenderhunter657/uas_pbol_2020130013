/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpbol;

import java.sql.*;

/**
 *
 * @author ASUS
 */
public class connection {
    public Connection dbCon;
    public Statement statement;
    public PreparedStatement preparedStatement;
    public connection() {this.dbCon = null;}
    
    public void opCon() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbuas?user=root&password=1234");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void clCon() {
        try {
            if (statement != null){statement.close();}
            if (preparedStatement != null){preparedStatement.close();}
            if (dbCon != null){dbCon.close();}
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
