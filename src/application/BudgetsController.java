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

public class BudgetsController implements Initializable {
	DBFunctions db = new DBFunctions();
	private int activeUser = db.getActiveUser();
	
	@FXML
	private Button toMain;
	@FXML
	private Button createNew;
	@FXML
	private Label firstName;
	@FXML
	private ListView<String> budgetList;
	
	public void fillList(Connection c) {
		ArrayList<String> budgets = db.getUserBudgets(c, activeUser);
		System.out.println("Made it here");
		for(int i=0; i<budgets.size(); i++) {
			budgetList.getItems().add(budgets.get(i));
		}
	}
	
	public void toNew() throws Exception{
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		this.switchToNew(c);
	}
	
	public void switchToNew(Connection c) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/User_NewBudget.fxml"));
		Scene scene = new Scene(root, 600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
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
		Stage stage = (Stage) toMain.getScene().getWindow();
	    stage.close();
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
