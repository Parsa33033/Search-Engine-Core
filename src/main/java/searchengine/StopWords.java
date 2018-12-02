package searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StopWords {
	
	public Set<String> stopWords = new HashSet<String>();
	public File stopWordsFile = new File("stopwords.txt");
	
	
	/**
	 * constructor:
	 * gets input from stopWordsFile and append each word to set of stopWords
	 */
	public StopWords() {
		
	}	
	
	/**
	 * getting the term(sentence) and return a string that doesn't contain
	 * any word in the set stopWords
	 * @param term
	 * @return
	 */
	public String filteredString(String term) {
		
	}
}