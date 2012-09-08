package ir.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class ScorerDocContainer {

	PriorityQueue<ScorerDoc> queue;
	
	int max_elems;
	
	public ScorerDocContainer(int max_elems) {
		queue = new PriorityQueue<ScorerDoc>();
		this.max_elems = max_elems;
	}
	
	public void add_elem(ScorerDoc elem){
		//if (elem.compareTo(current_min) <= 0) { //greater then min elem
		queue.add(elem);
		if (queue.size() > max_elems)
			queue.poll();
			
		
	}
	
	public List<ScorerDoc> get_results(){
		//ScorerDoc[] docs = (ScorerDoc[])queue.toArray();
		List<ScorerDoc> docs_array = new ArrayList<ScorerDoc>();
		while (!queue.isEmpty())
			docs_array.add(queue.poll());
		
		Collections.reverse(docs_array);
		return docs_array;
	}
	
	public static void main(String[] args) {
		ScorerDocContainer c = new ScorerDocContainer(3);
		c.add_elem(new ScorerDoc(0,0, 1, 0, 0, 0, 0,0));
		c.add_elem(new ScorerDoc(0,0, 2, 0, 0, 0, 0,0));
		c.add_elem(new ScorerDoc(0,0, 3, 0, 0, 0, 0,0));
		c.add_elem(new ScorerDoc(0,0, 4, 0, 0, 0, 0,0));
		c.add_elem(new ScorerDoc(0,0, 5, 0, 0, 0, 0,0));
		c.add_elem(new ScorerDoc(0,0, 4, 0, 0, 0, 0,0));
		c.add_elem(new ScorerDoc(0,0, 5, 0, 0, 0, 0,0));
		c.add_elem(new ScorerDoc(0,0, 7, 0, 0, 0, 0,0));
		c.add_elem(new ScorerDoc(0,0, 2, 0, 0, 0, 0,0));
		
		System.out.println(c.get_results());

		
	}
}
