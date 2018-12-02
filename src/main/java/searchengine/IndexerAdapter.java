package searchengine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * works as a connector between Crawler and indexer
 * @author Parsa
 *
 */
public class IndexerAdapter implements IndexerInterface{
	private HttpURLConnection con;
	private Document doc;
	private boolean documentFound = true;
	private TextAcquisition ta;
	
	/**
	 * 
	 */
	public IndexerAdapter() {
		this.ta = new TextAcquisition();
	}
	
	/**
	 * connects to the Url crawled by the crawler
	 * @param urlString
	 */
	public void connectToUrl(String urlString) {
		try {
			URL url = new URL(urlString);
			con = (HttpURLConnection) url.openConnection();
			getDocument();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * gets the html document from the connected url
	 */
	public void getDocument() {
		try {
			documentFound = true;
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			StringBuilder html = new StringBuilder();
			while(reader.ready()) {
				html.append(reader.readLine());
			}	
			doc = Jsoup.parse(html.toString());
		}catch(Exception e) {
			documentFound = false;
		}
	}
	
	/**
	 * gets the links from the connected url,
	 * from href attribute of <a> tags in the html documents
	 * derived from getDocument()
	 * @return
	 */
	public List<String> getLinks(){
		if(this.documentFound) {
			List<String> tempLinks = new ArrayList<String>();
			Elements elements = doc.select("a");
			if(elements!= null)
			for(Element e: elements) {
				if(e.attr("href") != null) {
					String link = e.attr("href");
					if(link.contains("ut.ac.ir") && link.contains("http")
							&& !link.contains("=http")) {
						tempLinks.add(link.trim());
					}	
				}
			}
			return tempLinks;
		}
		return null;
	}
	
	/**
	 * Attaches links of a pure url(protocol://hostname/) to it
	 * url ---> its links (int the document)
	 * uses IndexerInterface linkIndex to do this task
	 * @param url
	 * @param links
	 */
	public void linkIndex(String url, List<String> links) {
		String urlHost = this.getUrlHost(url);
		Set<String> linksTemp = new HashSet<String>();
		for(String u : links) {
			linksTemp.add(this.getUrlHost(u));
		}
		if(this.linkIndex.containsKey(urlHost)) {
			linksTemp.addAll(this.linkIndex.get(this.getUrlHost(url)));
			this.linkIndex.put(urlHost, linksTemp);
		}else {
			this.linkIndex.put(urlHost, linksTemp);			
		}
		urlIDs(urlHost);
	}
	
	/**
	 * gives id to pure urls(protocol://hostname/) which have been crawled
	 * starts from zero
	 * url ---> id
	 * uses IndexerInterface urlIDs 
	 * @param url
	 */
	public void urlIDs(String url) {
		if(this.urlIDs.isEmpty()) {
			this.urlIDs.put(url, 0);
		}
		if(!this.urlIDs.containsKey(url)) {
			this.urlIDs.put(url, Collections.max(this.urlIDs.values())+1);			
		}
	}
	
	/**
	 * gets pure url (protocol://hostname/)
	 * @param url
	 * @return
	 */
	public String getUrlHost(String url) {
		try {
			URL myUrl = new URL(url);
			return myUrl.getProtocol()+"://"+myUrl.getHost()+"/";
		}catch(Exception e) {
//			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * runs Text Transformer
	 */
	public void runTextTransformer() {
		TextTransformer tt = new TextTransformer();
		tt.run();
	}
	
	/**
	 * runs PageRank algorithm
	 */
	public void runPageRank() {
		PageRank pr = new PageRank();
		pr.run();
	}
	
	/**
	 * starts text aquisition
	 * @param url
	 */
	public void TextAcquisition(String url) {
		ta.getDocument(doc,url);
	}
	
	/**
	 * disconnects from the url connected by crawler
	 */
	public void disconnectFromUrl() {
		con.disconnect();
	}
	
	/**
	 * returns Exception occured in getDocument() as Boolean
	 * @return
	 */
	public boolean documentFound() {
		return this.documentFound;
	}
}
