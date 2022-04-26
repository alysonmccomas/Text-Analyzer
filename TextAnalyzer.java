//https://www.gutenberg.org/files/1065/1065-h/1065-h.htm
//once upon a midnight dreary while i pondered weak and weary
//shall be lifted nevermore

package application;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class TextAnalyzer {
	public static List<String> wordList = new ArrayList<String>();
	public static List<String> userText = new ArrayList<String>();

	/**read text from url*/
	public static List<String> textAnalyzer(List<String> userInput) throws IOException {
		userText.clear();
		
		/**declare variables*/
		Document document;
		int firstLineIndex = 0;
		//List<String> userText = new ArrayList<String>();
		
		String firstLine = userInput.get(1).toLowerCase();
		String endLine = userInput.get(2).toLowerCase();
		
		/**parse html*/
		document = Jsoup.connect(userInput.get(0)).get();
		String str = document.toString();
		
		/**clean up html tags*/
		str = str.replaceAll("<br>", "\n");
		
		String[] strSplit = str.split("\n");
		List<String> strList = new ArrayList<String>(Arrays.asList(strSplit));
		
		for (int i = 0; i < strList.size(); i++) {
			String string = strList.get(i);
			string = string.replaceAll("\\<.*?\\>", "").replaceAll("—"," ").replaceAll("[^a-zA-Z ]", "").toLowerCase();
			strList.set(i, string);
		}
		
		/**Index of first line*/
		for(int i = 0; i < strList.size(); i++) {
			if (strList.get(i).contains(firstLine) == true) {
				firstLineIndex = i;
			}
		}
		
		/**isolate desired text*/
		for (int i = firstLineIndex; i < strList.size(); i++) {
			//System.out.println(strList.get(i));
			userText.add(strList.get(i));
			 if (strList.get(i).contains(endLine) == true) {
				break;
			}
		}

		/**split text into individual words*/
		
		for (int i = 0; i < userText.size(); i++) {
			String next = userText.get(i);
			String[] splitString = next.split(" ");
			String[] removedNull = Arrays.stream(splitString).filter(
					value -> value != null && value.length() > 0)
					.toArray(size -> new String[size]);
			for (String a : removedNull)
				wordList.add(a);
		}
		return wordList;
	}
	
	public void textDatabase(List<String> wordList) {
		/**add words to database*/
		
		Connection connection;
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
			int rowsAffected = statement.executeUpdate("DELETE FROM word");
			for (int i = 0; i < wordList.size(); ++i) {
				String sql = "INSERT INTO word (word) VALUES ('" + wordList.get(i) + "')";
				statement.executeUpdate(sql);
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
	};
}

