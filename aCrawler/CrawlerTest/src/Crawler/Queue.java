package Crawler;

import java.util.LinkedList;

public class Queue {

	private LinkedList<String> queue = new LinkedList<String>();

	public void enQueue(String t) {
		queue.addLast(t);
	}

	public String deQueue() {
		return queue.removeFirst().toString();
	}

	public boolean isQueueEmpty() {
		return queue.isEmpty();
	}

	public boolean contians(String t) {
		return queue.contains(t);
	}

	public boolean empty() {
		return queue.isEmpty();
	}
	
	public String QueueFirst() {
		return queue.getFirst().toString();
	}

}