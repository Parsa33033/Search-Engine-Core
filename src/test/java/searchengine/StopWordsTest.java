package searchengine;

import org.junit.Test;

public class StopWordsTest {
	StopWords stopWords;
	@Test
	public void stopWordsTest() {
		stopWords = new StopWords();
		assert("test  cleaning  stopwords  like".hashCode() == stopWords.filteredString("This is a test for cleaning the stopwords like this").trim().hashCode());
	}

}
