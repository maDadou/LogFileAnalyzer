package comparator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestType {
	String type;
	int totalTime;
	double averageTime;
	int number;
	int maxTime;
	
	//req should be completed
	public RequestType(Request req){
		number=1;
		type= req.getType();
		totalTime=req.getTime();
		averageTime=(double)totalTime;
		maxTime=req.getTime();
		
	}
	public RequestType(String type, int tot, double ave, int num, int max){
		this.type=type;
		totalTime=tot;
		averageTime=ave;
		number=num;
		maxTime=max;
	}
	
	public void add(RequestType req){
		this.totalTime+=req.getTotalTime();
		number+=req.getNumber();
		maxTime+=req.getMaxTime();
		averageTime+=req.getAverageTime();
	}
	
	public void addRequest(Request req){
		totalTime+=req.getTime();
		number++;
		averageTime=(double)totalTime/number;
		if(req.getTime()>maxTime){
			maxTime=req.getTime();
		}
	}
	public String getType(){
		return this.type;
	}
	public int getNumber(){
		return this.number;
	}
	public int getTotalTime(){
		return this.totalTime;
	}
	public double getAverageTime(){
		return this.averageTime;
	}
	public int getMaxTime(){
		return this.maxTime;
	}
	public String toString(){
		return(type+"\nNumber of requests : "+number+"\nAverage Time : "+averageTime+"\nMaximum Time : "+maxTime+"\nTotal Time : "+totalTime );
	}
	public String toJSONString(){
		Pattern guillement=Pattern.compile("\"");
		Matcher m=guillement.matcher(this.type);
		String typeBis = m.replaceAll("”");
		String res="    {\n";
		res=res+"        \"Type\": \""+typeBis+"\",\n";
		res=res+"        \"Number\": "+this.number+",\n";
		res=res+"        \"Average Time\": "+this.averageTime+",\n";
		res=res+"        \"Maximum Time\": "+this.maxTime+",\n";
		res=res+"        \"Total Time\": "+this.totalTime+"\n";
		res=res+"    }";
		
		
		   
		return res;
	}
	

}
