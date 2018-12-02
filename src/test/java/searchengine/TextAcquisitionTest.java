package searchengine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class TextAcquisitionTest implements IndexerInterface{
	TextAcquisition ta = new TextAcquisition();
	@Test
	public void getDocumentTest() {
		String html = "<html><body>"
				+ "<h1>h1</h1><h6>h6</h6>"
				+ "<div>div</div><a>a</a>"
				+ "</body></html>";
		Document doc = Jsoup.parse(html);
		
				
		ta.getDocument(doc, "A");
		assert(this.contentRepo.get("A").trim().hashCode()=="h1  h6  div  a".hashCode());
	}
	
	@Test
	public void getTextTags() {
		String html = "<html><body>"
				+ "<h1>h1</h1><h6>h6</h6>"
				+ "<div>div</div><a>a</a>"
				+ "</body></html>";
		Document doc = Jsoup.parse(html);
		
				
		ta.getDocument(doc, "A");
		assert("h1  h6  div  a".hashCode() == ta.getTextTags().trim().hashCode());
	}
	
	
}
