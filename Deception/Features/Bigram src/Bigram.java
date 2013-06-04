package SVM;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.State;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import SVM.Stemmer;

public class Bigram {

	/**
	 * @param args
	 */
	
	public static Stemmer stemmer = new Stemmer();
    public static StopWords stopWords = new StopWords();
    
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(new File("data/Train.txt"));
		Scanner s1 = new Scanner(new File("data/Validation.txt"));
		Scanner s2 = new Scanner(new File("data/Test.txt"));
		String out = "data/outBiTrain.txt";
		String out1 = "data/outBiVal.txt";
		String out2 = "data/outBiTest.txt";
		String outUni = "data/outUniTrain.txt";
		String outUni1 = "data/outUniVal.txt";
		String outUni2 = "data/outUniTest.txt";
		readFile(s, out);
		readFile(s1, out1);
		readFile(s2, out2);
		s.close();
		s1.close();
		s2.close();
		
		s = new Scanner(new File("data/Train.txt"));
		s1 = new Scanner(new File("data/Validation.txt"));
		s2 = new Scanner(new File("data/Test.txt"));
		readFile1(s, outUni);
		readFile1(s1, outUni1);
		readFile1(s2, outUni2);
		s.close();
		s1.close();
		s2.close();
	}
	
	private static void readFile1(Scanner s, String outFile) throws IOException {
		FileWriter fstream = new FileWriter(outFile);
		BufferedWriter out = new BufferedWriter(fstream);
		String outReview;
		s.useDelimiter("\\n");
		while (s.hasNext()) {
			String value = s.next();
			String isTruthful = null;
			String isDeceptive = null;
			String review = null;
			
			Scanner t = new Scanner(value);
			t.useDelimiter(",");
			int state = 0;
			while (t.hasNext()) {
				String value1 = t.next();
				if (state == 0) {
					isTruthful = value1;
					out.write(trimLeft(isTruthful) + ",");
					state ++;
				}
				else if (state == 1) {
					isDeceptive = value1;
					out.write(isDeceptive + ",");
					state ++;
				}
				else {
					review = value1;
					review = trimLeft(review);
					review = trimRight(review);
					//outReview = getBigrams(review);
					out.write(review + "\n");
				}
			}
		}
		out.close();
	}
	
	public static String trimLeft(String s) {
	    return s.replaceAll("^\\s+", "");
	}
	 
	public static String trimRight(String s) {
	    return s.replaceAll("\\s+$", "");
	} 
	
	private static void readFile(Scanner s, String outFile) throws IOException {
		FileWriter fstream = new FileWriter(outFile);
		BufferedWriter out = new BufferedWriter(fstream);
		String outReview;
		s.useDelimiter("\\n");
		while (s.hasNext()) {
			String value = s.next();
			String isTruthful = null;
			String isDeceptive = null;
			String review = null;
			
			Scanner t = new Scanner(value);
			t.useDelimiter(",");
			int state = 0;
			while (t.hasNext()) {
				String value1 = t.next();
				if (state == 0) {
					isTruthful = value1;
					out.write(trimLeft(isTruthful) + ",");
					state ++;
				}
				else if (state == 1) {
					isDeceptive = value1;
					out.write(isDeceptive + ",");
					state ++;
				}
				else {
					review = value1;
					review = trimLeft(review);
					review = trimRight(review);
					outReview = getBigrams(review);
					out.write(outReview + "\n");
				}
			}
		}
		out.close();
	}
	
	public static String getBigrams(String line) throws IOException{
			String out = "";
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
			
			if(readin.length > 0){
				int i;
				for(i = 1; i < readin.length; i++) {
					String b = "";
					b = readin[i-1] + "-" + readin[i];
					if (out.equals("")) {
						out = b;
					}
					else {
						out = out + " " + b;
					}
				}
				//out = out + " " + readin[i-1] + "END";
			}
			return out;
	}
}
