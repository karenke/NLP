/**
 * 
 */
package SVM;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author rakeshc
 *
 */
public class SvmFeature {

	/**
	 * @param args
	 * @throws IOException 
	 */
	//public static Stemmer stemmer = new Stemmer();
    public static StopWords stopWords = new StopWords();
    public static HashMap<String, Long> featureMap = new HashMap<String, Long>();
    public static long featureValue = 1;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(new File("outBiTest.txt"));
		//Scanner sTest = new Scanner(new File("data/pySRS_train2.txt"));
		String out = "outBiTest1.txt";
		//String outTest = "data/outSTrain2.txt";
		boolean flag = true;
		readFile(s, out, flag);
	
		//readFile(sTest, outTest, flag);
		s.close();
		//sTest.close();
	}
	
	private static void readFile(Scanner s, String outFile, boolean flag) throws IOException {
		FileWriter fstream = new FileWriter(outFile);
		BufferedWriter out = new BufferedWriter(fstream);
		String sentence;
		String tag="0";
		String tag1="0";
		String tag3="";
		Pattern p = Pattern.compile("(.+?),(.+?),(.+?)+");
		if (flag) {
			p = Pattern.compile("(.+?),(.+?),(.*)+");
		}
		s.useDelimiter("\\n");
		while (s.hasNext()) {
			String value = s.next();
			Matcher mpp = p.matcher(value);
			if (mpp.find()) {
				sentence = mpp.group();
				if (flag) {
					tag = mpp.group(1);
					tag1= mpp.group(2);
					tag3 = tag+tag1;
					if (tag3.contains("00")){
					tag3="-2";
					}
					if (tag3.contains("01")){
						tag3="-1";
						}
					if (tag3.contains("10")){
						tag3="1";
						}
					if (tag3.contains("11")){
						tag3="2";
						}
				}	
				
				SortedMap<Long, Integer> map = new TreeMap<Long, Integer>();
				//printHashMap(sentenceHashMap);
				StringTokenizer currentToken = new StringTokenizer(sentence.toLowerCase()," ");
				String[] currentWords = new String[currentToken.countTokens()];
				for( int i = 0; i < currentWords.length; i++){
					long featureVal;
					currentWords[i] = currentToken.nextToken();
					//currentWords[i] = stemmer.Stem(currentWords[i]);
					//System.out.println(currentWords[i]);

					if (!featureMap.containsKey(currentWords[i])) {
						featureMap.put(currentWords[i], featureValue);
						featureVal = featureValue++;
					}
					else {
						featureVal = featureMap.get(currentWords[i]);
					}
					
					if (!map.containsKey(featureVal)) {
						map.put(featureVal, 1);
					}
					else {
						Integer count = map.get(featureVal);
						count ++;
						map.put(featureVal, count);
					}
				}
				
				String str =tag3;								
				Iterator<Long> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
				  Long featureVal = (Long) iterator.next();
				  Integer count = map.get(featureVal);
				  str = str + " " + featureVal + ":" + count;
				}
				printMap(map);
				out.write(str + "\n");
			}
		}
		
		//printHashMap(featureMap);
		System.out.println("Feature Values: " + featureValue);
		out.close();
	}

	private static void printHashMap(HashMap<String, Long> featureMap) {
		// TODO Auto-generated method stub
		Iterator<String> it = featureMap.keySet().iterator();
		while (it.hasNext()){
			String keyString = (String)it.next();
			Long value = featureMap.get(keyString);
			System.out.println("Key: " + keyString + " Value :" + value); 
		}
	}
	
	private static void printMap(SortedMap<Long, Integer> map) {
		// TODO Auto-generated method stub
		Iterator<Long> it = map.keySet().iterator();
		while (it.hasNext()){
			Long key = (Long)it.next();
			Integer value = map.get(key);
			System.out.println("Key: " + key + " Value :" + value); 
		}
	}
}
