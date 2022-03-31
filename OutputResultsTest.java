package application;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class OutputResultsTest {

	@Test
	void test() throws IOException {
		ArrayList<String> input = new ArrayList<String>();
		input.add("https://www.gutenberg.org/files/503/503-h/503-h.htm");
		input.add("there lived a merchant");
		input.add("and the marriage was celebrated the very next day with the utmost splendor");
		
		TextAnalyzer test = new TextAnalyzer();
		TextAnalyzer.textAnalyzer(input);
		List<String> list1 = test.outputResults();
		List<String> list2 = test.outputResults();
		assertArrayEquals(list1.toArray(), list2.toArray());
	}

}
