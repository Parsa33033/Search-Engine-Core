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
		int n = links.size();
		double epsilon = Math.pow(Math.E, -10);
		double beta = 0.8;

		//create matrix M
		double[][] m = new double[n][n];
		Matrix M = createMatrixM(links, urlIDs);
		for(int i = 0 ; i < n ; i++) {
			for(int j = 0 ; j<n ; j++) {
				// A = beta * M + (1-beta)/N
				M.set(i, j, M.get(i, j)*beta + (1-beta)/n);
			}
		}

		//create vector r
		double[][] v = new double[1][n];
		Matrix r = createVectorR(n, (double)1/n);
		Matrix r_prev = null;
		do {
			r_prev = r;
			r = M.times(r);
		}while(!this.lessThanEpsilon(r, r_prev, epsilon, n));

		savePageRanks(r, urlIDs);
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
		int n = urlIDs.size();
		int k = 0;
		Matrix M = new Matrix(new double[n][n]);
		for(Map.Entry<String, Integer> j : urlIDs.entrySet()) {
			Set<String> s = links.get(j.getKey());
			s.retainAll(urlIDs.keySet());
			k = s.size();
			for(String i : s) {
				if(urlIDs.containsKey(i)) {
					M.set(urlIDs.get(i), j.getValue(), (double)1/k);					
				}
			}
		}
		return M;
	}
	
	/**
	 * creates initialized vector r (Mr = r)
	 * @param n: number of elements in the vector
	 * @param coef: the coefficient by which we need to set the vectors
	 * @return
	 */
	public Matrix createVectorR(int n, double coef) {
		double[][] vec = new double[n][1];
		Matrix r = new Matrix(vec);
		for(int i = 0 ; i<n ; i++) {
			r.set(i, 0, 1);
		}
		r = r.times((double)coef);
		return r;
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
		boolean less = true;
		for(int i = 0 ; i<n ; i++) {
			if(Math.abs(a.get(i, 0) - b.get(i, 0)) > eps) {
				less = false;
			}
		}
		return less;
	}

}
