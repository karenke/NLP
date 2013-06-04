/*
 *  Run this program using : "java -Xmx1024M Smoothing"
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

public class Smoothing {
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException{
	
		// Token generated for training set using NLTK	
		String[] inTokenTrain = new String[4];
		inTokenTrain[0] = "./data/WSJtokentrain";
		inTokenTrain[1] = "./data/Movietokentrain";
		inTokenTrain[2] = "./data/NFStokentrain";
		inTokenTrain[3] = "./data/RCVtokentrain";
		
		// Token generated for test set using NLTK	
		String[] inTokenTest = new String[4];
		inTokenTest[0] = "./data/WSJtokentest";
		inTokenTest[1] = "./data/Movietokentest";
		inTokenTest[2] = "./data/NFStokentest";
		inTokenTest[3] = "./data/RCVtokentest";

		// Unigram counts for the training set
		String[] outUniTrain = new String[4];
		outUniTrain[0] = "./data/WSJ-Smooth-Uni-train";
		outUniTrain[1] = "./data/Movie-Smooth-Uni-train";
		outUniTrain[2] = "./data/NFS-Smooth-Uni-train";
		outUniTrain[3] = "./data/RCV-Smooth-Uni-train";

		// Bigram counts for the training set
		String[] outBiTrain = new String[4];
		outBiTrain[0] = "./data/WSJ-Smooth-Bi-train";
		outBiTrain[1] = "./data/Movie-Smooth-Bi-train";
		outBiTrain[2] = "./data/NFS-Smooth-Bi-train";
		outBiTrain[3] = "./data/RCV-Smooth-Bi-train";
           
		// Unigram counts for the test set 
		String[] outUniTest = new String[4];
		outUniTest[0] = "./data/WSJ-Smooth-Uni-test";
		outUniTest[1] = "./data/Movie-Smooth-Uni-test";
		outUniTest[2] = "./data/NFS-Smooth-Uni-test";
		outUniTest[3] = "./data/RCV-Smooth-Uni-test";
		
		// Bigram counts for the test set 
		String[] outBiTest = new String[4];
		outBiTest[0] = "./data/WSJ-Smooth-Bi-test";
		outBiTest[1] = "./data/Movie-Smooth-Bi-test";
		outBiTest[2] = "./data/NFS-Smooth-Bi-test";
		outBiTest[3] = "./data/RCV-Smooth-Bi-test";

		// Unigram probabilities for the training set
                String[] outUniProb = new String[4];
		outUniProb[0] = "./data/WSJ-Smooth-Uni-prob";
		outUniProb[1] = "./data/Movie-Smooth-Uni-prob";
		outUniProb[2] = "./data/NFS-Smooth-Uni-prob";
		outUniProb[3] = "./data/RCV-Smooth-Uni-prob";

		// Bigram probabilities for the training set
                String[] outBiProb = new String[4];
		outBiProb[0] = "./data/WSJ-Smooth-Bi-prob";
		outBiProb[1] = "./data/Movie-Smooth-Bi-prob";
		outBiProb[2] = "./data/NFS-Smooth-Bi-prob";
		outBiProb[3] = "./data/RCV-Smooth-Bi-prob";

		// Unigram Unknown words for the test set
                String[] outUniUnknown = new String[4];
		outUniUnknown[0] = "./data/WSJ-Smooth-Uni-unknown";
		outUniUnknown[1] = "./data/Movie-Smooth-Uni-unknown";
		outUniUnknown[2] = "./data/NFS-Smooth-Uni-unknown";
		outUniUnknown[3] = "./data/RCV-Smooth-Uni-unknown";	
		
		// Bigram Unknown words for the test set
                String[] outBiUnknown = new String[4];
		outBiUnknown[0] = "./data/WSJ-Smooth-Bi-unknown";
		outBiUnknown[1] = "./data/Movie-Smooth-Bi-unknown";
		outBiUnknown[2] = "./data/NFS-Smooth-Bi-unknown";
		outBiUnknown[3] = "./data/RCV-Smooth-Bi-unknown";	

		// Unigram perplexity for the test set
                String[] outUniPerp = new String[4];
		outUniPerp[0] = "./data/WSJ-Smooth-Uni-perp";
		outUniPerp[1] = "./data/Movie-Smooth-Uni-perp";
		outUniPerp[2] = "./data/NFS-Smooth-Uni-perp";
		outUniPerp[3] = "./data/RCV-Smooth-Uni-perp";

		// Bigram perplexity for the test set
                String[] outBiPerp = new String[4];
		outBiPerp[0] = "./data/WSJ-Smooth-Bi-perp";
		outBiPerp[1] = "./data/Movie-Smooth-Bi-perp";
		outBiPerp[2] = "./data/NFS-Smooth-Bi-perp";
		outBiPerp[3] = "./data/RCV-Smooth-Bi-perp";
		
		// Unigram gtcount for the test set
                String[] outUniGt = new String[4];
		outUniGt[0] = "./data/WSJ-Smooth-Uni-gtcount";
		outUniGt[1] = "./data/Movie-Smooth-Uni-gtcount";
		outUniGt[2] = "./data/NFS-Smooth-Uni-gtcount";
		outUniGt[3] = "./data/RCV-Smooth-Uni-gtcount";

		// Bigram gtcount for the test set
                String[] outBiGt = new String[4];
		outBiGt[0] = "./data/WSJ-Smooth-Bi-gtcount";
		outBiGt[1] = "./data/Movie-Smooth-Bi-gtcount";
		outBiGt[2] = "./data/NFS-Smooth-Bi-gtcount";
		outBiGt[3] = "./data/RCV-Smooth-Bi-gtcount";

		// Get the Unigram and bigram counts, Good turing counts and probabilities
		// for the training set
		for (int i = 0; i < 4; i++) {
			 System.out.println("Data set " + (i+1) + "\n");
                         HashMap<String,Integer> setTrainUni = new HashMap<String,Integer>();
			 HashMap<String,Double> probSetTrainUni = new HashMap<String,Double>();
			 HashMap<Integer,Double> totgt_count = new HashMap<Integer,Double>();
			 getSmoothUnigrams(setTrainUni, totgt_count, probSetTrainUni, inTokenTrain[i]);
			 writeFileCount(setTrainUni, outUniTrain[i]);
			 writeFileGt(totgt_count, outUniGt[i]);
			 writeFileProb(probSetTrainUni, outUniProb[i]);

			 System.out.println("Finding the Unigram unknown words..");
			 findUnknownUniWords(setTrainUni, inTokenTest[i], outUniUnknown[i]);
			 HashMap<String,Double> perpSetTestUni = new HashMap<String,Double>(); 
		  	 System.out.println("Compute the Unigram perplexity..");
			 computePerplexityUni(probSetTrainUni, perpSetTestUni, inTokenTest[i]);
			 writeFileProb(perpSetTestUni, outUniPerp[i]);
			 probSetTrainUni = null;
			 perpSetTestUni = null;

			 System.out.println("\n");	
			 HashMap<String,Integer> setTrainBi = new HashMap<String,Integer>();
			 HashMap<String,Double> probSetTrainBi = new HashMap<String,Double>();
			 HashMap<Integer,Double> gt_count = new HashMap<Integer,Double>();
			 getSmoothBigrams(setTrainBi, gt_count, setTrainUni, totgt_count, probSetTrainBi, inTokenTrain[i]);
			 writeFileCount(setTrainBi, outBiTrain[i]);
			 writeFileGt(gt_count, outBiGt[i]);
			 writeFileProb(probSetTrainBi, outBiProb[i]);

			 System.out.println("Finding the Bigram Unknown words..");
			 findUnknownBiWords(setTrainBi, setTrainUni, inTokenTest[i], outBiUnknown[i]);
			 setTrainBi = null;

			 HashMap<String,Double> perpSetTestBi = new HashMap<String,Double>();
			 System.out.println("Compute the Bigram perplexity...");
                         computePerplexityBi(probSetTrainBi, perpSetTestBi, setTrainUni, gt_count, totgt_count, inTokenTest[i]);
			 writeFileProb(perpSetTestBi, outBiPerp[i]);

			 gt_count = null;
			 totgt_count = null;
			 setTrainUni = null;
			 probSetTrainUni = null;
			 perpSetTestBi = null;
			 System.out.println("\n\n");
                }
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
                 if (key <= 10) {
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

        public static void writeFileGt(HashMap<Integer,Double> mp, String output) throws IOException
        {
                FileWriter fstream = new FileWriter(output);
                BufferedWriter out = new BufferedWriter(fstream);
                Iterator itr = mp.keySet().iterator();
                while(itr.hasNext()){
                        Integer pair = (Integer) itr.next();
                        double value = mp.get(pair);
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
