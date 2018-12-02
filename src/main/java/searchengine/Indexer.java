package searchengine;

/**
 * Indexing the crawled pages by thread.
 * uses mode to choose pagerank or text transforming method
 * @author Parsa
 *
 */
public class Indexer implements Runnable{
	private IndexerAdapter indexerAdapter = new IndexerAdapter();
	private String mode;
	
	/**
	 * @param mode
	 */
	public Indexer(String mode) {
		this.mode = mode;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if(mode.equals("text transformer")) {
			this.startTextTransformer();
		}else if(mode.equals("page rank")) {
			this.startPageRank();
		}
	}
	
	/**
	 * starts Text Transformer method
	 */
	public void startTextTransformer() {
		indexerAdapter.runTextTransformer();
	}
	
	/**
	 * starts PageRank algorithm
	 */
	public void startPageRank() {
		indexerAdapter.runPageRank();
	}
}
