package searchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Parsa
 *
 */
public class CrawlerResource implements CrawlerInterface{
	
	/**
	 * 
	 */
	public CrawlerResource() {
		this.urlsToCrawl.add("http://ut.ac.ir/en");
		this.urlsToCrawl.add("http://ut.ac.ir/fa");
		this.urlsToCrawl.add("https://rtis2.ut.ac.ir/cv/moeini");
		this.urlsToCrawl.add("https://jac.ut.ac.ir/");
		this.urlsToCrawl.add("https://jac.ut.ac.ir/journal/editorial.board");
	}
	
	/**
	 * @param url
	 */
	public void addUrlsToCrawl(String url) {
		this.urlsToCrawl.add(url);
	}
	
	/**
	 * @param url
	 */
	public void addCrawledUrls(String url) {
		this.crawledUrls.add(url);
	}
	
	/**
	 * @return
	 */
	public String getUrlsToCrawlHead() {
		if(!this.urlsToCrawl.isEmpty()) {
			return this.urlsToCrawl.remove(0);			
		}
		return null;
	}
	
}
