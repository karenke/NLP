import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/*
 * Note: the table can also be used for trigrams and bigrams as well
 */
public class Table {
	public String name;

	public HashMap<String, Integer> unig;
	public HashMap<String, Integer> bigram;
	public HashMap<String, Integer> trigram;
	public HashMap<String, Double> biprob;
	
	/*
	 * Trigram example
	 */
	public Table(String n,HashMap<String, Integer> tri, HashMap<String,Integer> bi, HashMap<String, Integer> unigram){
		name = n;
		biprob = new HashMap();
		unig = new HashMap(unigram);
		bigram = new HashMap(bi);
		trigram = new HashMap(tri);
	}

	/*
	 * bigram example Without Smooth
	 */
	public Table(String n,HashMap<String,Integer> bi, HashMap<String, Integer> unigram){
		name = n;
		biprob = new HashMap();
		unig = new HashMap(unigram);
		bigram = new HashMap(bi);
		trigram = new HashMap();
	}
	
	/*
	 * bigram example with smoothing
	 */
	public Table(HashMap<String, Double> bipro,String n){
		name = n;
		biprob = new HashMap(bipro);
		unig = new HashMap();
		bigram = new HashMap();
		trigram = new HashMap();
	}
	
	/*
	 * Return the probablity of w2 goes after w1;
	 */
	public double getProbablity(String w1, String w2){
		if(!unig.containsKey(w1)||!unig.containsKey(w2)){
			return 0.0;
		}
		if(!bigram.containsKey(w1+" "+w2)){
			return 0;
		}
		return (double)1.0*(bigram.get(w1+" "+w2))/unig.get(w1);
	}

	/*
	 * Calculate the smoothed probablity of w2 goes after w1
	 */
	public double getProbablitySmooth(String w1, String w2){
		if(biprob.containsKey(w1+" "+w2)){
			return biprob.get(w1+" "+w2);
		}
		else 
			return 0.0;
	}
	/*
	 * return the probablity of w2 goes after w1(tri)
	 */
	public double getProbablityTri(String w1, String w2){
		if(!bigram.containsKey(w1)){
			return 0.0;
		}
		if(!trigram.containsKey(w1+" "+w2)){
			return 0;
		}
		return (double)1.0*(trigram.get(w1+" "+w2))/bigram.get(w1);
	}	
}
