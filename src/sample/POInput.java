package sample;

import javafx.scene.Parent;

import java.sql.*;

public class POInput extends itemController{
    private Parent root;
    POInput (String POnum, String createDate, String delDate, String ToP, String MoP, String valDate, double totalAmount, String prepBy, String appBy, String venID, int brNo) {
       try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");

            Statement statement = connection.createStatement();

            String sql = "INSERT INTO purchaseorder (POnum, createDate, deliverDate, termsOfPay, modeOfPay, validDate," +
                    "totalAmount, preparedBy, approvedBy, venID, branchNo)" +
                    " VALUES ('" + POnum + "','" + createDate +  "','" + delDate +  "','"
                    + ToP +  "','" + MoP + "','" + valDate + "','" + totalAmount + "','" + prepBy + "','" + appBy + "','"
                    + venID + "'," + brNo + ")" + " ON DUPLICATE KEY UPDATE totalAmount = " + totalAmount;

//           String sql = "INSERT INTO purchaseorder VALUES ('"
//                   + POnum + "','" + createDate +  "','" + delDate +  "','"
//                   + ToP +  "','" + MoP + "','" + valDate + "','" + totalAmount + "','" + prepBy + "','" + appBy + "','"
//                   + venID + "'," + brNo + ")";
            statement.executeUpdate(sql);
       } catch (Exception error) {
           error.printStackTrace();
       }
   }
}
