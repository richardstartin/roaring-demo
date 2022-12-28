package io.github.richardstartin.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.github.richardstartin.demo.HouseTransactions.numRows;
import static io.github.richardstartin.demo.Printing.*;

public class IntsInvertedIndex {

    public static void main(String... args) {
        int numRows = numRows();
        {
            startSection("ints - county");
            var index = buildIndex("county");
            printTop10(index, Ints::cardinality);
            printFootprint(index, numRows);
        }
        {
            startSection("ints - town");
            var index = buildIndex("town");
            printTop10(index, Ints::cardinality);
            printFootprint(index, numRows);
        }
        {
            startSection("ints - postcode");
            var index = buildIndex("postcode");
            printTop10(index, Ints::cardinality);
            printFootprint(index, numRows);
        }
    }

    private static Map<String, Ints> buildIndex(String attribute) {
        Map<String, Ints> index = new HashMap<>();
        int rowIndex = 0;
        var it = HouseTransactions.stream(attribute).iterator();
        while (it.hasNext()) {
            String attr = it.next();
            index.computeIfAbsent(attr, k -> new Ints()).add(rowIndex++);
        }
        for (Ints ints : index.values()) {
            ints.trim();
        }
        return index;
    }

    private static class Ints {
        private int[] ints = new int[1];
        int size = 0;

        public void add(int i) {
            if (size >= ints.length) {
                ints = Arrays.copyOf(ints, 2 * ints.length);
            }
            ints[size++] = i;
        }

        public int cardinality() {
            return size;
        }

        public void trim() {
            if (size < ints.length) {
                ints = Arrays.copyOf(ints, size);
            }
        }
    }
}
