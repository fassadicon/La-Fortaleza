package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ItemTableController extends Main implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML TableView <itemTableData> itemTable;
    @FXML TableColumn<itemTableData, String> itemIDColumn;
    @FXML TableColumn <itemTableData, String> itemNameColumn;
    @FXML TableColumn <itemTableData, String> itemDescColumn;
    @FXML TableColumn <itemTableData, String> itemUnitColumn;
    @FXML TableColumn <itemTableData, Double> itemPPUColumn;

    public ObservableList<itemTableData> itemTableData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from item");
            while (resultSet.next()) {
                itemTableData.add(new itemTableData(resultSet.getString("itemID"),
                        resultSet.getString("itemName"),
                        resultSet.getString("itemDesc"),
                        resultSet.getString("itemUnit"),
                        resultSet.getDouble("itemPricePerUnit")));
            }
        } catch(Exception error) {
            System.out.println(error);
        }
        itemIDColumn.setCellValueFactory(new PropertyValueFactory<itemTableData, String>("itemID"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<itemTableData, String>("itemName"));
        itemDescColumn.setCellValueFactory(new PropertyValueFactory<itemTableData, String>("itemDesc"));
        itemUnitColumn.setCellValueFactory(new PropertyValueFactory<itemTableData, String>("itemUnit"));
        itemPPUColumn.setCellValueFactory(new PropertyValueFactory<itemTableData, Double>("itemPPU"));
        itemTable.setItems(itemTableData);
    }

    public void back (ActionEvent e) {
        try {
            root = FXMLLoader.load(getClass().getResource("FormDetails.fxml"));
            stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}
