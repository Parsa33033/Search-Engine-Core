package searchengine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The Class For Running Threads to Crawl the University of Teharn URLs
 * and Indexing them
 * @author Parsa
 * 
 */
public class EngineThreadFactory {
	private ExecutorService ex;
	private int numberOfThreads;
	private CrawlerResource crawlerResource = new CrawlerResource();
	private boolean allDone = true;
	private List<Future<?>> futures = new ArrayList<Future<?>>();
	
	/**
	 * @param numberOfThreads
	 */
	public EngineThreadFactory(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}
	
	/**
	 * running thread that runs the crawler and indexer and query
	 */
	public void run() {
		//start query in a thread
		Thread query = new Thread(new Query());
		query.setPriority(1);
		query.start();
		
		//start crawling and indexing in different Threads
		while(true) {
			ex = Executors.newCachedThreadPool();
			for(int i=0; i<this.numberOfThreads; i++) {
				try {
					Thread.sleep(100);
				}catch(Exception e) {}
				Future<?> f = ex.submit(CrawlerBuilder.buildCrawler(this.crawlerResource));
				futures.add(f);
			}
			threadsPriority();
			
			ex = Executors.newCachedThreadPool();
			Future<?> f = ex.submit(IndexerBuilder.buildIndexer("Text Transformer"));
			futures.add(f);
			f = ex.submit(IndexerBuilder.buildIndexer("Page Rank"));
			futures.add(f);
			
			threadsPriority();	
		}
	}
	
	/**
	 * makes sure that Threads of Previous step finished working
	 */
	public void threadsPriority() {
		while(true) {
			allDone = true;
			for(Future<?> future : futures){
			    allDone &= future.isDone(); // check if future is done
			}
			if(allDone) {
				ex.shutdownNow();
				break;
			}
		}
		futures = new ArrayList<Future<?>>();
	}
}
