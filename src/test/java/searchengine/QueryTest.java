package searchengine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class QueryTest implements IndexerInterface{
	Query q = new Query();
	
	@Test
	public void queryTest() {
		Map<String, Double> m = new HashMap<String, Double>();
		m.put("word1", 1.0);
		m.put("word2", 0.5);
		m.put("word3", 0.1);
		
		this.fwdIndex.put("A", m);
		this.fwdIndex.put("B", m);
		
		Map<String, Double> n = new HashMap<String, Double>();
		n.put("A", 1.0);
		n.put("B", 1.0);
		this.invertedIndex.put("word1", n);
		
		n = new HashMap<String, Double>();
		n.put("A", 0.5);
		n.put("B", 0.5);
		this.invertedIndex.put("word2", n);

		n = new HashMap<String, Double>();
		n.put("A", 0.1);
		n.put("B", 0.1);
		this.invertedIndex.put("word3", n);
		
		q.query = "word1 word2";
		q.init(q.query);
		q.query();
		Map<String,Double> temp = new HashMap<String,Double>();
		temp.put("A", 1.5);
		temp.put("B", 1.5);
		assert(temp.hashCode() == q.searchResult.hashCode());
		
	}
	
	@Test
	public void intersectionTest() {
		Set<String> s1 = new HashSet<String>();
		Set<String> s2 = new HashSet<String>();
		s1.add("1");
		s1.add("2");
		s1.add("3");
		s2.add("2");
		
		Set<String> i = new HashSet<String>();
		i.add("2");
		
		assert(q.intersection(s1, s2).hashCode() == i.hashCode());
		
	}
	
	@Test
	public void sortByValueTest() {
		Map<String, Double> m = new HashMap<String, Double>();
		m.put("A", 2.0);
		m.put("B", 3.0);
		m.put("C", 1.0);
		Map<String, Double> n = new HashMap<String, Double>();
		n.put("B", 3.0);
		n.put("A", 2.0);
		n.put("C", 1.0);
		
		assert(q.sortByValue(m).toString().hashCode() == "{B=3.0, A=2.0, C=1.0}".hashCode());
	}
	
	
	
}
