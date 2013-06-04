package WSD;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoadDictionaryXML{

	private HashMap<String, List<String>> senseObjHashMap;
	
	public LoadDictionaryXML() {
		// TODO Auto-generated constructor stub
		senseObjHashMap = new HashMap<String, List<String>>();
	}
	
	public void readXML() throws IOException {
		parse("data/TunedDictionary.xml", "data/outDict.data");
		printHashMap();
	}
	
	// parse the original XML style input file
	public void parse(String inFile, String outFile) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(inFile));
		FileWriter fstream = new FileWriter(outFile);
		BufferedWriter output = new BufferedWriter(fstream);
		String line = null;
		Pattern p = Pattern.compile("<lexelt item=\"(.+?)\\..?\"");
		Pattern p2 = Pattern.compile("<sense.+gloss=\"(.+?)\"");
		long records = 0;
		String keyString = null;
		while ((line = input.readLine()) != null) {
			Matcher mp = p.matcher(line);
			Matcher mp2 = p2.matcher(line);
			if (mp.find()) {
				//System.out.println(mp.group(1) + "\n");
				records++;
				keyString = mp.group(1);
				List<String> value = new ArrayList<String>();
				senseObjHashMap.put(keyString, value);
			}
			else if (mp2.find()) {
				List<String>value =  senseObjHashMap.get(keyString);
				String senseValue = mp2.group(1);
				senseValue = senseValue.replaceAll("(\\w+)\\p{Punct}(\\s|$)", "$1$2");
				senseValue = senseValue.replaceAll("([a-z]+)[(?:!.,;)]*", "$1");
				senseValue = senseValue.replaceAll("\\.", "");
				senseValue = senseValue.replace('(', ' ');
				senseValue = senseValue.replace(')', ' ');
				senseValue = senseValue.trim().replaceAll("\\s+", " ");
				value.add(senseValue);
				//System.out.println(senseValue);
				senseObjHashMap.put(keyString, value);
			}
			else if (line.equals("</lexelt>")) {
				keyString = null;
				System.out.println("\n\n");
			}
		}
		System.out.println("Number of records:" + records + "\n");
		input.close();
		output.close();
	}
	
	private void printHashMap() {
		Iterator<String> it = senseObjHashMap.keySet().iterator();
		while (it.hasNext()){
			String keyString = (String)it.next();
			List<String> value = senseObjHashMap.get(keyString);
			Iterator<String> itr = value.iterator();
			while (itr.hasNext()) {
				System.out.println("Key :" + keyString + " Value :" + itr.next());
			}
		}
	}
	
	public void matchSense(String targetWord, String context, double[] matchRatio) {
		if (!senseObjHashMap.containsKey(targetWord)) {
			return;
		}
		
		System.out.println("Entered Rakesh");
		List<String> targetSenses = senseObjHashMap.get(targetWord);
		System.out.println("targetWord:" + targetWord);
		System.out.println("Context:" + context);
		for (int k = 0; k < targetSenses.size(); k++) {	
			int weight = 0;
			System.out.println("Target Sense :" + targetSenses.get(k));
	    	StringTokenizer targetToken = new StringTokenizer(targetSenses.get(k).toLowerCase()," ");
	    	String[] targetWords = new String[targetToken.countTokens()];
			for(int i = 0; i < targetWords.length; i++){
				targetWords[i] = targetToken.nextToken();
				targetWords[i] = DictionaryClass.stemmer.Stem(targetWords[i]);
			}
	    	if (targetWords.length == 0) {
	    		return;
	    	}
	    
	    	List<HashMap<String, Integer>> targethashMaps = new ArrayList<HashMap<String, Integer>>();
	    	// Construct the hashmaps with counts for targetSense
	    	for (int i = 0; i < targetWords.length; i++ ) {
	    		HashMap<String, Integer> map = new HashMap<String, Integer>();
	    		for (int j = 0; j < (targetWords.length)-i; j++) {
	    			String s = targetWords[j];
	    			int count = 1;
	    			while (count <= i) {
	    				s = s + " " + targetWords[j+count];
	    				count++;
	    			}
	    			
	    			if (!map.containsKey(s)) {
	    				//System.out.println("value : " + s);
	    				map.put(s, 1);
	    			}
	    			else {
	    				int value = map.get(s);
	    				value++;
	    				map.put(s, value);
	    			}
	    		}
	    		if (i == 0) {
	    			String stemTarget = DictionaryClass.stemmer.Stem(targetWords[i]);
	    			map.put(stemTarget, 1);
	    		}
	    		targethashMaps.add(map);
	    	}
	    	if (senseObjHashMap.containsKey(context)) {
	    		List<String> contextSenses = senseObjHashMap.get(context);
	    		for (int j = 0; j < contextSenses.size(); j++) {
	    			System.out.println("Context Sense:" + contextSenses.get(j));
	    			int matchLength = matchSentence(targethashMaps, contextSenses.get(j));
	    			weight = weight + matchLength;
	    		}
	    	}
	    	String stemContext = DictionaryClass.stemmer.Stem(context);
	    	if (targethashMaps.get(0).containsKey(stemContext)) {
	    		weight = weight + 10;
	    	}
			matchRatio[k+1] = matchRatio[k+1] + (double)weight;
		}
	}
	
	private int matchSentence(List<HashMap<String, Integer>> targetHashMaps, String contextSense) {
		int weight = 0;
    	StringTokenizer contextToken = new StringTokenizer(contextSense.toLowerCase()," ");
    	String[] contextWords = new String[contextToken.countTokens()];
		for(int i = 0; i < contextWords.length; i++){
			contextWords[i] = contextToken.nextToken();
			contextWords[i] = DictionaryClass.stemmer.Stem(contextWords[i]);
		}    	
    	if (contextWords.length == 0) {
    		return 0;
    	}
    	
    	/*
    	for (int i = 0; i < targetHashMaps.size(); i++) {
    		Iterator<String> it = targetHashMaps.get(i).keySet().iterator();
    		while (it.hasNext()){
    			String keyString = (String)it.next();
    			int value = (int)targetHashMaps.get(i).get(keyString);
    			System.out.println("Key :" + keyString + " Value :" + value);
    		}	
    	}
    	*/
    	
    	// Check the targetHashMaps and calculate the weight
    	for (int i = 0; i < contextWords.length; i++ ) {
    		for (int j = 0; j < (contextWords.length)-i; j++) {
    			String s = contextWords[j];
    			int count = 1;
    			while (count <= i) {
    				s = s + " " + contextWords[j+count];
    				count++;
    			}
    			
    			//System.out.println("S :" + s);
    			if (targetHashMaps.contains(i) && targetHashMaps.get(i).containsKey(s)) {
    				   System.out.println("Rakesh Value : "+s);
    				   weight = weight + (i+1);
    			}
    		}
    	}
		return weight;
	}
}
