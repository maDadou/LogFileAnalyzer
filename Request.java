package comparator;

public class Request {
	private String type;
	
	private int code;
	private int time;
	
	//m must be of kind Started
	public Request(Message m){
		
			this.type= m.getType();	
		
	}
	
	//m must be of kind Completed
	public void complete(Message m){
		this.code=m.getCode();
		this.time=m.getTime();
	}
	
	public String getType(){
		return this.type;
	}
	
	public int getTime(){
		return this.time;
	}
	
	public String toString(){
		return (type+" "+code+" "+time+"ms");
	}
	
	

}
