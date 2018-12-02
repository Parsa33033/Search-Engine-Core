package searchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Parsa
 *
 */
public interface CrawlerInterface {
	List<String> urlsToCrawl = Collections.synchronizedList(new ArrayList<String>());
	Set<String> crawledUrls = Collections.synchronizedSet(new HashSet<String>());
	
}
