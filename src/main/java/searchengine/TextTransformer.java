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
		for(Map.Entry<String, String> i : this.contentRepo.entrySet()) {
			List<String> content = Arrays.asList(filterString(i.getValue()).toLowerCase().split("\\s+"));
			Map<String, Integer> wordCount = new HashMap<String, Integer>();
			for(String s : content) {	  
				s = s.toLowerCase();
				if(!stemString(s).equals(""))
					wordCount.put(stemString(s), (wordCount.get(s)==null)? 1 : wordCount.get(s)+1);
			}
			docWordCountIndex.put(i.getKey(), wordCount);
		}
	}
	
	/**
	 * uses tfidf and docWordCountIndex created from stemDocWordCount() 
	 * to populate IndexerInterface fwdIndex
	 */
	public void createFWDIndex() {
		double tf_idf = 0;
		for(Map.Entry<String, Map<String, Integer>> m : this.docWordCountIndex.entrySet()) {
			Map<String, Double> tempMap = new HashMap<String, Double>();
			for(Map.Entry<String, Integer> term : m.getValue().entrySet()) {
				 tf_idf = tfidf.tfidf(this.docWordCountIndex, term.getKey().toLowerCase(), m.getKey());
				 tempMap.put(term.getKey(), tf_idf);
			}
			this.fwdIndex.put(m.getKey(), tempMap);
		}
	}
	
	/**
	 * uses fwdIndex and urlPageRank from IndexerInterface to
	 * populate invertedIndex from IndexerInterface
	 */
	public void createInvertedIndex() {
		for(Map.Entry<String, Map<String, Double>> i : this.fwdIndex.entrySet()) {
			for(Map.Entry<String, Double> j : i.getValue().entrySet()) {
				Map<String, Double> temp = new HashMap<String, Double>();
				temp.put(i.getKey(), (double)j.getValue() * (double)this.urlPageRank.get(getUrlHost(i.getKey())));
				if(!this.invertedIndex.containsKey(j.getKey())) {
					this.invertedIndex.put(j.getKey(),temp);					
				}else {
					temp.putAll(this.invertedIndex.get(j.getKey()));
					this.invertedIndex.put(j.getKey(), temp);
				}
			}
		}
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
