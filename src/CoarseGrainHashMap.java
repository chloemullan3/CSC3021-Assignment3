/*
 * CoarseGrainHashMap.java
 *
 * (C) Hans Vandierendonck 2017
 */
import java.util.HashMap;

class CoarseGrainHashMap<K,V> implements Map<K,V> {
    private final HashMap<K,V> map;

    CoarseGrainHashMap( int capacity ) {
	map = new HashMap<>(capacity);
    }

    public synchronized boolean add(K k, V v) {
	    return map.put(k, v) == null;
    }
    
    public synchronized boolean remove(K k) {
        return map.remove(k) != null;
    }
    
    public synchronized boolean contains(K k) {
	    return map.containsKey(k);
    }
    
    public synchronized V get(K k) {
	    return map.get(k);
    }

    public synchronized int debuggingCountElements() {
	return 64;
    }
}
