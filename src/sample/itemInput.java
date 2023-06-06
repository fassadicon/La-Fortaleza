package sample;

import java.sql.*;

public class itemInput extends Main{
    itemInput (String itemID, String itemName, String itemDesc, String itemUnit, Double itemPricePerUnit) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");

            Statement statement = connection.createStatement();

            String sql = "INSERT INTO item (itemID, itemName, itemDesc, itemUnit, itemPricePerUnit)"
                    + " VALUES ('" + itemID + "','" + itemName + "','" + itemDesc +  "','" +
                    itemUnit + "'," + itemPricePerUnit + ")"
                    + " ON DUPLICATE KEY UPDATE itemID = itemID";

            statement.executeUpdate(sql);

        } catch (SQLException error) {
            System.out.println(error);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
