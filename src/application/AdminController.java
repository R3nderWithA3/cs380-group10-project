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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminController implements Initializable {
	DBFunctions db = new DBFunctions();
	private int activeUser = db.getActiveUser();
	
	@FXML
	private Button toLogin;
	@FXML
	private Button enterUser;
	@FXML
	private TextField userID;
	@FXML
	private ListView<String> acctList;
	
	public void closeAdmin() {
		Stage stage = (Stage) enterUser.getScene().getWindow();
	    stage.close();
	}
	
	public void useAcct() throws Exception{
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		db.setActiveUser(Integer.parseInt(userID.getText()));
		this.switchToUser(c, "User_View");
	}
	
	public void switchToUser(Connection c, String name) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/" + name + ".fxml"));
		Scene scene = new Scene(root, 600,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void fillList(Connection c) throws Exception {
		ArrayList<String> users = db.getRoster(c);
		for(int i=0; i<users.size(); i++) {
			acctList.getItems().add(users.get(i));
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
			this.fillList(c);
		} catch(Exception e) {
			System.out.println(e);
		}
	}

}
