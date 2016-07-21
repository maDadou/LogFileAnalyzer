package comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {
	private String kind;
	private int time;
	private String type;
	private int code;

	public Message(String s){
		
		Pattern start = Pattern.compile("Started (\\w{3,6} \\W.*) for ([\\d\\.]+) at ");
		Matcher mStart = start.matcher(s);
		
		Pattern completed = Pattern.compile("Completed (\\d{3}) \\w.* in (\\d+)ms ");
		Matcher mCompleted = completed.matcher(s);
		
		if(mStart.find()){
			kind="Started";
			type=mStart.group(1);
			Pattern end= Pattern.compile("[\\?$]");
			type=end.split(type)[0];
			Pattern id=Pattern.compile("/\\d+([/\"])");
			Pattern id2=Pattern.compile("/\\d+$");
			Matcher mID=id.matcher(type);
			if(mID.find()){
				type=mID.replaceAll("/id"+mID.group(1));
				}
			Matcher mID2=id2.matcher(type);
			if(mID2.find()){
				type=mID2.replaceAll("/id");
				}
			time=-1;
			code=0;
		} else if(mCompleted.find()){
			kind="Completed";
			time=Integer.parseInt(mCompleted.group(2));
			code=Integer.parseInt(mCompleted.group(1));
			type="";
		} else{
			kind="Other";
			time=-1;
			code=0;
			type="";
		}	
	}
	
	public String getKind(){
		return this.kind;
	}
	
	public int getCode(){
		return code;
	}
	public int getTime(){
		return time;
	}
	public String getType(){
		return this.type;
	}

}
