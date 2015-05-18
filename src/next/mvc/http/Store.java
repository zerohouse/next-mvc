package next.mvc.http;

import java.util.HashMap;
import java.util.Map;

public class Store {
	
	private Map<Object, Object> store;
	
	public Store() {
		store = new HashMap<Object, Object>();
	}
	
	public void put(Object obj){
		store.put(obj.getClass(), obj);
	}
	public void put(Object key, Object obj){
		store.put(key, obj);
	}
	
	public Object get(Object obj){
		return store.get(store);
	}
	
	

}
