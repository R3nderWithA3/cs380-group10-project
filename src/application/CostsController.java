package application;

import java.util.*;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CostsController implements Initializable {
	DBFunctions db = new DBFunctions();
	private int activeUser = db.getActiveUser();
	
	@FXML
	public Label firstName;
	@FXML
	public Button toMain;
	@FXML 
	public Button toNewBtn;
	@FXML
	public ListView<String> costList;
	
    
	public void fillList(Connection c) {
		ArrayList<String> costs = db.getUserCosts(c, activeUser);
		System.out.println("Made it here");
		for(int i=0; i<costs.size(); i++) {
			costList.getItems().add(costs.get(i));
		}
	}
	
	public void toUser() throws Exception{
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		this.switchToUser(c);
	}
	
	public void toNew() throws Exception{
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		this.switchToNew(c);
	}
	
	public void switchToNew(Connection c) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/User_NewCost.fxml"));
		Scene scene = new Scene(root, 600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void switchToUser(Connection c) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/User_View.fxml"));
		Scene scene = new Scene(root, 600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage stage = (Stage) firstName.getScene().getWindow();
	    stage.close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
			firstName.setText(db.getUser(c,activeUser));
			this.fillList(c);
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
