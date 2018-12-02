package searchengine;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * uses Text Transformer methods to create Index from crawled pages
 * @author Parsa
 *
 */
public class TextTransformer implements IndexerInterface{
	
	PorterStemmer stemmer = new PorterStemmer();
	Map<String, Map<String, Integer>> docWordCountIndex = new HashMap<String, Map<String, Integer>>();
	TFIDF tfidf = new TFIDF();
	StopWords stopWords = new StopWords();
	
	/**
	 * runs stemming
	 * creates fwd Index and inverted index
	 */
	public void run() {
		stemDocWordCount();
		createFWDIndex();
		createInvertedIndex();	
	}
	
	/**
	 * creates word count from a given document and 
	 * uses NLP stemming and stopping algorithms from filterString() method
	 * to filter words and sentences
	 */
	public void stemDocWordCount() {
		
	}
	
	/**
	 * uses tfidf and docWordCountIndex created from stemDocWordCount() 
	 * to populate IndexerInterface fwdIndex
	 */
	public void createFWDIndex() {
		
	}
	
	/**
	 * uses fwdIndex and urlPageRank from IndexerInterface to
	 * populate invertedIndex from IndexerInterface
	 */
	public void createInvertedIndex() {
		
	}
	
	/**
	 * NLP stopping and stemming of String s 
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
	
	/**
	 * gets pure URL (protocol://hostname/) from a given url string
	 * @param url
	 * @return
	 */
	public String getUrlHost(String url) {
		try {
			URL myUrl = new URL(url);
			return myUrl.getProtocol()+"://"+myUrl.getHost()+"/";
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
