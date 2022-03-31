package application;

import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class TextAnalyzer {
	
	public static List<String> userText = new ArrayList<String>();
	public static List<String> outputList = new ArrayList<String>();

	//read text from url
	public static List<String> textAnalyzer(List<String> userInput) throws IOException {
		userText.clear();
		outputList.clear();
		
		//declare variables
		Document document;
		int firstLineIndex = 0;
		//List<String> lines = new ArrayList<String>();
		List<String> userText = new ArrayList<String>();
		
		String firstLine = userInput.get(1).toLowerCase();
		String endLine = userInput.get(2).toLowerCase();
		
		//parse html
		document = Jsoup.connect(userInput.get(0)).get();
		

		
		String str = document.toString();
		
		//clean up html tags
		str = str.replaceAll("<br>", "\n");
		
		String[] strSplit = str.split("\n");
		List<String> strList = new ArrayList<String>(Arrays.asList(strSplit));
		
		for (int i = 0; i < strList.size(); i++) {
			String string = strList.get(i);
			string = string.replaceAll("\\<.*?\\>", "").replaceAll("—"," ").replaceAll("[^a-zA-Z ]", "").toLowerCase();
			strList.set(i, string);
		}
		
		//Index of first line
		for(int i = 0; i < strList.size(); i++) {
			if (strList.get(i).contains(firstLine) == true) {
				firstLineIndex = i;
			}
		}
		
		for (int i = firstLineIndex; i < strList.size(); i++) {
			//System.out.println(strList.get(i));
			userText.add(strList.get(i));
			 if (strList.get(i).contains(endLine) == true) {
				break;
			}
		}

		List<String> wordList = new ArrayList<String>();
		for (int i = 0; i < userText.size(); i++) {
			String next = userText.get(i);
			String[] splitString = next.split(" ");
			for (String a : splitString)
				wordList.add(a.replaceAll(" ", ""));
		}
		
		Map<String,Integer> counter = new HashMap<String,Integer>();
		for (String list : wordList)
			counter.put(list, 1 + (counter.containsKey(list) ? counter.get(list) : 0));
		List<String> arrayList = new ArrayList<String>(counter.keySet());
		Collections.sort(arrayList, new Comparator<String>() {
			@Override
			public int compare(String x, String y) {
				return counter.get(y) - counter.get(x);
			}
		});
		
		for (int i = 1; i < 21; i++) {
			//System.out.println(Collections.frequency(wordList, arrayList.get(i)) + " " + arrayList.get(i));
			String frequency = Collections.frequency(wordList, arrayList.get(i)) + " " + arrayList.get(i);
			outputList.add(frequency);
		}
		return outputList;
	}
	
	public static List<String> outputResults() {
		return outputList;
		}

}

