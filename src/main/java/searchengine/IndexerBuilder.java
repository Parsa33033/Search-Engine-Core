package searchengine;

/**
 * builds Indexer instance
 * @author Parsa
 *
 */
public class IndexerBuilder {
	/**
	 * @param mode
	 * @return
	 */
	public static Indexer buildIndexer(String mode) {
		return new Indexer(mode.toLowerCase());
	}
	
}
