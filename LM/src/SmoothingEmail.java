
/*
**  Run this program using : "java -Xmx1024M Smoothing"
 *  Otherwise this program will run out of heap space.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class SmoothingEmail {
	
	
	/**
	 * 1. read XML, extract TEXT
	 * 2. python NLTK tokenize, output: each line is a sentence
	 * 3. get bigrams and write into the file*/
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException{
	
		String[] emails = new String [3];
		emails[0] = "./data/email/newbeck-s.txt";
		emails[1] = "./data/email/newfarmer-d.txt";
		emails[2] = "./data/email/newkaminski-v.txt";
		
		String [] outEmail = new String[3];
		outEmail[0] = "./data/email/beck-sprob";
		outEmail[1] = "./data/email/farmer-dprob";
		outEmail[2] = "./data/email/kaminski-vprob";
		for(int i = 0;i<3;i++){
			 HashMap<String,Integer> setTrainUni = new HashMap<String,Integer>();
			 HashMap<String,Double> probSetTrainUni = new HashMap<String,Double>();
			 HashMap<Integer,Double> totgt_count = new HashMap<Integer,Double>();
			 getSmoothUnigrams(setTrainUni, totgt_count, probSetTrainUni, emails[i]);
			 HashMap<String,Integer> setTrainBi = new HashMap<String,Integer>();
			 HashMap<String,Double> probSetTrainBi = new HashMap<String,Double>();
			 HashMap<Integer,Double> gt_count = new HashMap<Integer,Double>();
			 getSmoothBigrams(setTrainBi, gt_count, setTrainUni, totgt_count, probSetTrainBi, emails[i]);
//			 writeFileCount(setTrainBi, outBiTrain[i]);
			 writeFileProb(probSetTrainBi, outEmail[i]);
		}
		
	}
	
	
	// parse the original XML style input file
	public static void parse(String inFile, String outFile) throws ParserConfigurationException, SAXException, IOException{
		BufferedReader input = new BufferedReader(new FileReader(inFile));
		String line = null;
		int count = 0;
		StringBuffer buffer = new StringBuffer();
		Pattern p = Pattern.compile("<TEXT>(.+?)</TEXT>");
		StringBuffer result = new StringBuffer();
		while ((line = input.readLine()) != null) {	
			Pattern p2 = Pattern.compile("[\"]");
			Matcher mp = p2.matcher(line);
                line = mp.replaceAll("");
	    	buffer.append(line);
	    	if (line.equals("</DOC>")) {
	    		String out = buffer.toString();
	    		Matcher m = p.matcher(out);
	    		while (m.find()) {
	    			result.append(m.group(1)+"\n");
	    	    }
	    		buffer.delete(0, buffer.length());
	    	}
	    	count++;
	        }
		System.out.println("finish reading, count=" + count);
		input.close();
	
		FileWriter fstream = new FileWriter(outFile);
		BufferedWriter output = new BufferedWriter(fstream);
	        output.write(result.toString());
		output.close();
	}
	
	//rcv
	public static void parse2(String inFile, String outFile) throws ParserConfigurationException, SAXException, IOException{
		BufferedReader input = new BufferedReader(new FileReader(inFile));
		String line = null;
		int count = 0;
		StringBuffer buffer = new StringBuffer();
		Pattern p = Pattern.compile("<DOC>(.+?)</DOC>");
		StringBuffer result = new StringBuffer();
		while ((line = input.readLine()) != null) {	
			Pattern p2 = Pattern.compile("[\"]");
			Matcher mp = p2.matcher(line);
                line = mp.replaceAll("");
	    	buffer.append(line);
	    	if (line.equals("</DOC>")) {
	    		String out = buffer.toString();
	    		Matcher m = p.matcher(out);
	    		while (m.find()) {
	    			result.append(m.group(1)+"\n");
	    	        }
	    		buffer.delete(0, buffer.length());
	    	}
	    	count++;
	        }
		System.out.println("finish reading, count=" + count);
		input.close();
	
		FileWriter fstream = new FileWriter(outFile);
		BufferedWriter output = new BufferedWriter(fstream);
	        output.write(result.toString());
		output.close();
		
	}
	
	public static void parse3(String inFile, String outFile) throws ParserConfigurationException, SAXException, IOException{
		BufferedReader input = new BufferedReader(new FileReader(inFile));
		String line = null;
		int count = 0;
		StringBuffer buffer = new StringBuffer();
		Pattern p = Pattern.compile("Abstract(.+?)</DOC>");
		StringBuffer result = new StringBuffer();
		while ((line = input.readLine()) != null) {	
			Pattern p2 = Pattern.compile("[\":]");
			Matcher mp = p2.matcher(line);
                line = mp.replaceAll("");
	    	buffer.append(line);
	    	if (line.equals("</DOC>")) {
	    		String out = buffer.toString();
	    		Matcher m = p.matcher(out);
	    		while (m.find()) {
	    			result.append(m.group(1)+"\n");
	    	    }
	    		buffer.delete(0, buffer.length());
	    	}
	    	count++;
	        }
		System.out.println("finish reading, count=" + count);
		input.close();
	
		FileWriter fstream = new FileWriter(outFile);
		BufferedWriter output = new BufferedWriter(fstream);
	        output.write(result.toString());
		output.close();
		
	}

        public static void findUnknownUniWords(HashMap<String,Integer> setTrainUni, String filename, String output) throws IOException
        {
		BufferedReader input = new BufferedReader(new FileReader(filename));
                FileWriter fstream = new FileWriter(output);
                BufferedWriter out = new BufferedWriter(fstream);
		String line = null;
                long numLine = 0;
	    while ((line = input.readLine()) != null) {
	    	//String testreg = "[,.?!;']";
	    	String testreg = "[^a-zA-Z0-9\\d+\\.\\d{2}?\\s]";
            Pattern matchsip = Pattern.compile(testreg);
            Matcher mp = matchsip.matcher(line);
            line = mp.replaceAll("");
	    	StringTokenizer token = new StringTokenizer(line.toLowerCase()," ");
	    	String[] readin = new String[token.countTokens()];
			for(int i = 0; i < readin.length; i++){
				readin[i] = token.nextToken();
			}
		
			// Unigram Unknown Words	
			for(int i = 0; i < readin.length; i++){	
		            String b = readin[i];
                            if(!setTrainUni.containsKey(b)){
				out.write(b+ " : " + "UNKNOWN" + "\n");
                            }
			}
	     }
	     out.close();
        }

	public static void findUnknownBiWords(HashMap<String,Integer> setTrainBi, HashMap<String,Integer> setTrainUni, String filename, String output) throws IOException
	{
                BufferedReader input = new BufferedReader(new FileReader(filename));
                FileWriter fstream = new FileWriter(output);
                BufferedWriter out = new BufferedWriter(fstream);
                String line = null;
                long numLine = 0;
            while ((line = input.readLine()) != null) {
                //String testreg = "[,.?!;']";
                String testreg = "[^a-zA-Z0-9\\d+\\.\\d{2}?\\s]";
            Pattern matchsip = Pattern.compile(testreg);
            Matcher mp = matchsip.matcher(line);
            line = mp.replaceAll("");
                StringTokenizer token = new StringTokenizer(line.toLowerCase()," ");
                String[] readin = new String[token.countTokens()];
                        for(int i = 0; i < readin.length; i++){
                                readin[i] = token.nextToken();
                        }

			// Bigram Unknown Words
			if(readin.length > 0){
				for(int i = 0; i <= readin.length;i++){
					if(i != readin.length && (readin[i].equals(".")||readin[i].equals("?"))){
						if(i != 0) {
							String b = readin[i-1] + " END";
							if(!setTrainUni.containsKey(b)){
								out.write(b+ " : " + "UNKNOWN" + "\n");	
							}
						}
						break;
					}
					else if(i == 0) {
						String b= "START " + readin[i];
						if(!setTrainUni.containsKey(b)){
							out.write(b+ " : " + "UNKNOWN" + "\n");
						}
					}
					else if(i == readin.length){
						String b = readin[i-1] + " END";
						if(!setTrainUni.containsKey(b)){
							out.write(b+ " : " + "UNKNOWN" + "\n");
						}
					}
					else {
						String b = readin[i-1] + " " + readin[i];
						if(!setTrainBi.containsKey(b)){
						    if (!setTrainUni.containsKey(readin[i-1]) && 
								!setTrainBi.containsKey(readin[i])) {
							out.write(b+ " : " + "UNKNOWN UNKNOWN" + "\n");
						    }
						    else if (setTrainUni.containsKey(readin[i-1])) {
							out.write(b+ " : " + "UNKNOWN " + readin[i] + "\n");
						    }
						    else {
							out.write(b+ " : " + readin[i-1] + " UNKNOWN" + "\n");
						    }
						}
					}
				}
			}
		}
		input.close();
		out.close();
	}

        public static void computePerplexityUni(HashMap<String,Double> probSetUni, HashMap<String,Double> perpSetUni, String filename) throws IOException
        {
		BufferedReader input = new BufferedReader(new FileReader(filename));
		String line = null;
		long numLine = 0;
		double Max = Double.MIN_VALUE;
		double Min = Double.MAX_VALUE;
		double Mean = 0.0;


	    	while ((line = input.readLine()) != null) {
                    numLine++;
            	}
            	input.close();
            	input = new BufferedReader(new FileReader(filename));
	    	while ((line = input.readLine()) != null) {
	    	    //String testreg = "[,.?!;']";
	    	    String testreg = "[^a-zA-Z0-9\\d+\\.\\d{2}?\\s]";
            	    Pattern matchsip = Pattern.compile(testreg);
            	    Matcher mp = matchsip.matcher(line);
            	    line = mp.replaceAll("");
	    	    StringTokenizer token = new StringTokenizer(line.toLowerCase()," ");
	    	    String[] readin = new String[token.countTokens()];
			for(int i = 0; i < readin.length; i++){
				readin[i] = token.nextToken();
			}
			double total = 0.0;
			double num = 0.0;
			double perp = 0.0;
			// Unigram Perplexity
			for(int i = 0; i < readin.length; i++) {
                                String b = readin[i];
                                if (b.equals(".") || b.equals("?")) {
                                    break;
                                }
				double value;
				if(!probSetUni.containsKey(b)){
				    value = 0.0;
                                }
				else {
				    value = probSetUni.get(b);
                                }
			
				if (value != 0.0) {	
				    // Mean perplexity
				    total = total + Math.log(value);
				}
				num++;
                        }
			double perplog = (((double)-1/num) * (total));
			perp = Math.exp(perplog);
	
			if (perp != perp) {
				perp = 0.0;
			}
			double Mean1 = (perp/(double)numLine);
			Mean = Mean + Mean1;
			if (perp > Max) {
				Max = perp;
			}
			if (perp < Min) {
				Min = perp;
			}
	    }
	    System.out.println("Mean :" + Mean);
	    perpSetUni.put("Mean Uni perp", Mean);
	    perpSetUni.put("Max Uni perp", Max);
	    perpSetUni.put("Min Uni perp", Min);

   	    input.close();
	}
	
	public static void computePerplexityBi(HashMap<String,Double> probSetBi, HashMap<String,Double> perpSetBi, HashMap<String,Integer> setTrainUni, HashMap<Integer,Double> gt_count, 
													HashMap<Integer,Double> totgt_count, String filename) throws IOException
        {
                BufferedReader input = new BufferedReader(new FileReader(filename));
                String line = null;
                long numLine = 0;
                long numWords = 0;
		double Max = Double.MIN_VALUE;
		double Min = Double.MAX_VALUE;
		double Mean = 0.0;

            while ((line = input.readLine()) != null) {
                numLine++;
            }
            input.close();
            input = new BufferedReader(new FileReader(filename));
            while ((line = input.readLine()) != null) {
                //String testreg = "[,.?!;']";
                String testreg = "[^a-zA-Z0-9\\d+\\.\\d{2}?\\s]";
            	Pattern matchsip = Pattern.compile(testreg);
            	Matcher mp = matchsip.matcher(line);
            	line = mp.replaceAll("");
                StringTokenizer token = new StringTokenizer(line.toLowerCase()," ");
                String[] readin = new String[token.countTokens()];
                        for(int i = 0; i < readin.length; i++){
                                readin[i] = token.nextToken();
                        }
			double total = 0.0;
			double perp = 0.0;
			Integer num = 0;

			if(readin.length > 0){
				for(int i = 0; i <= readin.length;i++){
					double value = 0.0;
					if(i != readin.length && (readin[i].equals(".")||readin[i].equals("?"))){
						if(i != 0) {
							String b = readin[i-1] + " END";
							value = checkProbBi(b, setTrainUni, gt_count, totgt_count, probSetBi);
						}
						break;
					}
					else if(i == 0) {
						String b= "START " + readin[i];
						value = checkProbBi(b, setTrainUni, gt_count, totgt_count, probSetBi);
						numWords++;
					}
					else if(i == readin.length){
						String b = readin[i-1] + " END";
						value = checkProbBi(b, setTrainUni, gt_count, totgt_count, probSetBi);
					}
					else {
						String b = readin[i-1] + " " + readin[i];
						value = checkProbBi(b, setTrainUni, gt_count, totgt_count, probSetBi);
						numWords++;
					}
				
	                                if (value != 0.0) {
                                       	    // Mean perplexity
                                           total = total + Math.log(value);
                                        }
                                	num++;
				}
                        	double perplog = (((double)-1/num) * (total));
                        	perp = Math.exp(perplog);

                        	if (perp != perp) {
                                    perp = 0.0;
                        	}
                        	double Mean1 = (perp/(double)numLine);
                        	Mean = Mean + Mean1;
                        	if (perp > Max) {
                                    Max = perp;
                        	}
                        	if (perp < Min) {
                                    Min = perp;
                        	}
			}
		}
            	System.out.println("Mean :" + Mean);
            	perpSetBi.put("Mean Uni perp", Mean);
            	perpSetBi.put("Max Uni perp", Max);
            	perpSetBi.put("Min Uni perp", Min);
		input.close();
	}

	public static double checkProbBi(String b, HashMap<String,Integer> setTrainUni, HashMap<Integer,Double> gt_count, 
									HashMap<Integer,Double> totgt_count, HashMap<String,Double> probSetBi) throws IOException
	{
		double value = 0.0;
		if (probSetBi.containsKey(b)) {
			value = probSetBi.get(b);
		 	return value;
		}
		String parts[] = b.split(" : ");
		if (setTrainUni.containsKey(parts[0]) && setTrainUni.containsKey(parts[1])) {
		       	double numer_value = gt_count.get(0);
			Integer denom_num = setTrainUni.get(parts[0]);
			double denom_value = totgt_count.get(denom_num);
			if (denom_value != 0) {
				value = (double)numer_value/denom_value;
			}
		}
		return value;
	}
	
	public static void getSmoothUnigrams(HashMap<String,Integer> totSet, HashMap<Integer,Double> totgt_count, HashMap<String,Double> probSetUni, String filename) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(filename));
		String line = null;
                long numLine = 0;
                long numWords = 0;
	    while ((line = input.readLine()) != null) {
	    	//String testreg = "[,.?!;']";
	    	String testreg = "[^a-zA-Z0-9\\d+\\.\\d{2}?\\s]";
            	Pattern matchsip = Pattern.compile(testreg);
            	Matcher mp = matchsip.matcher(line);
            	line = mp.replaceAll("");
	    	StringTokenizer token = new StringTokenizer(line.toLowerCase()," ");
	    	String[] readin = new String[token.countTokens()];
			for(int i = 0; i < readin.length; i++){
				readin[i] = token.nextToken();
			}

			// Unigram counts
			for(int i = 0; i < readin.length;i++){
				String b = readin[i];
				if (b.equals(".") || b.equals("?")) {
				    break;
				}
				if(!totSet.containsKey(b)){
				    totSet.put(b, 1);
			        }
				else{
				    int count = totSet.get(b);
				    count++;
				    totSet.put(b, count);
				}
				numWords++;
			}
	      }

	     System.out.println("numWords : " + numWords);
              // Compute counts Nc for Unigrams 
              HashMap<Integer,Long> totset_count;
              totset_count = computeCount(totSet, 0);

              // Compute Good Turing counts for Unigrams 
              computeGtcount(totgt_count, totset_count);

	      // Compute the probabilities for
	      // Unigrams and write it into a file
	      Iterator itr = totSet.keySet().iterator();
	      while (itr.hasNext()) {
		  String key = (String)itr.next();
		  
		  Integer numer_count = totSet.get(key);
		  double numer_value = totgt_count.get(numer_count);
		  double prob = numer_value/numWords;
		  probSetUni.put(key, prob);
	      }

	      input.close();
	}

	public static void getSmoothBigrams(HashMap<String,Integer> set, HashMap<Integer,Double> gt_count, HashMap<String, Integer> totSet, 
							HashMap<Integer,Double> totgt_count, HashMap<String,Double> probSet, String filename) throws IOException{

		BufferedReader input = new BufferedReader(new FileReader(filename));
                //FileWriter fstream = new FileWriter(output);
                //BufferedWriter out = new BufferedWriter(fstream);

		String line = null;
                long numLine = 0;
                long numWords = 0;
	    while ((line = input.readLine()) != null) {
	    	//String testreg = "[,.?!;']";
	    	String testreg = "[^a-zA-Z0-9\\d+\\.\\d{2}?\\s]";
            	Pattern matchsip = Pattern.compile(testreg);
            	Matcher mp = matchsip.matcher(line);
            	line = mp.replaceAll("");
	    	StringTokenizer token = new StringTokenizer(line.toLowerCase()," ");
	    	String[] readin = new String[token.countTokens()];
			for(int i = 0; i < readin.length; i++){
				readin[i] = token.nextToken();
			}

			// Bigram counts
			if(readin.length > 0){
				for(int i = 0; i <= readin.length;i++){
//					numWords++;
					if(i != readin.length && (readin[i].equals(".")||readin[i].equals("?"))){
						if(i != 0) {
							String b = readin[i-1] + " END";
							if(!set.containsKey(b)){
								set.put(b, 1);
							}
							else{
								int count = set.get(b);
								count++;
								set.put(b, count);
							}
						}
						numWords++;
						break;
					}
					else if(i == 0) {
						String b= "START " + readin[i];
						if(!set.containsKey(b)){
							set.put(b, 1);
						}
						else {
							int count = set.get(b);
							count++;
							set.put(b, count);
						}
						numWords++;
					}
					else if(i == readin.length){
						String b = readin[i-1] + " END";
						if(!set.containsKey(b)){
							set.put(b, 1);
						}
						else{
							int count = set.get(b);
							count++;
							set.put(b, count);
						}
						numWords++;
					}
					else {
						String b = readin[i-1] + " " + readin[i];
						if(!set.containsKey(b)){
							set.put(b, 1);
						}
						else{
							int count = set.get(b);
							count++;
							set.put(b, count);
						}
						numWords++;
					}
				}
			}
			numLine++;
	      }
	    
              long zeroCount = (numWords * numWords) - numWords;
	      System.out.println("Zero count = " + zeroCount);

              // Compute counts Nc for the bigrams
              HashMap<Integer,Long> set_count;
              set_count = computeCount(set, zeroCount);

              // Compute Good Turing counts for Bigrams
              // c* = ((c+1) * (Nc+1/ Nc))
              computeGtcount(gt_count, set_count);

              // Compute the probabilities for the
	      // Bigrams and write it into a file
              Iterator itr = set.keySet().iterator();
              while (itr.hasNext()) {
                  String key = (String)itr.next();
                  String []parts = key.split(" ");

		  // Prob for Bigrams
                  Integer numer_count = set.get(key);
                  double numer_value = gt_count.get(numer_count);

                  Integer denom_count;
		  double denom_value;
		  if (parts[0].equals("START")) {
     		      denom_value = numLine;
                  }
		  else {
		      denom_count = totSet.get(parts[0]);
                      denom_value = totgt_count.get(denom_count);
		  }
		  
                  double prob = numer_value/denom_value;
                  //out.write(key+ " : " + prob  + "\n");
		  probSet.put(key, prob);
              }

	      input.close();
	      //out.close();
	}

        // Compute number of bigrams, Nc of count c, where c= 0,1,2,3...
        public static HashMap<Integer,Long> computeCount(HashMap<String,Integer> set, long zeroCount) throws IOException 
        {
	      HashMap<Integer,Long> set_count = new HashMap<Integer,Long>();
	      Iterator itr = set.keySet().iterator();
	      while(itr.hasNext()) {
	          String pair = (String)itr.next();
		  int count = set.get(pair);
		  if(!set_count.containsKey(count)){
		      set_count.put(count, (long)1);
	          }
		  else {
		      long value = set_count.get(count);
		      value++;
		      set_count.put(count, (long)value);
	          }
	      }
              set_count.put(0, zeroCount);
              return set_count;
        }

        // Compute Good Turing Counts
        // c* = ((c+1) * (Nc+1/ Nc))
        public static void computeGtcount(HashMap<Integer,Double> gt_count, HashMap<Integer,Long> set_count) throws IOException
        {
	     Iterator itr = set_count.keySet().iterator();
	     while(itr.hasNext()) {
	         Integer key = (Integer)itr.next();
  		 double gtcount = key;
                 if (key <= 5) {
	             long ncount = set_count.get(key);
			if((ncount != 0) && set_count.containsKey((key+1))) {
                            long ncount1 = set_count.get((key+1));
                            gtcount = (key + 1) * ((double)ncount1 / ncount);
			}
                 }
                 gt_count.put(key, gtcount);
             }
        }

        public static void writeFileCount(HashMap<String,Integer> mp, String output) throws IOException
        {
                FileWriter fstream = new FileWriter(output);
                BufferedWriter out = new BufferedWriter(fstream);
                Iterator itr = mp.keySet().iterator();
                while(itr.hasNext()){
                        String pair = (String) itr.next();
                        Integer value = mp.get(pair);
                        out.write(pair+ " : " + value + "\n");

                }
                out.close();
        }

       public static void writeFileString(HashMap<String,String> mp, String output) throws IOException
        {
                FileWriter fstream = new FileWriter(output);
                BufferedWriter out = new BufferedWriter(fstream);
                Iterator itr = mp.keySet().iterator();
                while(itr.hasNext()){
                        String pair = (String) itr.next();
                        String value = mp.get(pair);
                        out.write(pair+ " : " + value + "\n");

                }
                out.close();
        }


	public static void writeFileProb(HashMap<String,Double> mp, String output) throws IOException
        {
		FileWriter fstream = new FileWriter(output);
		BufferedWriter out = new BufferedWriter(fstream);
		Iterator itr = mp.keySet().iterator();
		while(itr.hasNext()){
			String pair = (String) itr.next();
			double value = mp.get(pair);
			out.write(pair+ " : " + value + "\n");
			
		}
		out.close();
	}
}