public class myString implements Comparable<myString> {
	private String value;
	public int count;
	
	public myString(){
		value = "";
	}
	
	public myString(String v, int c){
		count = c;
		value = v;
	}
	
	public int compareTo(myString t){
		if(this.count>t.count){
			return 1;
		}
		else if(this.count<t.count){
			return -1;
		}
		else{
			//count is equal
			return -value.compareTo(t.value);
		}
	}
	public String value(){
		return value;
	}
}
