package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class itemController extends Main implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    ArrayList<itemData> itemDataList = new ArrayList<>();

    @FXML
    private Label POLabel;
    public void displayPONum(String PONum) {
        POLabel.setText("Purchase Order No. " + PONum);
    }

    public void submit(ActionEvent e) throws Exception {
        totalAmount = 0.0;
        root = FXMLLoader.load(getClass().getResource("FormDetails.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public String PONum, createDate=null, delDate=null, ToP, MoP, valDate=null, prepBy, appBy, venID;
    int brNo;

    @FXML
    private TextField itemIDTxtField, itemNameTxtField, itemUnitTxtField, itemPPUTxtField;
    @FXML
    private TextField itemQuantTxtField;
    @FXML
    private TextArea itemDescTxtArea;
    @FXML
    private Label POTotalAmountLabel;
    @FXML
    public Label duplicateLabel;

    static String itemID, itemName, itemDesc, itemUnit;
    int itemQuantity;
    public Double amount = 0.0, itemPPU;
    public static Double totalAmount = 0.0;
    public Double emp;
    public int proceed = 1;

    public void addItem(ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FormDetails.fxml"));
            root = loader.load();
            POController POrderController = loader.getController();

            PONum = POrderController.getPONum();
            itemID = itemIDTxtField.getText();

            createDate = POrderController.getCreateDate();
            delDate = POrderController.getDelDate();
            ToP = POrderController.getToP();
            MoP = POrderController.getMoP();
            valDate = POrderController.getValDate();
            prepBy = POrderController.getPrepBy();
            appBy = POrderController.getAppBy();
            venID = POrderController.getVenID();
            brNo = POrderController.getbrNo();


            itemName = itemNameTxtField.getText();
            itemDesc = itemDescTxtArea.getText();
            itemDescTxtArea.setWrapText(true);
            itemQuantity = Integer.parseInt(itemQuantTxtField.getText());
            itemUnit = itemUnitTxtField.getText();
            itemPPU = Double.parseDouble(itemPPUTxtField.getText());
            amount = itemQuantity * itemPPU;
            validate();
            validateNum();

            if (proceed == 1 && nullCatch == 1) {
                totalAmount += amount;
                itemDataList.add(new itemData(itemID, itemName, itemDesc, itemQuantity, itemUnit, itemPPU, amount));
                POTotalAmountLabel.setText(Double.toString(totalAmount));
                emp = Double.parseDouble(POTotalAmountLabel.getText());
                itemDataObservableList.add(count, new itemData(itemID, itemName, itemDesc, itemQuantity, itemUnitTxtField.getText(), itemPPU, amount));
                itemDataTableView.setItems(getItems());
                new POInput(PONum, createDate, delDate,ToP, MoP, valDate, emp, prepBy, appBy, venID, brNo);
                new itemInput(itemID, itemName, itemDesc, itemUnit, itemPPU);
                new orderInput(PONum, itemID, itemQuantity, amount);
            }
        } catch (NumberFormatException error) {
            itemQuantTxtField.setStyle("-fx-border-color: red;");
            itemPPUTxtField.setStyle("-fx-border-color: red;");
            nullWarning.setText("Invalid Input in Quantity and/or Item PPU Text Fields");
            proceed = 0;
        } catch (Exception error) {
        }
    }

    @FXML Label nullWarning;
    int nullCatch = 1;
    public void validate () {
        try {
            if (itemIDTxtField.getText().length() == 0) {
                itemIDTxtField.setStyle("-fx-border-color: red;");
                nullWarning.setText("Item ID is Null");
                nullCatch = 0;
            }
            else if (itemQuantTxtField.getText().length() == 0) {
                itemQuantTxtField.setStyle("-fx-border-color: red;");
                nullWarning.setText("Item Quantity is Null");
                nullCatch = 0;
            }
            else if (itemPPUTxtField.getText().length() == 0) {
                itemPPUTxtField.setStyle("-fx-border-color: red;");
                nullWarning.setText("Item Price Per Unit is Null");
                nullCatch = 0;
            }
            else {
                nullWarning.setText(null);
                itemIDTxtField.setStyle(null);
                itemQuantTxtField.setStyle(null);
                itemPPUTxtField.setStyle(null);
                nullCatch = 1;
            }
        } catch (Exception error) {
            System.out.println(error);
        }
    }
    public void validateNum() {
        String verifyLogin = "SELECT COUNT(1) FROM finalorder WHERE POnum = '" + PONum + "' AND itemID = '" + itemIDTxtField.getText() + "'";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    itemIDTxtField.setStyle("-fx-border-color: red;");
                    duplicateLabel.setText("This ItemID have already been used.");
                    proceed = 0;
                } else {
                    itemIDTxtField.setStyle(null);
                    duplicateLabel.setText(null);
                    proceed = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML TableView<itemData> itemDataTableView;
    @FXML TableColumn<itemData, String> IDcolumn;
    @FXML TableColumn<itemData, String> namecolumn;
    @FXML TableColumn<itemData, String> desccolumn;
    @FXML TableColumn<itemData, Integer> quantcolumn;
    @FXML TableColumn<itemData, String> unitcolumn;
    @FXML TableColumn<itemData, Double> ppucolumn;
    @FXML TableColumn<itemData, Double> amountcolumn;

    int count = 0;
    ObservableList<itemData> itemDataObservableList = FXCollections.observableArrayList();
    public ObservableList<itemData> getItems() {
        count++;
        return itemDataObservableList;
    }

    String tmpitemID, tmpitemName, tmpitemDesc, tmpitemUnit;
    double tmpitemPPU;
    ArrayList<String> itemIDList = new ArrayList<String>(Arrays.asList("NEW ITEM"));
    @FXML
    ChoiceBox itemCBox;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            IDcolumn.setCellValueFactory(new PropertyValueFactory<itemData, String>("itemID"));
            namecolumn.setCellValueFactory(new PropertyValueFactory<itemData, String>("itemName"));
            desccolumn.setCellValueFactory(new PropertyValueFactory<itemData, String>("itemDesc"));
            quantcolumn.setCellValueFactory(new PropertyValueFactory<itemData, Integer>("itemQuantity"));
            ppucolumn.setCellValueFactory(new PropertyValueFactory<itemData, Double>("itemPPU"));
            amountcolumn.setCellValueFactory(new PropertyValueFactory<itemData, Double>("itemAmount"));

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet itemResultSet = statement.executeQuery("SELECT itemID FROM item");
            while (itemResultSet.next()) {
                itemIDList.add(itemResultSet.getString("itemID"));
            }
            itemCBox.getItems().addAll(itemIDList);
            itemCBox.setOnAction(this::showItemDetails);
        } catch (Exception error) {
            System.out.println(error);
        }
    }

    String selectedItem;
    private void showItemDetails(Event event) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");
            selectedItem = (String) itemCBox.getValue();
            if (selectedItem != "NEW ITEM") {
                String sql = "SELECT * FROM item WHERE itemID = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, selectedItem);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                tmpitemID =  resultSet.getString("itemID");
                tmpitemName =  resultSet.getString("itemName");
                tmpitemDesc =  resultSet.getString("itemDesc");
                tmpitemUnit =  resultSet.getString("itemUnit");
                tmpitemPPU =  resultSet.getDouble("itemPricePerUnit");
                itemIDTxtField.setText(tmpitemID);
                itemNameTxtField.setText(tmpitemName);
                itemDescTxtArea.setText(tmpitemDesc);
                itemUnitTxtField.setText(tmpitemUnit);
                itemPPUTxtField.setText(String.valueOf(tmpitemPPU));
                itemIDTxtField.setEditable(false);
                itemNameTxtField.setEditable(false);
                itemDescTxtArea.setEditable(false);
                itemDescTxtArea.setWrapText(true);
                itemUnitTxtField.setEditable(false);
                itemPPUTxtField.setEditable(false);
            } else {
                itemIDTxtField.setText("");
                itemNameTxtField.setText("");
                itemDescTxtArea.setText("");
                itemDescTxtArea.setWrapText(true);
                itemUnitTxtField.setText("");
                itemPPUTxtField.setText("");
                itemIDTxtField.setEditable(true);
                itemNameTxtField.setEditable(true);
                itemDescTxtArea.setEditable(true);
                itemUnitTxtField.setEditable(true);
                itemPPUTxtField.setEditable(true);
            }
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}
