package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class orderInput extends itemController{
    private Parent root;
    orderInput(String POnum, String itemID, int itemQuantity, double itemAmount) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");
            Statement statement = connection.createStatement();
//            String sql = "INSERT INTO finalorder (POnum, itemID, itemQuantity, itemAmount)" +
//                    " VALUES (" + "'" + POnum + "','" + itemID +  "'," + itemQuantity +  "," + itemAmount + ")"
//                    + " ON DUPLICATE KEY UPDATE itemQuantity = " + itemQuantity + ", itemAmount = " + itemAmount;
            String sql = "INSERT INTO finalorder" +
                    " VALUES (" + "'" + POnum + "','" + itemID +  "'," + itemQuantity +  "," + itemAmount + ")";

            statement.executeUpdate(sql);
        } catch (Exception error) {
            System.out.println(error);
        }
    }

}
