import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import snowball.SnowballStemmer;

public class sentence {
	public String word;//the word of the sentence
	
	public ArrayList<String> senten;
	
	public ArrayList<Integer> index;//the index of word in sent
	
	public ArrayList<Integer> value;
	
	public int count; //# of meanings
	
	public int numOfWords;
	
	public sentence(String line) throws Throwable{
		numOfWords = 0;
		String[] segem = line.split("@");//split by @
		String[] segemlabel = segem[0].split(" ");
		count = segemlabel.length-2;
		word = segemlabel[0];
		value = new ArrayList();index = new ArrayList();senten = new ArrayList();
		for(int i = 2;i<segemlabel.length;i++){
			value.add(Integer.parseInt(segemlabel[i])); 
		}
		Class stemClass = Class.forName("snowball.englishStemmer");
		SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
		for(int i = 1;i<segem.length;i=i+4){
			//i
			String testreg = "[^a-zA-Z0-9\\d{2}?\\s]";
            Pattern matchsip = Pattern.compile(testreg);
            Matcher mp = matchsip.matcher(segem[i]);
            segem[i] = mp.replaceAll("");
	    	StringTokenizer token = new StringTokenizer(segem[i].toLowerCase()," ");//ignore the case here 
	    	String[] readin1 = new String[token.countTokens()];
	    	for(int ii = 0;ii<readin1.length;ii++){
	    		readin1[ii] = token.nextToken();
	    	}
	    	for(String temp:readin1){
	    		stemmer.setCurrent(temp);
	    		stemmer.stem();
	    		String tempword = stemmer.getCurrent();
	    		senten.add(tempword);//add to senten
	    	}
	    	senten.add(word);
	    	index.add(senten.size()-1);
	    	numOfWords++;
	    	if(i+2>=segem.length){
	    		break;
	    	}
	    	matchsip = Pattern.compile(testreg);
	    	mp = matchsip.matcher(segem[i+2]);
	    	segem[i+2] = mp.replaceAll("");
	    	token = new StringTokenizer(segem[i+2].toLowerCase()," ");//ignore the case here 
	    	String[] readin2 = new String[token.countTokens()];
	    	for(int ii = 0;ii<readin2.length;ii++){
	    		readin2[ii] = token.nextToken();
	    	}
	    	for(String temp:readin2){
	    		stemmer.setCurrent(temp);
	    		stemmer.stem();
	    		String tempword = stemmer.getCurrent();
	    		senten.add(tempword);//add to senten
	    	}
	    	numOfWords++;
	    	if(i+2==segem.length-1){
	    		//last index
	    		break;
	    	}
	    	senten.add(word);
	    	index.add(senten.size()-1);
		}
		value = this.getValue();
	}
	
	/*
	 * return all the word k length away from index
	 */
	public ArrayList<String> wordAroundIndex(int k){
		ArrayList<String> my = new ArrayList<String>();
		for(int i:index){
			for(int t = i-k;t<=i+k;t++){
				if(t<0||t>=senten.size()||t==i){
					continue;//can't get in this case
				}
				my.add(senten.get(t));
			}
		}
		return my;
	}
	
	public ArrayList<Integer> getValue(){
		ArrayList<Integer> v = new ArrayList<Integer>();
		for(int i = 0;i<value.size();i++){
			if(value.get(i) == 1){
				v.add(i);
			}
		}
		return v;
	}
}
