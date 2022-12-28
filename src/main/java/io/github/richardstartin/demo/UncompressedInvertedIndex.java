package io.github.richardstartin.demo;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import static io.github.richardstartin.demo.HouseTransactions.numRows;
import static io.github.richardstartin.demo.Printing.*;

public class UncompressedInvertedIndex {

    public static void main(String... args) {
        int numRows = numRows();
        {
            startSection("bitset - county");
            var index = buildIndex("county");
            printTop10(index, BitSet::cardinality);
            printFootprint(index, numRows);
        }
        {
            startSection("bitset - town");
            var index = buildIndex("town");
            printTop10(index, BitSet::cardinality);
            printFootprint(index, numRows);
        }
        {
            startSection("bitset - postcode");
            var index = buildIndex("postcode");
            printTop10(index, BitSet::cardinality);
            printFootprint(index, numRows);
        }
    }

    private static Map<String, BitSet> buildIndex(String attribute) {
        Map<String, BitSet> index = new HashMap<>();
        int rowIndex = 0;
        var it = HouseTransactions.stream(attribute).iterator();
        while (it.hasNext()) {
            String attr = it.next();
            index.computeIfAbsent(attr, k -> new BitSet()).set(rowIndex++);
        }
        return index;
    }
}
