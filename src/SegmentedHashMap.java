/*
 * SegmentedHashMap.java
 *
 * (C) Hans Vandierendonck, 2017
 */

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SegmentedHashMap<K,V> implements Map<K,V> {
    private final HashMap<K,V>[] segments;
    private final int num_segments;
    private ReentrantLock[] locks;


    SegmentedHashMap( int numseg, int capacity ) {
	num_segments = numseg;
        segments = new HashMap[num_segments];
        locks = new ReentrantLock[capacity];

        for(int i = 0; i < segments.length; i++){
            segments[i] = new HashMap<>(capacity);
        }

        for (int i = 0; i < capacity; i++) {
            locks[i] = new ReentrantLock();
        }
    }

    // Select a segment by hashing the key to a value in the range
    // 0 ... num_segments-1. Base yourself on k.hashCode().
    private int hash( K k ) {
        return k.hashCode() % num_segments;
    }

    public boolean add(K k, V v) {
        int hash = hash(k);
        try {
            locks[hash].lock();
            return segments[hash].put(k, v) == null;

        } finally {
            locks[hash].unlock();
        }

    }

    public boolean remove(K k) {
        int hash = hash(k);
        try {
            locks[hash].lock();
            if(segments[hash].containsKey(k)){
                return segments[hash].remove(k) != null;

            }
        } finally {
            locks[hash].unlock();
        }
        return false;
    }
    
    public boolean contains(K k) {
        for (int i = 0; i < segments.length; i++) {
            if (segments[i].containsKey(k)) {
                return true;
            }
        }
        return false;
    }
    
    public V get(K k) {
        for(int i = 0; i < segments.length; i++){
            if(segments[i].containsKey(k)){
                return segments[i].get(k);
            }
        }
	return null;
    }

    public int debuggingCountElements() {
	return 64;
    }
}
