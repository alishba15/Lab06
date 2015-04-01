
//Author : Alishba Idris
//Reg No : 01044
//Date : 01/04/2015.

//Local Spider

package lab6;

import java.util.*;

public class WorkQueue {

private LinkedList<String> workQ;
private boolean done; 
private int size; 
public WorkQueue() {
workQ = new LinkedList<String>();
done = false;
size = 0;
}

public synchronized void add(String s) {
workQ.add(s);
size++;
notifyAll();
}

public synchronized String remove() {
String s;
while (!done && size == 0) {
 try {
  wait();
 } catch (Exception e) {};
}
if (size > 0) {
 s = workQ.remove();
 size--;
 notifyAll();
} else
 s = null;
return s;
}

public synchronized void finish() {
done = true;
notifyAll();
}
}
