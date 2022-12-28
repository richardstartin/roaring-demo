package io.github.richardstartin.demo;

import org.roaringbitmap.RoaringBitmap;
import org.roaringbitmap.RoaringBitmapWriter;

import java.util.HashMap;
import java.util.Map;

import static io.github.richardstartin.demo.HouseTransactions.numRows;
import static io.github.richardstartin.demo.Printing.*;

public class RoaringBitmapInvertedIndex {

    public static void main(String... args) {
        int numRows = numRows();
        {
            startSection("roaring - county");
            var index = buildIndex("county");
            printTop10(index, RoaringBitmap::getCardinality);
            printFootprint(index, numRows);
        }
        {
            startSection("roaring - town");
            var index = buildIndex("town");
            printTop10(index, RoaringBitmap::getCardinality);
            printFootprint(index, numRows);
        }
        {
            startSection("roaring - postcode");
            var index = buildIndex("postcode");
            printTop10(index, RoaringBitmap::getCardinality);
            printFootprint(index, numRows);
        }
    }

    private static Map<String, RoaringBitmap> buildIndex(String attribute) {
        Map<String, RoaringBitmapWriter<RoaringBitmap>> index = new HashMap<>();
        int rowIndex = 0;
        var it = HouseTransactions.stream(attribute).iterator();
        while (it.hasNext()) {
            String attr = it.next();
            index.computeIfAbsent(attr, k -> RoaringBitmapWriter.writer().get()).add(rowIndex++);
        }
        Map<String, RoaringBitmap> sealed = new HashMap<>();
        for (var entry : index.entrySet()) {
            sealed.put(entry.getKey(), entry.getValue().get());
        }
        return sealed;
    }
}
