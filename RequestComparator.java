package comparator;
import java.util.LinkedList;
import java.util.TreeMap;

public class RequestComparator {
	
	
	
	
	static public String sortByTotalTime(){
		int cont =0;
		String print ="";
		TreeMap<Integer,LinkedList<RequestType>> requestsSorted=new TreeMap<Integer, LinkedList<RequestType>>();
		for(RequestType r : Tests.requestsSet){
			LinkedList<RequestType> l= requestsSorted.get(r.totalTime);
			if(l==null){
				l=new LinkedList();
				l.add(r);
				requestsSorted.put((Integer)r.totalTime,l);
			}
			else{l.add(r);}
		}
		int size = requestsSorted.size();
		for (int i=0; i<size; i++) {
			
			Integer j= requestsSorted.lastKey();
			LinkedList<RequestType> l=requestsSorted.get(j);
			for(RequestType v:l){
				print=print + v.toString()+"\n"+"\n";
				cont+=v.getNumber();	
			}
			requestsSorted.remove(j);
			
			
		}
		
		print=print+"Number of requests treated : "+cont+ "\n";
		System.out.print(print);
		return print;
		
	}
		static public String sortByAverageTime(){
			String print="";
			int cont =0;
			TreeMap<Double,LinkedList<RequestType>> requestsSorted=new TreeMap<Double, LinkedList<RequestType>>();
			for(RequestType r : Tests.requestsSet){
				LinkedList<RequestType> l= requestsSorted.get(r.averageTime);
				if(l==null){
					l=new LinkedList();
					l.add(r);
					requestsSorted.put((Double)r.averageTime,l);
				}
				else{l.add(r);}
			}
			int size = requestsSorted.size();
			for (int i=0; i<size; i++) {
				
				Double j= requestsSorted.lastKey();
				LinkedList<RequestType> l=requestsSorted.get(j);
				for(RequestType v:l){
					print=print + v.toString()+"\n"+"\n";
					cont+=v.getNumber();
						
				}
				requestsSorted.remove(j);
				
				
			}
			print=print+"Number of requests treated : "+cont+"\n";
			
			return(print);
	}
		
static public String sortByMaximumTime(){
	String print="";		
	int cont =0;
	TreeMap<Integer,LinkedList<RequestType>> requestsSorted=new TreeMap<Integer, LinkedList<RequestType>>();
	for(RequestType r : Tests.requestsSet){
		LinkedList<RequestType> l= requestsSorted.get(r.maxTime);
		if(l==null){
			l=new LinkedList();
			l.add(r);
			requestsSorted.put((Integer)r.maxTime,l);
		}
		else{l.add(r);}
	}
	int size = requestsSorted.size();
	for (int i=0; i<size; i++) {
		
		Integer j= requestsSorted.lastKey();
		LinkedList<RequestType> l=requestsSorted.get(j);
		for(RequestType v:l){
			print=print + v.toString()+"\n"+"\n";
			cont+=v.getNumber();
				
		}
		requestsSorted.remove(j);
		
		
	}
	print =print+ "Number of requests treated : "+cont+"\n";
	
	return print;
	}
	
	
	
	
	
	
	
	
	
	
	
}
