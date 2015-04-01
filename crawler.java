//Author : Alishba Idris
//Reg No : 01044
//Date : 01/04/2015.

//Local Spider

package lab6;

import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
 
public class crawler {
 
  private WorkQueue workQ;
  static String s;
  static int i = 0;
 static int found = 0;
  String fileElements[]; 
  
 private class Worker implements Runnable {
	 Map<String, String> map = new HashMap<String, String>();

  private WorkQueue queue;
  
 
  public Worker(WorkQueue q) {
   queue = q;
  }
 
 
  public void run() {
	  
   String name;

  // System.out.println("Files in the respective entries are:\n");
   while ((name = queue.remove()) != null) {
    File file = new File(name);
    fileElements =  file.list();
 
 
    if (fileElements == null)
     continue;
    for (String entry : fileElements) {
     if (entry.compareTo(".") == 0)
      continue;
     if (entry.compareTo("..") == 0)
      continue;
     
     String fn = name + "\\" + entry;
     
   
   map.put(entry,fn);

 System.out.println(fn);
   }

    }
   System.out.print("\n");
   
   for (Map.Entry<String, String> i : map.entrySet()) {
       String key = i.getKey();
       
       String values = i.getValue();
         	   
       System.out.println("File name = " + key);
       System.out.println("File path = " + values);
       System.out.println();
      
    	       
  }
   
   for (Map.Entry<String, String> i : map.entrySet()) {
	  
       String key = i.getKey();
      
       String values = i.getValue();
      
        if(key.contains(s)) {
      	   	
     	  System.out.println("FILE FOUND:" + s);
     	  
    }
        
        else {
      	   	
       	  System.out.println("FILE NOT FOUND:" + s);

        }
          
   }  
  }
 
 }
 
 public crawler() {
  workQ = new WorkQueue();
 }
 
 public Worker createWorker() {
  return new Worker(workQ);
 }
 

 
 public void processDirectory(String dir) {
	 
   try{
	
   File file = new File(dir);
   if (file.isDirectory()) {
    String entries[] = file.list();
    if (entries != null)
     workQ.add(dir);
 
    for (String entry : fileElements) {
     String subdir;
     if (entry.compareTo(".") == 0)
      continue;
     if (entry.compareTo("..") == 0)
      continue;
     if (dir.endsWith("\\")) {
      subdir = dir+entry;
    
     
     }
     else {
      subdir = dir+"\\"+entry;
    
      processDirectory(subdir);
 
     }
     
    }
   }}catch(Exception e){}
 }
 
 public static void main(String Args[]) {
 
  crawler fc = new crawler();
  
  
  Scanner in = new Scanner(System.in);

  System.out.println("Enter the search string:\n ");
  s = in.nextLine();
  //System.out.println(" "+s);

  int N = 5;
  ArrayList<Thread> thread = new ArrayList<Thread>(N);
  for (int i = 0; i < N; i++) {
   Thread t = new Thread(fc.createWorker());
   thread.add(t);
   t.start();
  }

  
  String input;
  Scanner scan = new Scanner(System.in);
  System.out.print("Enter the desired url:\n");
  input = scan.nextLine();
  
  fc.processDirectory(input);
 
  fc.workQ.finish();
 
  for (int i = 0; i < N; i++){
   try {
    thread.get(i).join();
   } catch (Exception e) {};
  }
 }
}
