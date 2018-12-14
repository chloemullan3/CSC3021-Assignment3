package A3Database;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

class Record {
    private ArrayList<DataValue> data;
    private ReentrantLock lock = new ReentrantLock();

    public ReentrantLock getLock(){
    	return lock;
	}

    Record( final int estimated_length ) {
	data = new ArrayList<DataValue>( estimated_length );
    }

    public boolean add( DataValue val ) {
	data.add( val );
	return true;
    }
    public boolean add( int position, DataValue val ) {
	data.add( position, val );
	return true;
    }
    public boolean set( int position, DataValue val ) {
	data.set( position, val );
	return true;
    }
    public DataValue get( int position ) {
	return data.get( position );
    }
    public void print() {
	for( DataValue f : data ) {
	    f.print();
	    System.out.print( " " );
	}
	System.out.println();
    }

    public String toString() {
	String s = new String();
	for( DataValue val : data )
	    s = s + val.toString() + " : ";
	return s;
    }

}
