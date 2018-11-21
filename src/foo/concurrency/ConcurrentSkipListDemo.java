package foo.concurrency;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentSkipListDemo {

	public static void main(String[] args) {
		Set<String> list = new ConcurrentSkipListSet<>();
		list.add("d");
		list.add("e");
		list.add("f");
		list.add("a");
		list.add("b");
		list.add("c");
		Iterator<String> itr = list.iterator();
		while(itr.hasNext()) {
		    System.out.println(itr.next());
		}
	
		System.out.println(list);
	}
}
