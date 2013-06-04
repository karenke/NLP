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

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class ReadFile {
	
	
	/**
	 * 1. read XML, extract TEXT
	 * 2. python NLTK tokenize, output: each line is a sentence
	 * 3. get bigrams and write into the file*/
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException{
		
		String[] in = new String[4];
		in[0] = "./data/wsj.train";
		in[1] = "./data/movie.train";
		in[2] = "./data/nfs.train";
		in[3] = "./data/rcv1.train";
		
		String[] out = new String[4];
		out[0] = "./data/newWSJ.train";
		out[1] = "./data/newMovie.train";
		out[2] = "./data/newNFS.train";
		out[3] = "./data/newRCV.train";
		
//		for(int i = 0; i < 2; i++){
//			parse(in[i],out[i]);
//		}
		
//		parse2(in[3],out[3]);
//		parse3(in[2],out[2]);
		
		String[] inToken = new String[4];
		inToken[0] = "./data/WSJtoken";
		inToken[1] = "./data/Movietoken";
		inToken[2] = "./data/NFStoken";
		inToken[3] = "./data/RCVtoken";
		
		String[] outBi = new String[4];
		outBi[0] = "./data/WSJ-Bi";
		outBi[1] = "./data/Movie-Bi";
		outBi[2] = "./data/NFS-Bi";
		outBi[3] = "./data/RCV-Bi";
		
		for(int i = 0; i < 4; i++){
			writeFile(getBigrams(inToken[i]),outBi[i]);
		}

		
		String[] outTri = new String[4];
		outTri[0] = "./data/WSJ-Tri1";
		outTri[1] = "./data/Movie-Tri1";
		outTri[2] = "./data/NFS-Tri1";
		outTri[3] = "./data/RCV-Tri1";

//		for(int i = 0; i < 4; i++){
//			getTrigrams(inToken[i],outTri[i]);
//		}
		
	}
	
	
	public static HashMap<String,Integer> getTrigrams(String inFile, String outFile) throws IOException{
		HashMap<String,Integer> set = new HashMap<String,Integer>();
		BufferedReader input = new BufferedReader(new FileReader(inFile));
		String line = null;
		String first = "";
		String sec = "";
		String third = "";
		StringTokenizer token;
		
		FileWriter fstream = new FileWriter(outFile);
		BufferedWriter out = new BufferedWriter(fstream);
	    while ((line = input.readLine()) != null) {	
	    	String testreg = "[^a-zA-Z0-9\\d+\\.\\d{2}?\\s]";
            Pattern matchsip = Pattern.compile(testreg);
            Matcher mp = matchsip.matcher(line);
            line = mp.replaceAll("");
	    	token = new StringTokenizer(line.toLowerCase()," ");
	    	int len = token.countTokens();
	    	if(len > 1){
	    		for(int i = 1; i < len; i++){
	    			if(i == 1){
	    				first = "START";
	    				sec = token.nextToken();
	    	    		third = token.nextToken();
	    			}
	    			else if(i == len-1){
	    				third = "END";
	    			}
	    			else{
	    				third = token.nextToken();
	    			}
	    			out.write(first + " " + sec + " " + third+"\n");
	    			first = sec;
		    		sec = third;
		    		
	    		}
	    		
	    	}
		}
	    input.close();
	    out.close();
	    return set;
	}
	
	// parse the original XML style input file
	public static void parse(String inFile, String outFile) throws ParserConfigurationException, SAXException, IOException{
		BufferedReader input = new BufferedReader(new FileReader(inFile));
		String line = null;
		int count = 0;
		StringBuffer buffer = new StringBuffer();
		Pattern p = Pattern.compile("<TEXT>(.+?)</TEXT>");//use regex to get contents in <TEXT></TEXT>
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
    //it is similar to parse, slightly different because the data file is a little different in format
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

	
	public static HashMap<String,Integer> getBigrams(String filename) throws IOException{
		HashMap<String,Integer> set = new HashMap<String,Integer>();// hash table is used to store the bigrams, and the value is occurrence
		BufferedReader input = new BufferedReader(new FileReader(filename));
		String line = null;
	    while ((line = input.readLine()) != null) {	
	    	//String testreg = "[,.?!;']";
	    	String testreg = "[^a-zA-Z0-9\\d+\\.\\d{2}?\\s]";//regex to remove some unrelated characters
            Pattern matchsip = Pattern.compile(testreg);
            Matcher mp = matchsip.matcher(line);
            line = mp.replaceAll("");
	    	StringTokenizer token = new StringTokenizer(line.toLowerCase()," "); 
	    	String[] readin = new String[token.countTokens()];
			for(int i = 0; i < readin.length; i++){
				readin[i] = token.nextToken();
			}
			if(readin.length > 0){
				for(int i = 0; i <= readin.length;i++){
					if(i != readin.length && (readin[i].equals(".")||readin[i].equals("?"))){
						if(i != 0){
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
					else if(i == 0){
						String b= "START " + readin[i];
						if(!set.containsKey(b)){
							set.put(b, 1);
						}
						else{
							int count = set.get(b);
							count++;
							set.put(b, count);
						}
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
					else{
						String b = readin[i-1] + " " + readin[i];
						if(!set.containsKey(b)){
							set.put(b, 1);
						}
						else{
							int count = set.get(b);
							count++;
							set.put(b, count);
						}
					}
					
				}
			}
		}
	    input.close();
	    return set;
	}
	

	
	public static void writeFile(HashMap<String,Integer> mp, String output) throws IOException{//write this bigrams/unigrams into files for later use
		FileWriter fstream = new FileWriter(output);
		BufferedWriter out = new BufferedWriter(fstream);
		Iterator itr = mp.keySet().iterator();
		while(itr.hasNext()){
			String pair = (String) itr.next();
			int count = mp.get(pair);
			out.write(pair+ " : " + count + "\n");
			
		}
		out.close();
	}
}
