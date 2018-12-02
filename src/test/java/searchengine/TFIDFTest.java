package searchengine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TFIDFTest implements IndexerInterface{
	TextTransformer tt = new TextTransformer();
	Map<String, Map<String, Integer>> docWordCountIndex = new HashMap<String, Map<String, Integer>>();
	@Test
	public void tfidfTest() {
		String doc1 = "This is the test document number one";
		String doc2 = "this Is the Test Document number two";
		String doc3 = "this is test Document number three";
		this.contentRepo.put("doc1", doc1);
		this.contentRepo.put("doc2", doc2);
		this.contentRepo.put("doc3", doc3);
		stemDocWordCount();
		TFIDF tfidf = new TFIDF();
		assert(String.format("%.2f", tfidf.tfidf(docWordCountIndex, "three", "doc3")).hashCode() == "0.18".hashCode());
	}
	
	public void stemDocWordCount() {
		
		for(Map.Entry<String, String> i : this.contentRepo.entrySet()) {
			List<String> content = Arrays.asList(i.getValue().toLowerCase().split("\\s+"));
			Map<String, Integer> wordCount = new HashMap<String, Integer>();
			for(String s : content) {	  
				s = s.toLowerCase();
				wordCount.put(s, (wordCount.get(s)==null)? 1 : wordCount.get(s)+1);
			}
			docWordCountIndex.put(i.getKey(), wordCount);
		}
	}
	
	
	
}


