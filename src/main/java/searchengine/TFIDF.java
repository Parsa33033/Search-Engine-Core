package searchengine;

import java.util.Map;

public class TFIDF {
	
	/**
	 * find the tf by document of the current url and the term given
	 * @param doc
	 * @param term
	 * @return
	 */
	public double tf(Map<String, Integer> doc, String term) {
		if(doc.containsKey(term)) {
			return (double)((double)doc.get(term)/doc.size());
		}
		return 0;
	}
	
	/**
	 * find idf by documents and term given
	 * @param docs
	 * @param term
	 * @return
	 */
	public double idf(Map<String, Map<String, Integer>> docs, String term) {
		int n = 0;
		for(Map.Entry<String, Map<String, Integer>> m : docs.entrySet()) {
			if(m.getValue().containsKey(term.toLowerCase())) {
				n++;
			}
		}
		return Math.log((double)docs.size()/n);
	}
	
	/**
	 * return tf.idf
	 * docs ==> <url , <term, wordcount>>
	 * @param docs
	 * @param term
	 * @param url
	 * @return
	 */
	public Double tfidf(Map<String, Map<String, Integer>> docs, String term, String url) {
		return tf(docs.get(url), term) * idf(docs, term);
	}

}
