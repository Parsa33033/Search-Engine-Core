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
	 * uses a copy of invertedIndex of IndexerInterface (invertedIndexCopy==> <word,<url,tfidf*PageRank>>) 
	 * to query the results and save the result in searchResult map
	 */
	public void query() {
		String[] str = this.query.split("\\s+");
		Set<String> intersect = new HashSet<String>();
		List<Set<String>> intersects = new ArrayList<Set<String>>();
		for(int i = 0 ; i<str.length ; i++) {
			if(this.invertedIndexCopy.containsKey(str[i]))
				intersects.add(this.invertedIndexCopy.get(str[i]).keySet());        
		}
		if(!intersects.isEmpty()) {
			for(Set<String> s: intersects) {
				if(intersect.isEmpty()) {
					intersect = s;
				}else {
					intersect = this.intersection(intersect, s);
				}
			}
			if(!intersect.isEmpty()) {
				for(String s : intersect) {
					this.searchResult.put(s,(double) 0);
					for(int i = 0 ; i<str.length ; i++) {
						if(this.fwdIndexCopy.containsKey(s) && this.invertedIndexCopy.containsKey(str[i]) && this.invertedIndexCopy.get(str[i]).containsKey(s))
							this.searchResult.put(s, this.searchResult.get(s)+ this.invertedIndexCopy.get(str[i]).get(s));
					}
				}	
			}
		}
		
		searchResult = sortByValue(searchResult);
		for(Map.Entry<String, Double> m : searchResult.entrySet()) {
			System.out.println("---"+m.getKey()+"---"+m.getValue());
		}
		
	}
	
	/**
	 * finds the intersection between two sets
	 * @param a
	 * @param b
	 * @return
	 */
	public Set<String> intersection(Set<String> a, Set<String> b) {
	    // unnecessary; just an optimization to iterate over the smaller set
	    if (a.size() > b.size()) {
	        return intersection(b, a);
	    }
	    Set<String> results = new HashSet<String>();
	    for (String element : a) {
	        if (b.contains(element)) {
	            results.add(element);
	        }
	    }
	    return results;	 
	}
	
	/**
	 * sorts the map given in decreasing order
	 * @param unsortMap
	 * @return
	 */
	public Map<String, Double> sortByValue(Map<String, Double> unsortMap) {
        // Convert Map to List of Map
        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        // Sort list with Collections.sort(), provide a custom Comparator
        // Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
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
