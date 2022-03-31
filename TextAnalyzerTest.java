package application;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TextAnalyzerTest {

	@Test
	void test() throws IOException {
		ArrayList<String> input = new ArrayList<String>();
		input.add("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
		input.add("once upon a midnight dreary while i pondered weak and weary");
		input.add("shall be lifted nevermore");
		
		TextAnalyzer test = new TextAnalyzer();
		List<String> list1 = test.textAnalyzer(input);
		List<String> list2 = test.textAnalyzer(input);
		assertArrayEquals(list1.toArray(), list2.toArray());
	}

}
