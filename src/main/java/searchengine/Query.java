package searchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Parsa
 *
 */
public class Query implements IndexerInterface, Runnable{
	
	private StopWords stopWords;
	private PorterStemmer stemmer;
	static String query;
	Map<String, Double> searchResult;
	private Map<String, Map<String, Double>> invertedIndexCopy;
	private Map<String, Map<String, Double>> fwdIndexCopy;
	
	/**
	 *  
	 */
	public Query() {
		this.stopWords = new StopWords();
		this.stemmer = new PorterStemmer();
	}
	
	/* runs query for search results as a thread
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while(true) {
			this.query = "";
			System.out.println("Search:");
			Scanner input = new Scanner(System.in);
			init(input.nextLine());
			
		}
	}
	
	/**
	 * 
	 */
	public void init(String input) {
		searchResult = new HashMap<String, Double>();
		invertedIndexCopy = new HashMap<String, Map<String, Double>>(this.invertedIndex);
		fwdIndexCopy = new HashMap<String, Map<String, Double>>(this.fwdIndex);
		this.query = this.filterString(input);
		if(!this.query.isEmpty())
			query();	
	}
	
	/**
	 * uses a copy of invertedIndex of IndexerInterface in invertedIndexCopy
	 * to query the results
	 */
	public void query() {
		
		
	}
	
	/**
	 * finds the intersection between two sets
	 * @param a
	 * @param b
	 * @return
	 */
	public Set<String> intersection(Set<String> a, Set<String> b) {
	 
	}
	
	/**
	 * sorts the map given in decreasing order
	 * @param unsortMap
	 * @return
	 */
	public Map<String, Double> sortByValue(Map<String, Double> unsortMap) {

    }
	
	/**
	 * uses stopping and stemming algorithms to filter string
	 * @param s
	 * @return
	 */
	public String filterString(String s) {
		s = s.toLowerCase();
		s = s.replaceAll("(\\.+)|(\\,+)|(\\=+)|(\\-+)|(\\_+)|(\\>+)|(\\<+)", "");
		s = stopWords.filteredString(s);
		s = stemSentence(s);
		return s;
	}
	
	/**
	 * uses Porter Stemmer to Stem string s
	 * @param s
	 * @return
	 */
	public String stemSentence(String s) {
		s = s.toLowerCase();
		String[] str = s.split("\\s+");
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i<str.length ; i++) {
			sb.append(" "+stemmer.stem(str[i])+" ");
		}
		return sb.toString();
	}
	
	/**
	 * @param s
	 * @return
	 */
	public String stemString(String s) {
		return stemmer.stem(s.toLowerCase());
	}
}
