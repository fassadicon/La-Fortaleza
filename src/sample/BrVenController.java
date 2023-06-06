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

public class BrVenController extends Main implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML TableView <branchData> brTable;
    @FXML TableColumn <branchData, Integer> brNoColumn;
    @FXML TableColumn <branchData, String> brCPersonColumn;
    @FXML TableColumn <branchData, String> brAddColumn;
    @FXML TableColumn <branchData, String> brTelColumn;
    @FXML TableColumn <branchData, String> brFaxColumn;
    public ObservableList<branchData> brTableData = FXCollections.observableArrayList();

    @FXML TableView<venTableData> venTable;
    @FXML TableColumn <venTableData, String> venIDColumn;
    @FXML TableColumn <venTableData, String> venNameColumn;
    @FXML TableColumn <venTableData, String> venAddColumn;
    @FXML TableColumn <venTableData, String> venCPersonColumn;
    @FXML TableColumn <venTableData, String> venTelColumn;
    @FXML TableColumn <venTableData, String> venFaxColumn;
    public ObservableList<venTableData> venTableDataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet rs1 = statement.executeQuery("select * from branch");
            while (rs1.next()) {
                brTableData.add(new branchData(rs1.getInt("branchNo"),
                        rs1.getString("branchCPerson"),
                        rs1.getString("branchAdd"),
                        rs1.getString("branchTel"),
                        rs1.getString("branchFax")));
            }
            ResultSet rs2 = statement.executeQuery("select * from vendor");
            while (rs2.next()) {
                venTableDataList.add(new venTableData(rs2.getString("venID"),
                        rs2.getString("venName"),
                        rs2.getString("venAdd"),
                        rs2.getString("venCPerson"),
                        rs2.getString("venTel"),
                        rs2.getString("venFax")));
            }
        } catch (Exception error) {
            System.out.println(error);
        }
        brNoColumn.setCellValueFactory(new PropertyValueFactory<branchData, Integer>("brNo"));
        brCPersonColumn.setCellValueFactory(new PropertyValueFactory<branchData, String>("brCPerson"));
        brAddColumn.setCellValueFactory(new PropertyValueFactory<branchData, String>("brAdd"));
        brTelColumn.setCellValueFactory(new PropertyValueFactory<branchData, String>("brTel"));
        brFaxColumn.setCellValueFactory(new PropertyValueFactory<branchData, String>("brFax"));
        brTable.setItems(brTableData);
        venIDColumn.setCellValueFactory(new PropertyValueFactory<venTableData, String>("venID"));
        venNameColumn.setCellValueFactory(new PropertyValueFactory<venTableData, String>("venName"));
        venAddColumn.setCellValueFactory(new PropertyValueFactory<venTableData, String>("venAdd"));
        venCPersonColumn.setCellValueFactory(new PropertyValueFactory<venTableData, String>("venCPerson"));
        venTelColumn.setCellValueFactory(new PropertyValueFactory<venTableData, String>("venTel"));
        venFaxColumn.setCellValueFactory(new PropertyValueFactory<venTableData, String>("venFax"));
        venTable.setItems(venTableDataList);
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
