package searchengine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface IndexerInterface {
	Map<String, Set<String>> linkIndex = Collections.synchronizedMap(new HashMap<String, Set<String>>());
	Map<String, Integer> urlIDs = Collections.synchronizedMap(new HashMap<String, Integer>());
	Map<String, Double> urlPageRank = Collections.synchronizedMap(new HashMap<String, Double>());
	Map<String, String> contentRepo = Collections.synchronizedMap(new HashMap<String, String>());
	Map<String, Map<String, Double>> fwdIndex = Collections.synchronizedMap(new HashMap<String, Map<String, Double>>());
	Map<String, Map<String, Double>> invertedIndex = Collections.synchronizedMap(new HashMap<String, Map<String, Double>>());
}
