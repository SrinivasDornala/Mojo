package com.customdata.component;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Mojo {
	
	private static Mojo mojo= null;
	private HashMap<String,Integer> map = new HashMap<>();
	private List<String> li= new LinkedList<>();
	
	private Mojo() {}
	private static Mojo getInstance() {
		if(mojo==null) {
			synchronized (Mojo.class) {
				mojo= new Mojo();
			}
		}
		return mojo;
	}
	
	public static void main(String[] args) {
		String s= "Hello #Hello";
		String s1= "#Hello";
		String s2= "AABCC #H #M #abcd #H #H #";
		
		Mojo mojo= Mojo.getInstance();
		mojo.saveHashTag(s);
		mojo.saveHashTag(s1);
		mojo.saveHashTag(s2);
		mojo.sortByValue();
		mojo.printList();
	}
	public void saveHashTag(String str){
		String s[]=str.split(" ");
		for (int i = 0; i < s.length; i++) {
			if(!s[i].isEmpty() && s[i].length()>1 && s[i].charAt(0)=='#') {
				if(map.containsKey(s[i]))
					map.put(s[i],map.get(s[i])+1);
				else
					map.put(s[i], 1);
			}
		}
	}
	public void printList() {
		for (int i = 0; i<li.size() &&i <10; i++) {
			System.out.println(li.get(i));
		}
	}
	private void sortByValue()  { 
		List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list,Comparator.comparingInt(Map.Entry<String,Integer>::getValue).reversed());  
          
        for (Map.Entry<String, Integer> aa : list) 
            li.add(aa.getKey()); 
    } 
	
	public HashMap<String, Integer> getHashTagMap() {
		return map;
	}
	public List<String> getTopTenHashTags() {
		return li;
	}
}
