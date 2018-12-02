package searchengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

public class IndexerAdapterTest implements IndexerInterface {
	IndexerAdapter ia = new IndexerAdapter();
	@Test
	public void testLinkIndex() {
		List<String> links = new ArrayList<String>();
		links.add("http://eng.ut.ac.ir/en");
		links.add("http://eng.ut.ac.ir/fa");
		String url = "http://ut.ac.ir/";
		for(int i = 0 ; i<10 ; i++) {
			ia.linkIndex(url, links);			
		}
		Set<String> set = new HashSet<String>();
		set.add("http://eng.ut.ac.ir/");
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		map.put(url, set);
		assert(map.hashCode() == this.linkIndex.hashCode());
	}

}
