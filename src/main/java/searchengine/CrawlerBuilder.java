package searchengine;

/**
 * builds Crawler instance
 * @author Parsa
 *
 */
public class CrawlerBuilder {
	/**
	 * @param crawlerResource
	 * @return
	 */
	public static Crawler buildCrawler(CrawlerResource crawlerResource) {
		return new Crawler(crawlerResource);
	}
	
}
