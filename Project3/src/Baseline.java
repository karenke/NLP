import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Baseline {

	ArrayList<String> stoplist;
	ArrayList<String> neg1words;
	ArrayList<String> neg2words;
	ArrayList<String> zerowords;
	ArrayList<String> pos1words;
	ArrayList<String> pos2words;
	int k;
	public Baseline(){
		k = 10;
		neg1words = new ArrayList<String>();
		neg2words = new ArrayList<String>();
		zerowords = new ArrayList<String>();
		pos1words = new ArrayList<String>();
		pos2words = new ArrayList<String>();
		
	}
	public static void main(String[] args) throws IOException{
		Baseline b = new Baseline();
		String file = "./data/DennisSchwartz_train.txt";
		String stop = "./data/stopwords.txt";
		String testfile = "./data/DennisSchwartz_test.txt";
		String outfile = "./data/DennisSchwartz_predict.csv";
		b.stoplist = b.readStopList(stop);
		b.readFile(file);
		b.test(testfile,outfile);
	}
	
	public ArrayList<String> readStopList(String filename) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(filename));
		String line = null;
        ArrayList<String> list = new ArrayList<String>();
        while ((line = input.readLine()) != null) {
        	list.add(line);
        	//System.out.println(line);
        }
        return list;
	}
	public void readFile(String filename) throws IOException{
		HashMap<String, Integer> neg1 = new HashMap<String, Integer>();
		HashMap<String, Integer> neg2 = new HashMap<String, Integer>();
		HashMap<String, Integer> zero = new HashMap<String, Integer>();
		HashMap<String, Integer> pos1 = new HashMap<String, Integer>();
		HashMap<String, Integer> pos2 = new HashMap<String, Integer>();
		
		PriorityQueue<Map.Entry<String, Integer>> neg2q = new PriorityQueue<Map.Entry<String, Integer>>(k, new Comparator<Map.Entry<String, Integer>>(){
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return o1.getValue() < o2.getValue() ? 1 : -1;
			}
		});
		PriorityQueue<Map.Entry<String, Integer>> neg1q = new PriorityQueue<Map.Entry<String, Integer>>(k, new Comparator<Map.Entry<String, Integer>>(){
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return o1.getValue() < o2.getValue() ? 1 : -1;
			}
		});
		PriorityQueue<Map.Entry<String, Integer>> zeroq = new PriorityQueue<Map.Entry<String, Integer>>(k, new Comparator<Map.Entry<String, Integer>>(){
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return o1.getValue() < o2.getValue() ? 1 : -1;
			}
		});
		PriorityQueue<Map.Entry<String, Integer>> pos1q = new PriorityQueue<Map.Entry<String, Integer>>(k, new Comparator<Map.Entry<String, Integer>>(){
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return o1.getValue() < o2.getValue() ? 1 : -1;
			}
		});
		PriorityQueue<Map.Entry<String, Integer>> pos2q = new PriorityQueue<Map.Entry<String, Integer>>(k, new Comparator<Map.Entry<String, Integer>>(){
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return o1.getValue() < o2.getValue() ? 1 : -1;
			}
		});
		
		
		BufferedReader input = new BufferedReader(new FileReader(filename));
		String line = null;
		StringTokenizer token;
		int group = -3;
		while ((line = input.readLine()) != null) {	
		//	System.out.println(line);
	    	String testreg = "<(.+?)>";
            Pattern matchsip = Pattern.compile(testreg);
            Matcher mp = matchsip.matcher(line);
            if(!mp.find()){
            	continue;
            }
            group = Integer.parseInt(mp.group(0).replaceAll("<", "").replaceAll(">",""));
       //   System.out.println(group);
            token = new StringTokenizer(line," ");
        	int len = token.countTokens();
        	String str = "";
            switch(group){
            	case -2:	
        	    	for(int i = 0; i < len-1; i++){
        	    		str = token.nextToken();
        	    		if(!str.equals("{}") && !stoplist.contains(str)){
        	    			if(!neg2.containsKey(str)){
        	    				neg2.put(str, 1);
        	    			}
        	    			else{
        	    				int count = neg2.get(str)+1;
        	    				neg2.put(str, count);
        	    			}
        	    		}
        	    	}	
        	    	break;
            		
            	case -1:
            		for(int i = 0; i < len-1; i++){
        	    		str = token.nextToken();
        	    		if(!str.equals("{}") && !stoplist.contains(str)){
        	    			if(!neg1.containsKey(str)){
        	    				neg1.put(str, 1);
        	    			}
        	    			else{
        	    				int count = neg1.get(str)+1;
        	    				neg1.put(str, count);
        	    			}
        	    		}
        	    	}	
        	    	break;
            	case 0:
            		for(int i = 0; i < len-1; i++){
        	    		str = token.nextToken();
        	    		if(!str.equals("{}") && !stoplist.contains(str)){
        	    			if(!zero.containsKey(str)){
        	    				zero.put(str, 1);
        	    			}
        	    			else{
        	    				int count = zero.get(str)+1;
        	    				zero.put(str, count);
        	    			}
        	    		}
        	    	}	
        	    	break;
            	case 1:
            		for(int i = 0; i < len-1; i++){
        	    		str = token.nextToken();
        	    		if(!str.equals("{}") && !stoplist.contains(str)){
        	    			if(!pos1.containsKey(str)){
        	    				pos1.put(str, 1);
        	    			}
        	    			else{
        	    				int count = pos1.get(str)+1;
        	    				pos1.put(str, count);
        	    			}
        	    		}
        	    	}	
        	    	break;
            	case 2:
            		for(int i = 0; i < len-1; i++){
        	    		str = token.nextToken();
        	    		if(!str.equals("{}") && !stoplist.contains(str)){
        	    			if(!pos2.containsKey(str)){
        	    				pos2.put(str, 1);
        	    			}
        	    			else{
        	    				int count = pos2.get(str)+1;
        	    				pos2.put(str, count);
        	    			}
        	    		}
        	    	}	
        	    	break;
            	default: break;
            }
		}
		
		for (Entry<String, Integer> ent: neg2.entrySet()){
			neg2q.add(ent);
		}
		
		for (Entry<String, Integer> ent: neg1.entrySet()){
			neg1q.add(ent);
		}
		
		for (Entry<String, Integer> ent: zero.entrySet()){
			zeroq.add(ent);
		}
		
		for (Entry<String, Integer> ent: pos1.entrySet()){
			pos1q.add(ent);
		}
		
		for (Entry<String, Integer> ent: pos2.entrySet()){
			pos2q.add(ent);
		}
		for (int i = 0; i < k; i++) {
			Entry<String, Integer> ent = neg2q.poll();
			neg2words.add(ent.getKey());
			ent = neg1q.poll();
			neg1words.add(ent.getKey());
			ent = zeroq.poll();
			zerowords.add(ent.getKey());
			ent = pos1q.poll();
			pos1words.add(ent.getKey());
			ent = pos2q.poll();
			pos2words.add(ent.getKey());	
		}
	}

	public void test(String filename,String outFile) throws IOException{
		FileWriter fstream = new FileWriter(outFile);
		BufferedWriter out = new BufferedWriter(fstream);
		BufferedReader input = new BufferedReader(new FileReader(filename));
		String line = null;
		StringTokenizer token;
		String group = null;
		while ((line = input.readLine()) != null) {	
		//	System.out.println(line);
	    	String testreg = "<>";
            Pattern matchsip = Pattern.compile(testreg);
            Matcher mp = matchsip.matcher(line);
            if(!mp.find()){
            	continue;
            }
            group = mp.group(0);
//          System.out.println(group);
            token = new StringTokenizer(line," ");
        	int len = token.countTokens();
   	
        	String str = "";
        	ArrayList<String> words = new ArrayList<String>();
        	if(group != null){
        		for(int i = 0; i < len-1; i++){
    	    		str = token.nextToken();
    	    		if(!str.equals("{}") && !stoplist.contains(str)){
    	    			words.add(str);
    	    		}
    	    	}	
        		double p_neg2 = calSim(words,neg2words);
        		double p_neg1 = calSim(words,neg1words);
        		double p_zero = calSim(words,zerowords);
        		double p_pos1 = calSim(words,pos1words);
        		double p_pos2 = calSim(words,pos2words);
        		
        		double max = 0;
        		int res = 0;
        		if(p_neg2 > max){
        			max = p_neg2;
        			res = -2;
        		}
        		if(p_neg1 > max){
        			max = p_neg1;
        			res = -1;
        		}
        		if(p_zero > max){
        			max = p_zero;
        			res = 0;
        		}
        		if(p_pos1 > max){
        			max = p_pos1;
        			res = 1;
        		}
        		if(p_pos2 > max){
        			max = p_pos2;
        			res = 2;
        		}
        		System.out.println(res);
//        		out.write("<"+res+">\n");
        		out.write(res+"\n");
//        		double d = Math.floor(p_neg2*(-2)+p_neg1*(-1)+p_zero*0+p_pos1*1+p_pos2*2);
//        		int result = (int)d;
        //		if(result != 0)
        //		System.out.println("prob: "+d);
        	}
		}
		out.close();
	}
	
	public double calSim(ArrayList<String> l1, ArrayList<String> l2){
		double prob = 0.0;
		int countAnd = 0;
		int countOr = 0;
		for(int i = 0; i < l1.size(); i++){
			for(int j = 0; j < l2.size(); j++){
				if(l1.get(i).equals(l2.get(j))){
					countAnd++;
				}
			}
		}
	
		countOr = l1.size()+l2.size()-countAnd;
	//	System.out.println("and: "+countAnd+", or: "+countOr);
		prob = (double)countAnd/(double)countOr;
	//	System.out.println(prob);
		return prob;
	}
}
