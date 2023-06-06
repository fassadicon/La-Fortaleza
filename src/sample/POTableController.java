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

public class POTableController extends Main implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TableView <POData> tableView;
    @FXML private TableColumn <POData, String> PONumColumn;
    @FXML private TableColumn <POData, String> createDateColumn;
    @FXML private TableColumn <POData, String> delDateColumn;
    @FXML private TableColumn <POData, String> ToPColumn;
    @FXML private TableColumn <POData, String> MoPColumn;
    @FXML private TableColumn <POData, Double> totAmountColumn;
    @FXML private TableColumn <POData, String> valDateColumn;
    @FXML private TableColumn <POData, String> prepByColumn;
    @FXML private TableColumn <POData, String> appByColumn;
    @FXML private TableColumn <POData, String> venIDColumn;
    @FXML private TableColumn <POData, Integer> branchNoColumn;

    public ObservableList<POData> poData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from purchaseorder");
            while (resultSet.next()) {
                poData.add(new POData(resultSet.getString("POnum"),
                        resultSet.getString("createDate"),
                        resultSet.getString("deliverDate"),
                        resultSet.getString("termsOfPay"),
                        resultSet.getString("modeOfPay"),
                        resultSet.getString("validDate"),
                        resultSet.getDouble("totalAmount"),
                        resultSet.getString("preparedBy"),
                        resultSet.getString("approvedBy"),
                        resultSet.getString("venID"),
                        resultSet.getInt("branchNo")));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        PONumColumn.setCellValueFactory(new PropertyValueFactory<POData, String>("POnum"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<POData, String>("createDate"));
        delDateColumn.setCellValueFactory(new PropertyValueFactory<POData, String>("deliverDate"));
        ToPColumn.setCellValueFactory(new PropertyValueFactory<POData, String>("termsOfPay"));
        MoPColumn.setCellValueFactory(new PropertyValueFactory<POData, String>("modeOfPay"));
        totAmountColumn.setCellValueFactory(new PropertyValueFactory<POData, Double>("totalAmount"));
        valDateColumn.setCellValueFactory(new PropertyValueFactory<POData, String>("validDate"));
        prepByColumn.setCellValueFactory(new PropertyValueFactory<POData, String>("preparedBy"));
        appByColumn.setCellValueFactory(new PropertyValueFactory<POData, String>("approvedBy"));
        venIDColumn.setCellValueFactory(new PropertyValueFactory<POData, String>("venID"));
        branchNoColumn.setCellValueFactory(new PropertyValueFactory<POData, Integer>("branchNo"));
        tableView.setItems(poData);
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
