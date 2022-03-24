package application;

import java.io.*;
import java.net.URL;
import java.util.*;

public class TextAnalyzer {
	
	public static List<String> AllLines = new ArrayList<String>();
	public static List<String> cleanLines = new ArrayList<String>();
	public static List<String> lines = new ArrayList<String>();
	public static List<String> wordList = new ArrayList<String>();
	public static List<String> noDuplicates = new ArrayList<String>();

	public static List<String> textAnalyzer(List<String> userInput) throws IOException{
		
		//read text from url
		URL url = new URL(userInput.get(0));
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		
		//declare variables

		String firstLine = userInput.get(1);
		String endLine = userInput.get(2);
		firstLine = firstLine.toLowerCase();
		endLine = endLine.toLowerCase();
		
		//Read File
		String text = reader.readLine();
		while (text != null) {
		 AllLines.add(text);
		 text = reader.readLine();
		 }
		
		//Remove html characters
		for (int i = 0; i < AllLines.size(); i++) {
			String line =  AllLines.get(i).toLowerCase();
			String cL1 = line.replaceAll("\\<.*?\\>", "");
			String cL2 = cL1.replaceAll("[â€œ™!?.,-;]", "");
			String cL3 = cL2.replaceAll("&mdash", " ");
			String cL4 = cL3.replaceAll("&mdash;", " ");
			String cL5 = cL4.replaceAll("[\\;,]", "");
			String cL6 = cL5.replaceAll("\"", "");
			String cleanLine = cL6.replace("\n", "").replace("\r", "");
			
			cleanLines.add(cleanLine);
			}
		   
		   //Isolate desired lines of text
		   for (int i = (cleanLines.indexOf(firstLine)); i < cleanLines.indexOf(endLine); i++) {
				   lines.add(cleanLines.get(i));
			   }
		   
		   //split lines into words
		  for (int i = 0; i < lines.size(); i++) {
			  String str = lines.get(i);
			  String[] strSplit = str.split(" ");
			  for (String a : strSplit)
				 wordList.add(a);	  
		  }
		  for (int i = 0; i < wordList.size(); i++) {
			  if(wordList.get(i).matches("[a-z]") == true) {
				  
			  }else{
				  String noChar = wordList.get(i).replaceAll("[^a-z]", "");
				  wordList.remove(i);
				  wordList.add(noChar);
			  }
		  }
		  
		  //sort words in order of descending frequency
		  int n = wordList.size();
		  String temp;
	        for (int i = 0; i < n - 1; i++) {
	            for (int j = 0; j < n - i - 1; j++)
	                if (Collections.frequency(wordList, wordList.get(j)) < Collections.frequency(wordList, wordList.get(j + 1))) {
	                	temp = wordList.get(j);
	                    wordList.set(j, wordList.get(j + 1));
	                    wordList.set((j + 1),temp);
	                }	
	        }
		  
	      //remove duplicates from output list
		  for (int m = 0; m < wordList.size(); m++){
				String currentWord = wordList.get(m);
				boolean checkDuplicates = noDuplicates.contains(currentWord);
			if (checkDuplicates == false ){
				noDuplicates.add(currentWord);
			}
		  }
		  return noDuplicates;
	}
	public static List<String> outputResults() {
		List<String> list = new ArrayList<String>();
		String word;
		int count;
		String wordCount;
		
	for (int i = 1; i < 10; i++) {
		word = noDuplicates.get(i);
		count = Collections.frequency(wordList, noDuplicates.get(i));
		wordCount = " " + word + " " + String.valueOf(count);
		list.add(wordCount);}
	for (int i = 10; i <= 20; i++) {
		word = noDuplicates.get(i);
		count = Collections.frequency(wordList, noDuplicates.get(i));
		wordCount = word + " " + String.valueOf(count);
		list.add(wordCount);
	  	}
	return list;
	}

}



	
	

