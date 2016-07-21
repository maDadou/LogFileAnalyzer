import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tests {

	static HashMap<Integer, Request> requestsinProcess = new HashMap<Integer, Request>();
	static HashMap<String, Request> requestsCompleted = new HashMap<String, Request>();
	static HashMap<String,RequestType> requestTypes= new HashMap<String, RequestType>();
	static HashSet<RequestType> requestsSet=new HashSet<RequestType>();
	
	
	
	public static void readLog(String file, boolean print){
		int counter = 1;
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("production.log")));
			String lecture = "";
			while (lecture != null) {
				try {
					lecture = br.readLine();
					if (lecture != null) {

						Pattern regexLine = Pattern.compile(
								"^[IE]\\, \\[\\d+(-\\d+)+T(\\d+:)+\\d+\\.\\d+ #(\\d+)\\]  \\w+ -- : (.*)$");
						//I, [2016-07-14T06:37:48.815654 #16960]  INFO -- : Started GET "/" for 95.213.164.154 at 2016-07-14 06:37:48 +0000
						Matcher m = regexLine.matcher(lecture);

						if (m.find()) {
							int process = Integer.parseInt(m.group(3));
							Message message = new Message(m.group(4));

							if (message.getKind().equals("Started")) {
								Request req = new Request(message);
								requestsinProcess.put(process, req);
							}
							if (message.getKind().equals("Completed")) {
								Request req = requestsinProcess.get(process);
								if (req != null) {
									req.complete(message);
									requestsCompleted.put(req.getType(),req);
									requestsinProcess.remove(process, req);
									
									RequestType reqType=requestTypes.get(req.getType());
									if(reqType!=null){
										reqType.addRequest(req);
										
									}
									else{
										RequestType r= new RequestType(req);
										requestTypes.put(req.getType(), r);
										requestsSet.add(r);
										
										
									}
									if(print){
										System.out.println(counter + ". " + req);
									}
									counter++;
									
								}
							}

						}
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		
		readLog("production.log",false);
		//RequestComparator.sortByTotalTime();
		//RequestComparator.sortByAverageTime();
		RequestComparator.sortByMaximumTime();
		
	}

}
