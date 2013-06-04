import java.util.Hashtable;


public class model {
	public Hashtable<String, Integer> unigram;
	public Hashtable<String, Integer> bigram;
	public Hashtable<String, Integer> unknown;
	public int count = 0;
	public int label;
	
	
	public model(int l){
		this.label = l;
		unigram = new Hashtable();
		this.bigram = new Hashtable();
		unknown = new Hashtable();
	}
	
	
	public void addUnigram(String s){
		String[] data = s.split(",");
		for(int i = 2;i<data.length;i++){
			String [] uni = data[i].split(" ");
			for(String w:uni){
				int v = 0;
				
				if(unigram.containsKey(w)){
					v = unigram.get(w);
				}
				v++;
				this.unigram.put(w, v);
			}
			count++;
		}
	}
	
	public void addBigram(String s){
		String[] data = s.split(",");
		for(int i = 2;i<data.length;i++){
			String [] bi = data[i].split(" ");
			for(String w:bi){
				int v = 0;
				if(this.bigram.containsKey(w)){
					v = this.bigram.get(w);
				}
				v++;
				this.bigram.put(w, v);
			}
		}
	}
	
	public double prob_un(String s){
		String [] unig = s.split(" ");
		double prob = 0.0;
		
		for(String t: unig){
			if(!this.unigram.containsKey(t)){
				prob = prob+ Math.log10(0.00001);
			}
			else{
				prob = prob+ Math.log10(1.0*this.unigram.get(t)/count);
//				System.out.println(1.0*this.unigram.get(t)/count);
			}
			
		}
		return prob;
	}
	
	public double prob_bi(String s){
		//the probability of getting s
		String []bigram = s.split(" ");
		double prob = 0.0;
		
		for(String t:bigram){
			if(!this.bigram.containsKey(t)){
				//add a negative number
				prob = prob+Math.log10(0.00001);
			}
			else{
				String [] word = t.split("-");
				prob = prob+Math.log10(getProb(word[0],word[1]));
			}
		}
		return prob;
	}
	
	public double getProb(String w1, String w2){
		double result =1.0 * this.bigram.get(w1+"-"+w2)/this.unigram.get(w1);
		return result;
	}
}
