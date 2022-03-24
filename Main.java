package application;
	
import java.io.*;
import java.util.*;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.geometry.*;


public class Main extends Application implements EventHandler<ActionEvent> {
		
	public Button button;
	public TextField enterURL;
	public TextField startLine;
	public TextField endLine;
	private TextArea textArea;
	
	public List<String> userInput = new ArrayList<String>();
	public List<String> result = new ArrayList<String>();
	public List<String> list = new ArrayList<String>();

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Word Occurences");
		
		button = new Button("Submit");
		button.setOnAction(this);
		
		//create a text field
		enterURL = new TextField();
		startLine = new TextField();
		endLine = new TextField();
		
		Label l = new Label("Enter the URL for the html file you would like to search:");
		Label start = new Label("Enter the first line of the text:");
		Label end = new Label("Enter the last line of the text:");
		
		textArea = new TextArea();
		textArea.setEditable(false);
		
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
	
	@Override
	public void handle(ActionEvent event) {
		userInput.add(enterURL.getText());
		userInput.add(startLine.getText());
		userInput.add(endLine.getText());
		try {
			result = TextAnalyzer.textAnalyzer(userInput);
		} catch (IOException e) {
			e.printStackTrace();
			textArea.setText("Please enter a valid URL");
		}
		
		list = TextAnalyzer.outputResults();
		
		
        for (int i = 1; i <= 20; i++) {
        	textArea.appendText(String.valueOf(i) + ". " + list.get(i-1) + "\n");
        }
        
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}

