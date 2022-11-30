package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewBillController implements Initializable {
	DBFunctions db = new DBFunctions();
	private int activeUser = db.getActiveUser();
	
	@FXML
	private Button toBill;
	@FXML
	private Button createNew;
	@FXML
	private TextField cName;
	@FXML
	private TextField cAmt;

	
	public void toCosts() throws Exception{
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
		this.switchToBill(c);
	}
	
	public void switchToBill(Connection c) throws Exception {
		Stage stage = (Stage) toBill.getScene().getWindow();
	    stage.close();
	}
	
	public void newEntry() throws Exception{
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/subscript", "root", "cs380");
			db.newBill(c, activeUser, Integer.parseInt(cAmt.getText()), cName.getText());
			Stage stage = (Stage) toBill.getScene().getWindow();
		}catch (Exception e) {
			e.printStackTrace();
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
