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


public class Unigram {
	
	
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
		
		for(int i = 0; i < 2; i++){
			parse(in[i],out[i]);
		}
		
		parse2(in[3],out[3]);
		parse3(in[2],out[2]);

		String[] inToken = new String[4];
		inToken[0] = "./data/WSJtoken";
		inToken[1] = "./data/Movietoken";
		inToken[2] = "./data/NFStoken";
		inToken[3] = "./data/RCVtoken";
		
		String[] outBi = new String[4];
		outBi[0] = "./data/WSJ-Uni";
		outBi[1] = "./data/Movie-Uni";
		outBi[2] = "./data/NFS-Uni";
		outBi[3] = "./data/RCV-Uni";
		for(int i = 0; i < 4; i++){
		    writeFile(getUnigrams(inToken[i]), outBi[i]);
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

	
	public static HashMap<String,Integer> getUnigrams(String filename) throws IOException{
		HashMap<String,Integer> set = new HashMap<String,Integer>();
		BufferedReader input = new BufferedReader(new FileReader(filename));
		String line = null;
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
				for(int i = 0; i < readin.length;i++){
						String b = readin[i];
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
	    input.close();
	    return set;
	}
	
	public static void writeFile(HashMap<String,Integer> mp, String output) throws IOException{
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
