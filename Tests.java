package comparator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileReader;

public class Tests {

	static HashMap<Integer, Request> requestsinProcess = new HashMap<Integer, Request>();
	static HashMap<String, Request> requestsCompleted = new HashMap<String, Request>();
	public static HashMap<String, RequestType> requestTypes = new HashMap<String, RequestType>();
	public static HashSet<RequestType> requestsSet = new HashSet<RequestType>();

	public static Pair readLog(File file, int start, Date deb, Date end) throws ParseException {
		String print = "";
		int counter = start;
		// LLLLLLL
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.FFFFFF");
		// LLLLLLLLL
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String lecture = "";
			Date current = null;
			while (lecture != null && (current == null || end == null || current.before(end))) {
				try {
					lecture = br.readLine();
					if (lecture != null) {

						Pattern regexLine = Pattern
								.compile("^[IE]\\, \\[(\\d+(-\\d+)+T(\\d+:)+\\d+\\.\\d+) #(\\d+)\\]  \\w+ -- : (.*)$");
						// I, [2016-07-14T06:37:48.815654 #16960] INFO -- :
						// Started GET "/" for 95.213.164.154 at 2016-07-14
						// 06:37:48 +0000
						Matcher m = regexLine.matcher(lecture);

						if (m.find()) {

							current = format.parse(m.group(1));
							if ((deb == null || (current.after(deb)) && (end == null || current.before(end)))) {
								int process = Integer.parseInt(m.group(4));
								Message message = new Message(m.group(5));

								if (message.getKind().equals("Started")) {
									Request req = new Request(message);
									requestsinProcess.put(process, req);
								}
								if (message.getKind().equals("Completed")) {
									Request req = requestsinProcess.get(process);
									if (req != null) {
										req.complete(message);
										requestsCompleted.put(req.getType(), req);
										requestsinProcess.remove(process, req);

										RequestType reqType = requestTypes.get(req.getType());
										if (reqType != null) {
											reqType.addRequest(req);

										} else {
											RequestType r = new RequestType(req);
											requestTypes.put(req.getType(), r);
											requestsSet.add(r);

										}
										print = print + counter + ". " + req + "\n";

										counter++;

									}
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
		return new Pair(print, counter);

	}

	public static Pair readLog(File file, int start) throws ParseException {
		return (readLog(file, start, null, null));
	}

	public static void initialize() {
		requestsinProcess = new HashMap<Integer, Request>();
		requestsCompleted = new HashMap<String, Request>();
		requestTypes = new HashMap<String, RequestType>();
		requestsSet = new HashSet<RequestType>();

	}

}
