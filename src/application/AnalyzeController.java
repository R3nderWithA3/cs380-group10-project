package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AnalyzeController implements Initializable {
	DBFunctions db = new DBFunctions();
	private int activeUser = db.getActiveUser();
	
	@FXML
	private Button showDataBtn;
	@FXML
	private LineChart<Integer, Integer> lineChart;
	
	@FXML
	private void showData(ActionEvent event) throws Exception{
		XYChart.Series<Integer, Integer> series = new Series<>(); 
		series.setName("No of schools in an year"); 
		series.getData().add(new XYChart.Data<Integer, Integer>(100, 15)); 
		series.getData().add(new XYChart.Data<Integer, Integer>(500, 30)); 
		series.getData().add(new XYChart.Data<Integer, Integer>(1000, 60)); 
		series.getData().add(new XYChart.Data<Integer, Integer>(1500, 120)); 
		series.getData().add(new XYChart.Data<Integer, Integer>(1800, 240)); 
		series.getData().add(new XYChart.Data<Integer, Integer>(1900, 300));
		lineChart.getData().add(series); 
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
		Stage stage = (Stage) showDataBtn.getScene().getWindow();
	    stage.close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		} catch(Exception e) {
			System.out.println(e);
		}
	}


}
