package searchengine;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import Jama.Matrix;

/**
 * evaluates pagerank for each pure url from linkIndex of IndexInterface
 * @author Parsa
 *
 */
public class PageRank implements IndexerInterface{
	
	/**
	 * runs findPageRank()
	 */
	public void run() {
		findPageRank(this.linkIndex, this.urlIDs);
	}
	
	/** 
	 * PageRank algorithm.
	 * uses links and urlIDs to get each urls links and ids 
	 * to compute page rank
	 * at the end uses savePageRank() to store the ranks of each url
	 * @param links
	 * @param urlIDs
	 */
	public void findPageRank(Map<String, Set<String>> links, Map<String, Integer> urlIDs) {
		
		
	}
	
	/**
	 * saves pagerank of each pure url(protocol://hostname/)
	 * in urlPageRank of IndexerInterface
	 * @param r
	 * @param urlIDs
	 */
	public void savePageRanks(Matrix r, Map<String, Integer> urlIDs) {
		for(Map.Entry<String, Integer> i : urlIDs.entrySet()) {
			this.urlPageRank.put(i.getKey(), r.get(i.getValue(), 0));
		}
	}
	
	/**
	 * creates matrix M from urls and their links (Mr = r)
	 * @param links: a map of pure urls and links in them
	 * @param urlIDs a map of pure urls and their id
	 * @return
	 */
	public Matrix createMatrixM(Map<String, Set<String>> links, Map<String, Integer> urlIDs) {
		
	}
	
	/**
	 * creates initialized vector r (Mr = r)
	 * @param n: number of elements in the vector
	 * @param coef: the coefficient by which we need to set the vectors
	 * @return
	 */
	public Matrix createVectorR(int n, double coef) {
		
	}
	
	/**
	 * checks if subtraction of vectors a-b element wise
	 * is less than eps
	 * @param a
	 * @param b
	 * @param eps
	 * @param n
	 * @return
	 */
	public boolean lessThanEpsilon(Matrix a, Matrix b, double eps, int n) {
		
	}

}
