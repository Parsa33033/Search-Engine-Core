package searchengine;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Parsa
 * gets Text from a given url
 */
public class TextAcquisition implements IndexerInterface{
	
	private Document doc;
	
	/**
	 * instantiate doc and set IndexerInterface contentRepo <url, String> in
	 * which string is the tags parsed from html (h1-h6,p,a,div)
	 * @param doc: Document element got by jsoup
	 * @param url: url of the document
	 */
	public void getDocument(Document doc, String url) {
		this.doc = doc;
		this.contentRepo.put(url, getTextTags());
	}
	
	/**
	 * gets text from (h1 h2 h3 h4 h5 h6 p a div) tags
	 * uses Jsoup api to Extract tag texts
	 * @return
	 */
	public String getTextTags() {
		StringBuilder str = new StringBuilder();
		Elements elements = doc.select("h1,h2,h3,h4,h5,h6,p,a,h,div");
		for(Element e : elements) { 
			str.append(" "+e.text()+" ");
		}
		String documentText = str.toString();
		return documentText;
	}
}
