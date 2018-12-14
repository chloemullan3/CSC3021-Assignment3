/*
 * SegmentedHashMap.java
 *
 * (C) Hans Vandierendonck, 2017
 */

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

class SegmentedHashMap<K,V> implements Map<K,V> {
    private final HashMap<K,V>[] segments;
    private final int num_segments;
    private final ReentrantLock[] locks;

    SegmentedHashMap( int numseg, int capacity ) {
        num_segments = numseg;
        segments = new HashMap[num_segments];
        locks = new ReentrantLock[num_segments];
        for(int i = 0; i < num_segments; i++) {
            segments[i] = new HashMap<K,V>(capacity);
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
        locks[hash].lock();
        try {
            V val;
            val = segments[hash].put(k,v);
            if(val==null) {
                return true;
            }
            else {
                return false;
            }
        } finally {
            locks[hash].unlock();
        }
    }

    public boolean remove(K k) {
        int hash = hash(k);
        locks[hash].lock();
        try {
            V val;
            val = segments[hash].remove(k);
            if(val==null) return false;
            else return true;
        } finally {
            locks[hash].unlock();
        }
    }

    public boolean contains(K k) {
        int hash = hash(k);
        locks[hash].lock();
        try {
            return segments[hash].containsKey(k);
        } finally {
            locks[hash].unlock();
        }
    }

    public V get(K k) {
        int hash = hash(k);
        locks[hash].lock();
        try {
            return segments[hash].get(k);
        } finally {
            locks[hash].unlock();
        }
    }

    public int debuggingCountElements() {
        int number = 0;
        for(int i = 0 ; i < num_segments;i++) {
            number+=segments[i].size();
        }
        return number;
    }
}
