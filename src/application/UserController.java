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

public class UserController implements Initializable{
	DBFunctions db = new DBFunctions();
	
	private int activeUser = db.getActiveUser(); //id of user who is currently logged in
	private double totalIncome;
	private double totalCosts;
	private double moneyNet;
	
	@FXML
	public Label firstName;
	@FXML
	public Label incomeTotal;
	@FXML
	public Label costTotal;
	@FXML
	public Label netMoney;
	@FXML 
	public Button costButton;
	@FXML 
	public Button billsButton;
	@FXML 
	public Button budgetButton;
	@FXML 
	public Button analyzeButton;

	
	public void toCosts() throws Exception {
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		this.switchToCosts(c);
	}
	
	public void toBills() throws Exception{
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		this.switchToBills(c);
	}
	
	public void toBudgets() throws Exception{
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		this.switchToBudgets(c);
	}
	
	public void toAnalyze() throws Exception{
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		this.switchToAnalyze(c);
	}
	
	public void switchToCosts(Connection c) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/User_Costs.fxml"));
		Scene scene = new Scene(root, 600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage stage = (Stage) costButton.getScene().getWindow();
	    stage.close();
	}
	
	public void switchToBills(Connection c) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/User_Bills.fxml"));
		Scene scene = new Scene(root, 600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage stage = (Stage) costButton.getScene().getWindow();
	    stage.close();
	}
	
	public void switchToBudgets(Connection c) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/User_Budgets.fxml"));
		Scene scene = new Scene(root, 600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage stage = (Stage) costButton.getScene().getWindow();
	    stage.close();
	}
	
	public void switchToAnalyze(Connection c) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/User_Analytics.fxml"));
		Scene scene = new Scene(root, 600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage stage = (Stage) costButton.getScene().getWindow();
	    stage.close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
			System.out.println("Active User: " + activeUser);
			firstName.setText(db.getUser(c,activeUser));
			totalIncome = db.getTotalIncome(c, activeUser);
			incomeTotal.setText("$" + Double.toString(totalIncome));
			totalCosts = db.getTotalCosts(c, activeUser);
			costTotal.setText("$" + Double.toString(totalCosts));
			moneyNet = totalIncome - totalCosts;
			if(moneyNet < 0) {
				String money = Double.toString(moneyNet).substring(1);
				netMoney.setText("-$" + money);
			} else {
				netMoney.setText("$" + Double.toString(moneyNet));
			}
		} catch (Exception e) {
	    	System.out.println(e);
	    }
	
	}
}
