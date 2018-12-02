package searchengine;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TextTransformerTest implements IndexerInterface{
	TextTransformer tt = new TextTransformer();

	@Test
	public void stemDocWordCountTest() {
		String s = "word1 word2 word1";
		this.contentRepo.put("A", s);
		tt.stemDocWordCount();
		assert("{A={word1=2, word2=1}}".hashCode() ==  tt.docWordCountIndex.toString().hashCode());
				
	}
	
	@Test
	public void createFWDIndexTest() {
		tt.docWordCountIndex.put("http://A/", new HashMap<String, Integer>(){{put("word1",2);put("word2",1);}});
		tt.docWordCountIndex.put("http://B/", new HashMap<String, Integer>(){{put("word1",2);put("word2",1);}});
		tt.createFWDIndex();
		assert("{http://B/={word1=0.0, word2=0.0}, http://A/={word1=0.0, word2=0.0}}".hashCode() == this.fwdIndex.toString().hashCode());
	}
	
	@Test
	public void createInvertedIndexTest() {
		Map<String, Double> m = new HashMap<String, Double>(){{put("word1",1.0);put("word2",2.0);}};
		this.fwdIndex.put("http://A/", m);
		m = new HashMap<String, Double>(){{put("word1",2.0);put("word2",3.0);}};
		this.fwdIndex.put("http://B/", m);
		
		this.urlPageRank.put("http://A/", 10.0);
		this.urlPageRank.put("http://B/", 1.0);
		
		tt.createInvertedIndex();
		assert("{word1={http://B/=2.0, http://A/=10.0}, word2={http://B/=3.0, http://A/=20.0}}".hashCode() == this.invertedIndex.toString().hashCode());
		
	}
	
	
}
