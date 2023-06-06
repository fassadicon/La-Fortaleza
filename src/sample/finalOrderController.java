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

public class finalOrderController extends Main implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TableView <finalOrderData> orderTable;
    @FXML TableColumn <finalOrderData, String> PONumColumn;
    @FXML TableColumn <finalOrderData, String> itemIDColumn;
    @FXML TableColumn <finalOrderData, Integer> quantityColumn;
    @FXML TableColumn <finalOrderData, Double> amountColumn;
    public ObservableList<finalOrderData> orderTableData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafortalezapo", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from finalorder");
            while (resultSet.next()) {
                orderTableData.add(new finalOrderData(resultSet.getString("POnum"),
                        resultSet.getString("itemID"),
                        resultSet.getInt("itemQuantity"),
                        resultSet.getDouble("itemAmount")));
            }
        } catch (Exception error) {
            System.out.println(error);
        }
        PONumColumn.setCellValueFactory(new PropertyValueFactory<finalOrderData, String>("POnum"));
       itemIDColumn.setCellValueFactory(new PropertyValueFactory<finalOrderData, String>("itemID"));
       quantityColumn.setCellValueFactory(new PropertyValueFactory<finalOrderData, Integer>("itemQuantity"));
       amountColumn.setCellValueFactory(new PropertyValueFactory<finalOrderData, Double>("itemAmount"));
       orderTable.setItems(orderTableData);
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
