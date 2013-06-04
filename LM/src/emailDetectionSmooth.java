import java.util.*;
import java.io.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class emailDetectionSmooth {
	public static void main(String []args)throws IOException{
/*		//step 1: pre-process the input data
		Scanner s = new Scanner(new File("./data/email/beck-s.txt"));
		String out = "./data/email/newbeck-s.txt";
		readin(s,out);
		s.close();
		
		s = new Scanner(new File("./data/email/farmer-d.txt"));
		out = "./data/email/newfarmer-d.txt";
		readin(s,out);
		s.close();
		
		s = new Scanner(new File("./data/email/kaminski-v.txt"));
		out = "./data/email/newkaminski-v.txt";
		readin(s,out);
		s.close();*/
		
		//step 2: get all the unigrams & generate all the bigrams(trigrams)
		String filename = "./data/email/newbeck-s.txt";
		String out = "./data/email/beck-sUnigram";
		HashMap<String, Integer> beck_un = getUnigrams(filename);
		writeFile(beck_un,out);
		out = "./data/email/beck-sBigram";
		HashMap<String, Integer> beck_bi = getBigrams(filename);
		writeFile(beck_bi,out);
	
		
		out = "./data/email/beck-sTrigram";
		HashMap<String, Integer> beck_tri = getTrigrams(filename);
		writeFile(beck_tri,out);
		HashMap<String, Double> beck_biprob = readProb("./data/email/beck-sprob");
		
		Table beck = new Table(beck_biprob,"beck-s");
		
		filename = "./data/email/newfarmer-d.txt";
		out = "./data/email/farmer-dUnigram";
		HashMap<String, Integer> farmer_un = getUnigrams(filename);
		writeFile(farmer_un,out);
		out = "./data/email/farmer-dBigram";
		HashMap<String, Integer> farmer_bi = getBigrams(filename);
		writeFile(farmer_bi,out);
		out = "./data/email/farmer-dTrigram";
		HashMap<String, Integer> farmer_tri = getTrigrams(filename);
		HashMap<String, Double> farmer_biprob = readProb("./data/email/farmer-dprob");
		writeFile(farmer_tri,out);		
		Table farmer = new Table(farmer_biprob,"farmer-d");
		
		filename = "./data/email/newkaminski-v.txt";
		out = "./data/email/kaminski-vUnigram";
		HashMap<String, Integer> kaminski_un = getUnigrams(filename);
		writeFile(kaminski_un,out);
		out = "./data/email/kaminski-vBigram";
		HashMap<String, Integer> kaminski_bi = getBigrams(filename);
		writeFile(kaminski_bi,out);
		out = "./data/email/kaminski-vTrigram";
		HashMap<String, Integer> kaminski_tri = getTrigrams(filename);
		writeFile(kaminski_tri,out);
		HashMap<String, Double> kaminski_biprob = readProb("./data/email/kaminski-vprob");
		Table kaminski = new Table(kaminski_biprob,"kaminski-v");
		
		String fname = "./data/email/validation.txt";
		
		ArrayList<String> detection = new ArrayList();
		detection.addAll(runValidationSmooth(fname,beck,farmer,kaminski));
		
		fname = "./data/email/test.txt";
		detection.addAll(runValidationSmooth(fname,beck,farmer,kaminski));
		
		out = "./data/email/SmoothemailDetection";
		FileWriter fstream = new FileWriter(out);
		BufferedWriter output = new BufferedWriter(fstream);
		for(int i = 0;i<detection.size();i++){
			output.write(detection.get(i)+"\n");
		}
		output.close();
		
		
	}
	
	public static ArrayList<String> runValidation(String fname, Table b,Table f, Table k)throws IOException{
		ArrayList<String> result = new ArrayList();
		int corr = 0,count = 0;
		Scanner s = new Scanner(new File(fname));
		while(s.hasNext()){
			String line = s.nextLine();
			int t = line.indexOf("\t");
			String answer = line.substring(0,t);
			line = line.substring(t+1);
			line = emailProcess(line);
			String [] sentences = line.split(" .\n");
			double valueb = 0, valuef = 0,valuek = 0;
			for(String ss: sentences){
				HashMap<String, Integer> tempTrigrams = Trigrams(ss);
				Set<String> setTrigram = tempTrigrams.keySet();
				for(String Trigr: setTrigram){
					String [] dividTrigr = Trigr.split(" ");
					valueb = b.getProbablityTri(dividTrigr[0]+" "+dividTrigr[1], dividTrigr[2])+valueb;
					valuef = f.getProbablityTri(dividTrigr[0]+" "+dividTrigr[1], dividTrigr[2])+valuef;
					valuek = k.getProbablityTri(dividTrigr[0]+" "+dividTrigr[1], dividTrigr[2])+valuek;
				}
			}
			
			if(valueb==valuek&&valuef==valuek){
				if(b.name.compareTo(answer)==0){
//					corr++;
				}
				result.add(b.name);
			}
			else if(valueb>=valuef&&valueb>=valuek){
				result.add(b.name);
				if(b.name.compareTo(answer)==0){
					corr++;
				}
				else{
				}
			}
			else if(valuef>=valuek&&valuef>=valueb){
				result.add(f.name);
				if(f.name.compareTo(answer)==0){
					corr++;
				}
				else{
				}
			}
			else if(valuek>=valuef&&valuek>=valueb){
				result.add(k.name);
				if(k.name.compareTo(answer)==0){
					corr++;
				}
				else{
				}
			}

			count++;
		}
		
		System.out.println("correct:"+corr+"\ntotal:"+result.size());
		s.close();
		return result;
	}
	
	
	public static ArrayList<String> runValidationSmooth(String fname, Table b,Table f, Table k)throws IOException{
		ArrayList<String> result = new ArrayList();
		int corr = 0,count = 0;
		Scanner s = new Scanner(new File(fname));
		while(s.hasNext()){
			String line = s.nextLine();
			int t = line.indexOf("\t");
			String answer = line.substring(0,t);
			line = line.substring(t+1);
			line = emailProcess(line);
			String [] sentences = line.split(" .\n");
			double valueb = 0, valuef = 0,valuek = 0;
			for(String ss: sentences){
				HashMap<String, Integer> tempBigrams = Bigrams(ss);
				Set<String> setBigram = tempBigrams.keySet();
				for(String bigr: setBigram){
					String [] dividBigr = bigr.split(" ");
					valueb = b.getProbablitySmooth(dividBigr[0], dividBigr[1])+valueb;
					valuef = f.getProbablitySmooth(dividBigr[0], dividBigr[1])+valuef;
					valuek = k.getProbablitySmooth(dividBigr[0], dividBigr[1])+valuek;
				}
			}
			
			if(valueb==valuek&&valuef==valuek){
				result.add(b.name);
			}
			else if(valueb>=valuef&&valueb>=valuek){
				result.add(b.name);
				if(b.name.compareTo(answer)==0){
					corr++;
				}
				else{
				}
			}
			else if(valuef>=valuek&&valuef>=valueb){
				result.add(f.name);
				if(f.name.compareTo(answer)==0){
					corr++;
				}
				else{
				}
			}
			else if(valuek>=valuef&&valuek>=valueb){
				result.add(k.name);
				if(k.name.compareTo(answer)==0){
					corr++;
				}
				else{
				}
			}

			count++;
		}
		
		System.out.println("correct:"+corr+"\ntotal:"+result.size());
		s.close();
		return result;
	}
	/*
	 * Read in buffer and output the 
	 */
	public static void readin(Scanner s, String outFile)throws IOException{
		String line = null;
		StringBuffer result = new StringBuffer();
		while(s.hasNext()){
			line = s.nextLine();
			int t = line.indexOf("\t");
			line = line.substring(t+1);
			String temp = emailProcess(line);
			result.append(temp);
		}
		FileWriter fstream = new FileWriter(outFile);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(result.toString());
	}
	
	/*
	 * read in the probablity from the file
	 */
	public static HashMap<String, Double> readProb(String fname) throws IOException{
		Scanner s = new Scanner(new File(fname));
		HashMap<String, Double> temp = new HashMap();
		while(s.hasNext()){
			String line = s.nextLine();
			String [] t = line.split(" : ");
			double p = Double.parseDouble(t[1]);
			temp.put(t[0], p);
		}
		s.close();
		return temp;
	}
	
	/*
	 * For each piece of email, we are not going to take forwarded email/ into account since there are not author's words
	 * For example, in the training data, we get emails sent by Kay Chapman in both beck-s and kaminski-v, if we took these 
	 * emails into account, we might get lots of mistakes.
	 */
	public static String emailProcess(String s){
		String [] temp1 = s.split("-----");
		String [] temp2 = s.split("From:");
		String [] temp3 = s.split("To:");
		String temp = null;
		if(temp1[0].length()<temp2[0].length()){
			if(temp1[0].length()<temp3[0].length()){
				temp = temp1[0].toLowerCase();
			}
			else{
				temp = temp3[0].toLowerCase();
			}
		}
		else{
			if(temp2[0].length()<temp3[0].length()){
				temp = temp2[0].toLowerCase();
			}
			else{
				temp = temp3[0].toLowerCase();
			}			
		}
		temp = temp.replaceAll("\t", " ");//get rid of \t
		temp = temp.replaceAll("\\?+", "? ");
		temp = temp.replaceAll("!+", ". ");
		String [] result1 = temp.split("\\. ");
		StringBuffer result = new StringBuffer();
		/*
		 * Split the sentences by "?" and "."
		 */
		for(String s1: result1){
			if(s1.length()>0){
				String [] result2 = s1.split("\\? ");
				for(String s2: result2){
					if(s2.length()>0){
						result.append(s2+" .\n");
					}
				}
			}
		}
		return result.toString();
	}
	
	/*
	 * generate all the bigrams from the input file name
	 */
	public static HashMap<String,Integer> getBigrams(String filename) throws IOException{
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

	/*
	 * get all the bigrams from the input string 
	 */
	public static HashMap<String, Integer> Bigrams(String line){
		HashMap<String,Integer> set = new HashMap<String,Integer>();
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
		return set;
	}

	/*
	 * generate all the Trigrams from the input file name
	 */
	public static HashMap<String,Integer> getTrigrams(String filename) throws IOException{
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
			if(readin.length==1){
				String b = "START "+readin[0]+" END";
				set.put(b,1);
			}
			else if(readin.length > 0){
				for(int i = 0; i <= readin.length-1;i++){
					if(i != readin.length-1 && (readin[i+1].equals(".")||readin[i+1].equals("?"))){
						if(i != 0){
							String b = readin[i-1]+" "+readin[i] + " END";
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
						String b= "START " + readin[i]+" "+readin[i+1];
						if(!set.containsKey(b)){
							set.put(b, 1);
						}
						else{
							int count = set.get(b);
							count++;
							set.put(b, count);
						}
					}
					else if(i == readin.length-1){
						String b = readin[i-1] +" "+readin[i] +" END";
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
						String b = readin[i-1] + " " + readin[i]+" "+readin[i+1];
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
	
	/*
	 * get all the trigrams from the input string
	 */
	public static HashMap<String, Integer> Trigrams(String line){
		HashMap<String,Integer> set = new HashMap<String,Integer>();
    	String testreg = "[^a-zA-Z0-9\\d+\\.\\d{2}?\\s]";
        Pattern matchsip = Pattern.compile(testreg);
        Matcher mp = matchsip.matcher(line);
        line = mp.replaceAll("");
    	StringTokenizer token = new StringTokenizer(line.toLowerCase()," "); 
    	String[] readin = new String[token.countTokens()];
		for(int i = 0; i < readin.length; i++){
			readin[i] = token.nextToken();
		}
		if(readin.length==1){
			String b = "START "+readin[0]+" END";
			set.put(b,1);
			return set;
		}
		if(readin.length > 0){
			for(int i = 0; i <= readin.length-1;i++){
				if(i != readin.length-1 && (readin[i+1].equals(".")||readin[i+1].equals("?"))){
					if(i != 0){
						String b = readin[i-1]+" "+readin[i] + " END";
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
					String b= "START " + readin[i]+" "+readin[i+1];
					if(!set.containsKey(b)){
						set.put(b, 1);
					}
					else{
						int count = set.get(b);
						count++;
						set.put(b, count);
					}
				}
				else if(i == readin.length-1){
					String b = readin[i-1] +" "+readin[i] +" END";
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
					String b = readin[i-1] + " " + readin[i]+" "+readin[i+1];
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
		return set;
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
