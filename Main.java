package application;
	
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.geometry.*;


public class Main extends Application {
	
	//Variables
	private Connection connection;
	public Button button;
	public TextField enterURL;
	public TextField startLine;
	public TextField endLine;
	private TextArea textArea;
	
	public List<String> userInput = new ArrayList<String>();
	public List<String> result = new ArrayList<String>();
	public List<String> list = new ArrayList<String>();

	//Construct GUI
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Word Occurences");
		
		button = new Button("Submit");
		button.setOnAction(buttonHandler);
		
		
		//create a text field
		enterURL = new TextField();
		startLine = new TextField();
		endLine = new TextField();
		
		Label l = new Label("Enter the URL for the html file you would like to search:");
		Label start = new Label("Enter the first line of the text:");
		Label end = new Label("Enter the last line of the text:");
		
		textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setPrefHeight(375);
		
		//create a tile pane
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10,20,20,20));
		vbox.setSpacing(5);
		
		//add nodes
		vbox.getChildren().addAll(l, enterURL, start, startLine, end, endLine, button, textArea);

		//create the scene
		Scene scene = new Scene(vbox);
		
		//set the scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	//Button Click!!
	EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
	@Override
	public void handle(ActionEvent event) {
		userInput.clear();
		textArea.setText("");
		userInput.add(enterURL.getText());
		userInput.add(startLine.getText());
		userInput.add(endLine.getText());
		try {
			TextAnalyzer.textAnalyzer(userInput);
		} catch (IOException e) {
			e.printStackTrace();
			textArea.setText("Please enter a valid URL");
		}
		
		try {
			ResultSet results = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wordOccurences",
					"root","password");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT word, COUNT(*) FROM word GROUP BY word ORDER BY COUNT(*) DESC LIMIT 20;");
			while(resultSet.next()) {

				textArea.appendText(resultSet.getString(1) + "...." + resultSet.getString(2) + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	};

	
	public static void main(String[] args) {
		launch(args);
	}
	
}

