package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.*;

public class Main extends Application {
	MainController ctrl = new MainController();
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
			Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void Login() throws Exception{
		ctrl.Login();
	}
}
