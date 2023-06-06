package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class POController extends Main implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField POTxtField;

    public void submit(ActionEvent e){
        try {
            PONum = POTxtField.getText();
            creationDate = CreateDateTxtField.getText();
            delDate = DelDateTxtField.getText();
            ToP = ToPTxtField.getText();
            MoP = tmpMoP;
            valDate = ValDateTxtField.getText();
            prepBy = PrepByTxtField.getText();
            appBy = AppByTxtField.getText();

            venID = VenIDTxtField.getText();
            venName = VenNameTxtField.getText();
            venCPerson = VenCPersonTxtField.getText();
            venAdd = VenAddTxtField.getText();
            venTel = VenTelTxtField.getText();
            venFax = VenFaxTxtField.getText();
            validate();
            validateNum();

            if (proceed == 1 && nullCatch == 1) {
                new vendorInput(venID, venName, venCPerson, venAdd, venTel, venFax);
                String PONum = POTxtField.getText();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemOrderDetails.fxml"));
                root = loader.load();
                itemController ItemController = loader.getController();
                ItemController.displayPONum(PONum);
                stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception error) {
            System.out.println(error);
        }
    }

    @FXML Label nullWarning;
    int proceed = 1, nullCatch = 1, brWarning = 0;
    public void validate () {
        try {
            brNo = getbrNo();
            if (POTxtField.getText().length() == 0) {
                POTxtField.setStyle("-fx-border-color: red;");
                nullWarning.setText("PO Number is Null");
                nullCatch = 0;
            }
            else if (VenIDTxtField.getText().length() == 0) {
                VenIDTxtField.setStyle("-fx-border-color: red;");
                nullWarning.setText("Vendor ID is Null");
                nullCatch = 0;
            }
            else if (brWarning == 0) {
                RB1.setStyle("-fx-border-color: red;");
                RB2.setStyle("-fx-border-color: red;");
                RB3.setStyle("-fx-border-color: red;");
                nullWarning.setText("Branch Number is Null");
                nullCatch = 0;
            }
            else {
                nullWarning.setText(null);
                POTxtField.setStyle(null);
                VenIDTxtField.setStyle(null);
                RB1.setStyle(null);
                RB2.setStyle(null);
                RB3.setStyle(null);
                nullCatch = 1;
            }
        } catch (Exception error) {
            System.out.println(error);
        }
    }

    @FXML Label POWarning;
    public void validateNum() {
        String verifyLogin = "SELECT COUNT(1) FROM purchaseorder WHERE POnum = '" + PONum + "'";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    POWarning.setText("This Purchase Order Number have already been used.");
                    proceed = 0;
                } else {
                    POWarning.setText("");
                    proceed = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML TextField VenNameTxtField, CreateDateTxtField, VenIDTxtField, VenCPersonTxtField, VenAddTxtField, VenTelTxtField,
            VenFaxTxtField, DelDateTxtField, ToPTxtField, ValDateTxtField, PrepByTxtField, AppByTxtField;

    public static String PONum, creationDate, delDate, ToP, MoP, valDate, prepBy, appBy,
            venID, venName, venCPerson, venAdd, venTel, venFax;

    @FXML RadioButton mopCash, mopCheque, mopBankTrans;
    public static String tmpMoP;

    public void pickMoP(ActionEvent e) {
        try {
            if (mopCash.isSelected()) {
                tmpMoP = "Cash";
            }
            else if (mopCheque.isSelected()) {
                tmpMoP = "Cheque";
            }
            else if (mopBankTrans.isSelected()) {
                tmpMoP = "Bank Transfer";
            }
            else {
                tmpMoP = null;
            }
        } catch (Exception error) {
            System.out.println(error);
        }
    }

    public String getPONum() { return PONum; }
    public String getCreateDate() {
        if(CreateDateTxtField.getText().length() == 0) {
            creationDate = String.valueOf(java.time.LocalDate.now());
        }
        return creationDate;
    }
    public String getDelDate() {
        if (DelDateTxtField.getText().length() == 0) {
            delDate = "0000-0-0";
        }
        return delDate;
    }
    public String getToP() { return ToP; }
    public String getMoP() { return tmpMoP; }
    public String getValDate() {
        if (ValDateTxtField.getText().length() == 0) {
            valDate = "0000-0-0";
        }
        return valDate;
    }
    public String getPrepBy() { return prepBy; }
    public String getAppBy() { return appBy; }
    public String getVenID() { return venID; }
    public int getbrNo() { return brNo; }

    @FXML RadioButton RB1, RB2, RB3;
    @FXML Label branchCPersonLabel, branchAddLabel, branchTelLabel, branchFaxLabel;
    public String brCPerson = null, brAdd = null, brTel = null, brFax = null;
    public static int brNo;
    public void getBranch(ActionEvent e) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");
            Statement statement = connection.createStatement();
            brNo = 0;
            if (RB1.isSelected()) {
                ResultSet resultSet1 = statement.executeQuery("SELECT * FROM branch WHERE branchNo = 1");
                brNo = 1;
                while (resultSet1.next()) {
                    brCPerson = resultSet1.getString("branchCPerson");
                    brAdd = resultSet1.getString("branchAdd");
                    brTel = resultSet1.getString("branchTel");
                    brFax = resultSet1.getString("branchFax");
                }
                brWarning = 1;
            }
            else if (RB2.isSelected()) {
                ResultSet resultSet2 = statement.executeQuery("SELECT * FROM branch WHERE branchNo = 2");
                brNo = 2;
                while (resultSet2.next()) {
                    brCPerson = resultSet2.getString("branchCPerson");
                    brAdd = resultSet2.getString("branchAdd");
                    brTel = resultSet2.getString("branchTel");
                    brFax = resultSet2.getString("branchFax");
                }
                brWarning = 1;
            }
            else if (RB3.isSelected()) {
                ResultSet resultSet3 = statement.executeQuery("SELECT * FROM branch WHERE branchNo = 3");
                brNo = 3;
                while (resultSet3.next()) {
                    brCPerson = resultSet3.getString("branchCPerson");
                    brAdd = resultSet3.getString("branchAdd");
                    brTel = resultSet3.getString("branchTel");
                    brFax = resultSet3.getString("branchFax");
                }
                brWarning = 1;
            }
            branchCPersonLabel.setText(brCPerson);
            branchAddLabel.setWrapText(true);
            branchAddLabel.setText(brAdd);
            branchTelLabel.setText(brTel);
            branchFaxLabel.setText(brFax);
        } catch (Exception error) {
            System.out.println(error);
        }
    }


    String tmpVenID, tmpVenName, tmpVenCPerson, tmpVenAdd, tmpVenTel, tmpVenFax;
    ArrayList<String> venIDList = new ArrayList<String>(Arrays.asList("ADD VENDOR"));
    @FXML
    ChoiceBox venCBox;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet venResultSet = statement.executeQuery("SELECT venID FROM vendor");
            while (venResultSet.next()) {
                venIDList.add(venResultSet.getString("venID"));
            }
            venCBox.getItems().addAll(venIDList);
            venCBox.setOnAction(this::showVenDetails);
        } catch (Exception error) {
            System.out.println(error);
        }
    }

    String selectedVendor;
    private void showVenDetails(Event event) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");
            selectedVendor = (String) venCBox.getValue();
            if (selectedVendor != "ADD VENDOR") {
                String sql = "SELECT * FROM vendor WHERE venID = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, selectedVendor);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                tmpVenID =  resultSet.getString("venID");
                tmpVenName =  resultSet.getString("venName");
                tmpVenCPerson =  resultSet.getString("venCPerson");
                tmpVenAdd =  resultSet.getString("venAdd");
                tmpVenTel =  resultSet.getString("venTel");
                tmpVenFax =  resultSet.getString("venFax");
                VenIDTxtField.setText(tmpVenID);
                VenNameTxtField.setText(tmpVenName);
                VenCPersonTxtField.setText(tmpVenCPerson);
                VenAddTxtField.setText(tmpVenAdd);
                VenTelTxtField.setText(tmpVenTel);
                VenFaxTxtField.setText(tmpVenFax);
                VenIDTxtField.setEditable(false);
                VenNameTxtField.setEditable(false);
                VenCPersonTxtField.setEditable(false);
                VenAddTxtField.setEditable(false);
                VenTelTxtField.setEditable(false);
                VenFaxTxtField.setEditable(false);
            } else {
                VenIDTxtField.setText("");
                VenNameTxtField.setText("");
                VenCPersonTxtField.setText("");
                VenAddTxtField.setText("");
                VenTelTxtField.setText("");
                VenFaxTxtField.setText("");
                VenIDTxtField.setEditable(true);
                VenNameTxtField.setEditable(true);
                VenCPersonTxtField.setEditable(true);
                VenAddTxtField.setEditable(true);
                VenTelTxtField.setEditable(true);
                VenFaxTxtField.setEditable(true);
            }
        } catch (Exception error) {
            System.out.println(error);
        }
    }

    public void viewPOTable (ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PurchaseTable.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception error) {
            System.out.println(error);
        }
    }

    public void viewItemTable (ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemTable.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception error) {
            System.out.println(error);
        }
    }

    public void viewBrVenTable (ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BrVenTable.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception error) {
            System.out.println(error);
        }
    }

    public void viewOrderTable (ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderTable.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception error) {
            System.out.println(error);
        }
    }

}
