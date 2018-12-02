package searchengine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import Jama.Matrix;



public class PageRankTest implements IndexerInterface{
	PageRank pr = new PageRank();
	@Test
	public void findPageRankTest() {
		Map<String, Set<String>> links = new HashMap<String, Set<String>>();
		Map<String, Integer> ids = new HashMap<String, Integer>();
		Set<String> s = new HashSet<String>();
		s.add("B");
		links.put("A", s);
		links.put("B", s);
		ids.put("A",0);
		ids.put("B",1);
		pr.findPageRank(links, ids);
		assert(String.format("%.1f",this.urlPageRank.get("B")).equals("0.9") );
		
	}
	
	
	@Test
	public void lessThanEpsilonTest() {
		double[][] m = new double[2][1];
		Matrix M = new Matrix(m);
		double[][] n = new double[2][1];
		Matrix N = new Matrix(m);
		assert(pr.lessThanEpsilon(M, N, Math.pow(Math.E, -10), 2) == true);
		assert(pr.lessThanEpsilon(M, N, -1, 2)==false);
	}
	
	@Test
	public void createVectorRTest() {
		Matrix r = pr.createVectorR(2, 0.1);
		
		double[][] n = new double[2][1];
		Matrix N = new Matrix(n);
		
		N.set(0, 0, 0.1);
		N.set(1, 0, 0.1);
		
		assert(r.get(0, 0)==N.get(0, 0) && r.get(1, 0)==N.get(1, 0));
	}
	
	@Test
	public void createMatrixMTest() {
		
		Map<String, Set<String>> links = new HashMap<String, Set<String>>();
		Map<String, Integer> ids = new HashMap<String, Integer>();
		Set<String> s = new HashSet<String>();
		s.add("B");
		links.put("A", s);
		links.put("B", s);
		ids.put("A",0);
		ids.put("B",1);
		Matrix M = pr.createMatrixM(links, ids);
		assert(M.get(0, 0)==0 && M.get(1, 0)==1 && M.get(0, 1)==0 && M.get(1, 1)==1 );
	}
	
	
	
}
