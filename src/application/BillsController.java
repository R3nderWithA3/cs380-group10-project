package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class BillsController implements Initializable {
	DBFunctions db = new DBFunctions();
	private int activeUser = db.getActiveUser();
	
	@FXML
	private Button newBtn;
	@FXML
	private Button goBack;
	@FXML
	private Label firstName;
	@FXML
	public ListView<String> billList;
	
	public void fillList(Connection c) {
		ArrayList<String> bills = db.getUserBills(c, activeUser);
		for(int i=0; i<bills.size(); i++) {
			billList.getItems().add(bills.get(i));
		}
	}
	
	public void toUser() throws Exception{
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		this.switchToUser(c);
		
	}
	
	public void switchToUser(Connection c) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/User_View.fxml"));
		Scene scene = new Scene(root, 600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage stage = (Stage) newBtn.getScene().getWindow();
	    stage.close();
	}
	
	public void newBill() throws Exception{
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		this.switchToNew(c);
	}
	
	public void switchToNew(Connection c) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/User_NewBill.fxml"));
		Scene scene = new Scene(root, 600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
			this.fillList(c);
			firstName.setText(db.getUser(c,activeUser));
		} catch(Exception e) {
			System.out.println(e);
		}
	}


}
