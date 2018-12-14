package A3Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class RecordVisitorLock implements RecordVisitor {
    private Table table;
    RecordVisitorLock( Table table_ ) {
        table = table_;
    }
    ArrayList<Record> records = new ArrayList<>();
    
    public void visit( Record row ) {
	// The record row needs to be locked.
        records.add(row);
    }

    public ArrayList<Record> getRecords() {
        Collections.sort(records, new Cmp());
        return records;
    }

    // This comparator class can be helpful to sort records as part of the
    // deadlock prevention policy.
    class Cmp implements Comparator<Record> {
	// Return a value less than zero if lhs < rhs
	// Return zero if lhs == rhs
	// Return a value greater than zero if lhs > rhs
	public int compare( Record lhs, Record rhs ) {
        int result = 0;
        if ((lhs.get(0).toInteger()) < rhs.get(0).toInteger()) result = -1;
        if ((lhs.get(0).toInteger()) == rhs.get(0).toInteger()) result = 0;
        if ((lhs.get(0).toInteger()) > rhs.get(0).toInteger()) result = 1;
        return result;
	}
    }

}
