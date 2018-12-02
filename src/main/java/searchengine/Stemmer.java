package searchengine;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Parsa
 */
public abstract class Stemmer implements Serializable
{

	private static final long serialVersionUID = 1889842876393488149L;

	/**
     * Reduce the given input to its stem word
     * @param word the unstemmed input word
     * @return the stemmed version of the word
     */
    abstract public String stem(String word);
    
    /**
     * Replaces each value in the list with the stemmed version of the word
     * @param list the list to apply stemming to
     */
    public void applyTo(List<String> list)
    {
        for(int i = 0; i < list.size(); i++)
            list.set(i, stem(list.get(i)));
    }
    
    /**
     * Replaces each value in the array with the stemmed version of the word
     * @param arr the array to apply stemming to
     */
    public void applyTo(String[] arr)
    {
        for(int i = 0; i < arr.length; i++)
            arr[i] = stem(arr[i]);
    }
}
