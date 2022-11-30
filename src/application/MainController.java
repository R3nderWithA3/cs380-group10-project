package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MainController {
	DBFunctions db = new DBFunctions();

	private int activeUser = 0; //id of user who is currently logged in
	
	@FXML
	public Label firstName;
	@FXML
	private Label sID;
	@FXML
	private TextField unID;
	@FXML
	private PasswordField passID;
	//@FXML
	//private Button btnSignIn, TransButton, billButton, analyze, goBack, adminButton, ShowData; 
	//@FXML
	//private Label checkTotal;
	//@FXML
	//private Label CheckAccountNum, SaveAccountNum;
	@FXML
	private LineChart<Integer, Integer> lineChart;
	
	@FXML
	//Runs when someone clicks the button on Login.fxml
	//Logs the user in if valid Username/Password combo is found (includes admin account)
	//Returns the users ID, returns 0 if no match is found
	public void Login() throws Exception{
		System.out.println("Made it here");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		System.out.println("Made it here " + unID.getText() + ", " + passID.getText());
		int userID = db.loginSearch(c, unID.getText(), passID.getText());
		System.out.println("User found: " + userID);
		
		if(userID > 0 ) {
			activeUser = userID;
			db.setActiveUser(userID);
			this.switchToUser(c, "User_View");
		} else if(userID == -1) {
			db.setActiveUser(userID);
			this.switchToAdmin(c, "Admin_View");
		} else {
			sID.setText("!!User not found!!");
		}
	}
	
	//Registers a new account
	//Sends error message if username is already taken
	public void Register()throws Exception {
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		String regReturn = db.register(c, unID.getText(), passID.getText());
		if(regReturn.equals("Success")) {
			int userID = db.loginSearch(c, unID.getText(), passID.getText());
			db.setActiveUser(userID);
			activeUser = userID;
			this.switchToUser(c, "User_View");
		} else if (regReturn.equals("ErrorUserNotAllowed")){
			sID.setText("!!Cannot use username '" + unID.getText() + "'!!");
		} else if (regReturn.equals("ErrorNeedPassword")) {
			sID.setText("!!Need a password to register!!");
		}
	}
	
	public void switchToUser(Connection c, String name) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/" + name + ".fxml"));
		Scene scene = new Scene(root, 600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void switchToAdmin(Connection c, String name) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/" + name + ".fxml"));
		Scene scene = new Scene(root, 407,506);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
