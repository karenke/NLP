import java.io.*;
import java.util.*;

public class Main {
	public static void main(String [] args)throws Throwable{
		model truth = new model(1);
		model fake = new model(0);
		implementmodel(truth,fake,"./data/uni/outUniTrain.txt","./data/bi/outBiTrain.txt");
		implementmodel(truth,fake,"./data/uni/outUniVal.txt","./data/bi/outBiVal.txt");
		runvalidbi(truth,fake,"./data/bi/outBiTest.txt");
		runvaliduni(truth,fake, "./data/uni/outUniTest.txt");
		System.out.println("finish");
	}
	
	public static void runvaliduni(model t, model f, String un)throws Throwable{
		Scanner s = new Scanner(new File(un));
		ArrayList<Integer> result = new ArrayList();
		int total = 0;
//		int right = 0;
		while(s.hasNext()){
			total++;
			String line = s.nextLine();
			String [] data = line.split(",");
			
			if(t.prob_un(data[2])>f.prob_un(data[2])){
				result.add(t.label);
			}
			else{
				result.add(f.label);
			}
//			System.out.println(total);
/*			if(result.get(result.size()-1)==Integer.parseInt(data[0])){
				right++;
			}*/
		}
		s.close();
//		System.out.println(right+" out of "+total);
		String out = "./data/uniprediction";
		FileWriter fstream = new FileWriter(out);
		BufferedWriter output = new BufferedWriter(fstream);
		for(Integer i:result){
			output.write(i+"\n");
		}
		output.close();
	}
	
	public static void runvalidbi(model t, model f, String bi)throws Throwable{
		Scanner s = new Scanner(new File(bi));
		ArrayList<Integer> result = new ArrayList();
		int total = 0;
//		int right = 0;
		while(s.hasNext()){
			total++;
			String line = s.nextLine();
			String [] data = line.split(",");
			
			if(t.prob_bi(data[2])>f.prob_bi(data[2])){
				result.add(t.label);
			}
			else{
				result.add(f.label);
			}
//			System.out.println(total);
/*			if(result.get(result.size()-1)==Integer.parseInt(data[0])){
				right++;
			}*/
		}
		s.close();
//		System.out.println(right+" out of "+total);
		String out = "./data/biprediction";
		FileWriter fstream = new FileWriter(out);
		BufferedWriter output = new BufferedWriter(fstream);
		for(Integer i:result){
			output.write(i+"\n");
		}
		output.close();
	}
	
	public static void implementmodel(model t, model f, String uni, String bi)throws Throwable{
		Scanner s = new Scanner(new File(uni));
		while(s.hasNext()){
			String line = s.nextLine();
			String [] label = line.split(",");
			String l =label[0];
			if(Integer.parseInt(l)==1){
				//true review
				t.addUnigram(line);
			}
			else{
				//deceptive review
				f.addUnigram(line);
			}
		}
		s.close();
		
		s = new Scanner(new File(bi));
		while(s.hasNext()){
			String line = s.nextLine();
			String [] label = line.split(",");
			String l =label[0];
			if(Integer.parseInt(l)==1){
				//true review
				t.addBigram(line);
			}
			else{
				//deceptive review
				f.addBigram(line);
			}
		}
		s.close();
	}
}
