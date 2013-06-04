package WSD;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;


public class DictionaryClass {

	/**
	 * @param args
	 */
	private static LoadDictionaryXML xmlDat;
	public static Stemmer stemmer = new Stemmer();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		xmlDat = new LoadDictionaryXML();
		// Load the dictionary into the memory.
		xmlDat.readXML();
		Scanner s = new Scanner(new File("data/TunedTestData.data"));
		String out = "data/outTunedTestdata.data";
		String outMatch = "data/outMatchRatioTest.data";
		readTokens(s, out, outMatch);
		s.close();
	}
	
	private static void readTokens(Scanner s, String outFile, String outMatchFile) throws IOException{
		int state;
		FileWriter fstream = new FileWriter(outFile);
		BufferedWriter out = new BufferedWriter(fstream);
		FileWriter fstreamMatch = new FileWriter(outMatchFile);
		BufferedWriter outMatch = new BufferedWriter(fstreamMatch);
		s.useDelimiter("\\n");
		long totMatch = 0;
		long matchNum = 0;
		long totNum = 0;
		
		while(s.hasNext()) {
			String current = s.next();
			Scanner t = new Scanner(current);
			t.useDelimiter(" @ | @|@ ");
			String targetWord = null;
			String prevContext = null;
			String nextContext = null;
			String headWord = null;
			state = 1;
			Integer[] trainMatch = null;
			double[] matchRatio = null;
			int totalWeight = 0;
			int numMatches = 0;
			while (t.hasNext()) {
				String value = t.next();
				if (state == 1) {
					String[] partStrings = value.split(" ");
					//out.write("target word :" + partStrings[0] + "\n");
					for (int i=1; i<partStrings.length; i++) {
						//out.write("sense "+ i + ": "+ partStrings[i] + "\n");
						//System.out.println("sense " + i + " :" + partStrings[i] + "\n");
					}
					trainMatch = new Integer[partStrings.length-1];
					matchRatio = new double[partStrings.length-1];
					for (int i=0; i<matchRatio.length; i++) {
						matchRatio[i] = 0.0;
						trainMatch[i] = new Integer(partStrings[i+1]);
					}
					
					targetWord = partStrings[0];
					//System.out.println("target-word :" + targetWord);
				}
				else if (state == 2) {
					//out.write("prev-context :" + value + "\n");
					prevContext = value;
				}
				else if (state == 3) {
					//out.write("head :" + value + "\n");
					headWord = value;
				}
				else {
					nextContext = value;
					//out.write("next-context :"+ value + "\n");
				}
				state ++;
			} // End Inner while
		
			matchAllSenses(targetWord, prevContext, nextContext, matchRatio);

			for (int i=0; i<matchRatio.length; i++) {
				if (matchRatio[i] > (double)0.0) {
					numMatches++;
					totalWeight += (int) matchRatio[i];
				}
			}

			//System.out.println("Match Ratios..");
			outMatch.write(targetWord + ":" + "\n");
			//out.write(targetWord + ":" + "\n");
			Integer[] match = new Integer[matchRatio.length];
			boolean all = true;
			for (int i=1; i<matchRatio.length; i++) {
				/*
				if (totalWeight == 0 && i == 0) {
					match[i] = 1;
					matchRatio[i] = 1.0;
				}
				else {
				*/
					if (totalWeight == 0) {
						match[i] = 0;
						matchRatio[i] = 0.0;
					}
					else {
						matchRatio[i] = matchRatio[i] / totalWeight;
						outMatch.write(matchRatio[i] + "\n");
						if (matchRatio[i] == (double)1) {
							match[i] = 1;
							all = false;
						}
						else {
							match[i] = 0;
						}
					}
				//}
			}
			
			if (totalWeight == 0) {
				matchRatio[0] = 1.0;
			}
			else {
				matchRatio[0] = 0.0;
			}
			
			if (all) {
				match[0] = 1;
			}
			else {
				match[0] = 0;
			}
			
			for (int i=0; i<matchRatio.length; i++) {
				out.write(match[i] + "\n" );
				outMatch.write(matchRatio[i] + "\n");
			}
			
			// Check for match with training set.
			for (int i=0; i < match.length; i++) {
			    //out.write("match : " + match[i].intValue() + " trainMatch : " + trainMatch[i].intValue() + "\n"); 
				if (match[i].equals(1)) {
					totMatch ++;
					if (trainMatch[i].equals(1)) {
						matchNum++;
					}
				}
				else if (match[i].equals(0) && trainMatch[i].equals(1)) {
					totNum++;
				}
			}
			
			// Check for match with training set.
			for (int i=0; i < match.length; i++) {
			    //out.write("match : " + match[i].intValue() + " trainMatch : " + trainMatch[i].intValue() + "\n"); 
				if (match[i].equals(0)) {
					totMatch ++;
					if (trainMatch[i].equals(0)) {
						matchNum++;
					}
				}
				else if (match[i].equals(1) && trainMatch[i].equals(0)) {
					totNum++;
				}
			}
			
			outMatch.write("\n");
		} // End Outer while
		
		outMatch.write("Precision: " + ((double)matchNum/totMatch) + "\n");
		outMatch.write("Recall: " + ((double)matchNum/(matchNum + totNum)) + "\n");
		s.close();
		out.close();
		outMatch.close();
	}
	
	private static void matchAllSenses(String targetWord, String prevContext,
										String nextContext, double[] matchRatio) {
		
		String testreg = "[^a-zA-Z0-9\\d+\\.\\d{2}?\\s]";
		Pattern matchsip = Pattern.compile(testreg);
		Matcher mp = matchsip.matcher(prevContext);
		prevContext = mp.replaceAll("");
		mp = matchsip.matcher(nextContext);
		nextContext = mp.replaceAll("");
		
		Pattern p = Pattern.compile("(.+?)\\..?");
		mp = p.matcher(targetWord);
		if (mp.find()) {
			targetWord = mp.group(1);
		}
		
		nextContext = nextContext.replaceAll("(\\w+)\\p{Punct}(\\s|$)", "$1$2");
		nextContext = nextContext.replaceAll("\\.", "");
		nextContext = nextContext.replace('(', ' ');
		nextContext = nextContext.replace(')', ' ');
		nextContext = nextContext.trim().replaceAll("\\s+", " ");
		
		prevContext = prevContext.replaceAll("(\\w+)\\p{Punct}(\\s|$)", "$1$2");
		prevContext = prevContext.replaceAll("\\.", "");
		prevContext = prevContext.replace('(', ' ');
		prevContext = prevContext.replace(')', ' ');
		prevContext = prevContext.trim().replaceAll("\\s+", " ");
		
		System.out.println("targetWord :" + targetWord);
		Scanner wordsScanner = new Scanner(prevContext);
		System.out.println("Prev Context:" + prevContext);
		while (wordsScanner.hasNext()) {
			String context = wordsScanner.next();
			//context = stemmer.Stem(context);
			xmlDat.matchSense(targetWord, context, matchRatio);
		}
		
		wordsScanner.close();
		
		wordsScanner = new Scanner(nextContext);
		System.out.println("nextContext:" + nextContext);
		while (wordsScanner.hasNext()) {
			String value = wordsScanner.next();
			//value = stemmer.Stem(value);
			xmlDat.matchSense(targetWord, value, matchRatio);
		}
		wordsScanner.close();
	}
}