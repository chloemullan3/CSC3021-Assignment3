package A3Database;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;

class LockSet {
    List<Lock> locks;

    LockSet() {
	locks = new LinkedList<Lock>();
    }

    public void add( Lock lock ) { locks.add( lock ); }

    public void release() {
	for( Lock lock : locks ) {
	    lock.unlock();
	}
    }

    public void joinLists(LockSet lockSet){
        locks.addAll(lockSet.locks);
    }
};
