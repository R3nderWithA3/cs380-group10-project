package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewCostController implements Initializable {
	DBFunctions db = new DBFunctions();
	private int activeUser = db.getActiveUser();
	
	@FXML
	private Button toCost;
	@FXML
	private Button createNew;
	@FXML
	private TextField cName;
	@FXML
	private TextField cAmt;
	@FXML
	private TextField cDate;
	
	public void toCosts() throws Exception{
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		this.switchToCosts(c);
	}
	
	public void switchToCosts(Connection c) throws Exception {
		Stage stage = (Stage) toCost.getScene().getWindow();
	    stage.close();
	}
	
	public void newEntry() throws Exception{
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
			db.newCost(c, activeUser, cName.getText(), Integer.parseInt(cAmt.getText()), cDate.getText());
			Stage stage = (Stage) toCost.getScene().getWindow();
		}catch (Exception e) {
			System.out.println(e);
		}
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
