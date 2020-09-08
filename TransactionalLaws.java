package com.customdata.component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/*
 * T1234, 2020 – 03 – 01 , 3:15 pm, start 
 * T1235, 2020 – 03 – 01 , 3:16 pm, start
 * T1236, 2020 – 03 – 01 , 3:17 pm, start 
 * T1234, 2020 – 03 – 01 , 3:18 pm, End
 * T1235, 2020 – 03 – 01 , 3:18 pm, End
 */
public class TransactionalLaws {
	
	private static TransactionalLaws laws;
	private static HashMap<String,TxPair> map = new HashMap<>(); 
	
	private TransactionalLaws() { }
	public static TransactionalLaws getInstance() {
		if(laws ==null) 
			laws = new TransactionalLaws();
		return laws;
	}
	
	public static void main(String[] args) throws IOException {
		String data =  "T1234, 2020-03-01 , 3:15 pm, start,"
					+ "T1235, 2020-03-01 , 3:17 pm, start,"
					+ "T1236, 2020-03-01 , 3:17 pm, start,"
					+ "T1234, 2020-03-01 , 3:18 pm, End,"
					+ "T1235, 2020-03-01 , 3:18 pm, End";
		//String data = Files.readString(Paths.get("c:\\data\\data.txt"));
		TransactionalLaws laws = TransactionalLaws.getInstance();
		laws.parsingData(data);
	}
	public void parsingData(String data) {
		
		String split[] = data.split(",");
		TxPair pair= null;
		TxObject txObject=null;
		for (int i = 0; i < split.length; i++) {
			if(split[i].isBlank())continue;
			if(split[i].charAt(0)=='T') {
				if(map.containsKey(split[i])) {
					pair= map.get(split[i]);
				}else {
					pair=TransactionalLaws.getInstance().new TxPair();
					pair.setTxId(split[i]);
					map.put(split[i], pair);
				}
			}else if(split[i].contains("start")) {
				pair.setStart(txObject);
			}else if(split[i].contains("End")) {
				pair.setEnd(txObject);
			}else if(split[i].contains("pm") || split[i].contains("pm")) {
				txObject.setTime(split[i].trim());
			}else {
				txObject= TransactionalLaws.getInstance().new TxObject();
				txObject.setDate(split[i].trim());
			}
		}
		try {
			printAverageTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public void printAverageTime() throws ParseException {
		
		for (Map.Entry<String, TxPair> txEntry : map.entrySet()) {
			TxObject start=txEntry.getValue().getStart();
			TxObject end=txEntry.getValue().getEnd();
			if(start ==null || end ==null ) {
				System.out.println("Trasanction Id #"+txEntry.getKey()+" has no start or end time");
				continue;
			}
			
			String startDate=start.getDate();
			String startTime=start.getTime();
			String endDate=end.getDate();
			String endTime=end.getTime();
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd h:mm aa", Locale.ENGLISH);
			Date startD=df.parse(startDate +" "+startTime);
			Date endD=df.parse(endDate +" "+endTime);
			
			long difference = endD.getTime() - startD.getTime();
			System.out.println("Average time for Transaction Id #"+txEntry.getKey()+" = "+ difference/1000+" Seconds");
		}
	}
	class TxPair{
		private String txId;
		private TxObject start;
		private TxObject end;
		
		public void setEnd(TxObject end) {
			this.end = end;
		}
		
		public TxObject getEnd() {
			return end;
		}
		public void setStart(TxObject start) {
			this.start = start;
		}
		public TxObject getStart() {
			return start;
		}
		public void setTxId(String txId) {
			this.txId = txId;
		}
		public String getTxId() {
			return txId;
		}

		@Override
		public String toString() {
			return "TxPair [txId=" + txId + ", start=" + start + ", end=" + end + "]";
		}
		
	}
	class TxObject{
		private String date;
		private String time;
		
		public String getDate() {
			return date;
		}
		public String getTime() {
			return time;
		}
		
		public void setDate(String date) {
			this.date = date;
		}
		public void setTime(String time) {
			this.time = time;
		}
		
		@Override
		public String toString() {
			return "TxObject [date=" + date + ", time=" + time + "]";
		}
	}
	
}
