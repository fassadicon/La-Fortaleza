package sample;

import java.sql.*;

public class vendorInput {
    vendorInput(String venID, String venName, String venCPerson, String venAdd, String venTel, String venFax) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");

            Statement statement = connection.createStatement();

            String sql = "INSERT INTO vendor (venID, venName, venAdd, venCPerson, venTel, venFax)"
                    + " VALUES ('" + venID + "','" + venName + "','" + venCPerson +  "','" +
                    venAdd + "','" + venTel + "','" + venFax + "')"
                    + " ON DUPLICATE KEY UPDATE venID = venID";

            statement.executeUpdate(sql);
        } catch (SQLException error) {
            System.out.println(error);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
