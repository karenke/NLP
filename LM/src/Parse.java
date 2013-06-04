/*
 *  Run this program using : "java Parse"
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


public class Parse {
	
	
	/*
	 * Read XML, extract TEXT
	 */
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException{
	
                // parse the training set
		String[] inTrain = new String[4];
		inTrain[0] = "./data/wsj.train";
		inTrain[1] = "./data/movie.train";
		inTrain[2] = "./data/nfs.train";
		inTrain[3] = "./data/rcv1.train";
		
		String[] outTrain = new String[4];
		outTrain[0] = "./data/newWSJ.train";
		outTrain[1] = "./data/newMovie.train";
		outTrain[2] = "./data/newNFS.train";
		outTrain[3] = "./data/newRCV.train";

                // parse the test set
		String[] inTest = new String[4];
		inTest[0] = "./data/wsj.test";
		inTest[1] = "./data/movie.test";
		inTest[2] = "./data/nfs.test";
		inTest[3] = "./data/rcv1.test";
		
		String[] outTest = new String[4];
		outTest[0] = "./data/newWSJ.test";
		outTest[1] = "./data/newMovie.test";
		outTest[2] = "./data/newNFS.test";
		outTest[3] = "./data/newRCV.test";

		for(int i = 0; i < 2; i++){
			parse(inTrain[i], outTrain[i]);
			parse(inTest[i], outTest[i]);
		}
		
		parse2(inTrain[3], outTrain[3]);
                parse2(inTest[3], outTest[3]);
		parse3(inTrain[2], outTrain[2]);
                parse3(inTest[2], outTest[2]);
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
}
