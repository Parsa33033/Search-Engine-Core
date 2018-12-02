package searchengine;

import java.util.List;

/**
 * crawls the web as a thread
 * @author Parsa
 *
 */
public class Crawler implements CrawlerInterface , Runnable{
	
	private static CrawlerResource crawlerResource;
	private IndexerAdapter indexerAdapter = new IndexerAdapter();
	
	/**
	 * @param crawlerResource
	 */
	public Crawler(CrawlerResource crawlerResource) {
		super();
		this.crawlerResource = crawlerResource;
	}
	
	/* 
	 * runs the thread for crawler
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		int i = 0;
		
		while(!this.urlsToCrawl.isEmpty() && i<10) {
			String nextUrl = this.crawlerResource.getUrlsToCrawlHead();
			if(nextUrl != null && !this.crawledUrls.contains(nextUrl)) {
				
				indexerAdapter.connectToUrl(nextUrl);
				if(indexerAdapter.documentFound()) {
					List<String> links = indexerAdapter.getLinks();
					this.crawledUrls.add(nextUrl);
					this.urlsToCrawl.addAll(links);
					this.urlsToCrawl.removeAll(this.crawledUrls);
					indexerAdapter.linkIndex(nextUrl, links);
					indexerAdapter.TextAcquisition(nextUrl);
				}
				indexerAdapter.disconnectFromUrl();
			}
			
			
			i++;
			System.gc();
		}
	}
	
	
}
