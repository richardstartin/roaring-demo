package io.github.richardstartin.demo;

import org.openjdk.jol.info.GraphLayout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.function.ToIntFunction;

public class Printing {

    static void startSection(String title) {
        System.out.println();
        System.out.println("-".repeat(20) + " " + title + " " + "-".repeat(20));
        System.out.println();
    }

    static void printFootprint(Map<String, ?> index, int numRows) {
        var layout = GraphLayout.parseInstance(new ArrayList<>(index.values()));
        System.out.println();
        System.out.println(layout.toFootprint());
        System.out.println(String.format("size: %.2fMiB", (layout.totalSize() / 1024D / 1024D)));
        System.out.println(String.format("bits per value: %.2f", 8d * layout.totalSize() / numRows));
        System.out.println();
    }

    static <T> void printTop10(Map<String, T> index, ToIntFunction<T> cardinality) {
        System.out.println("top 10:");
        index.entrySet().stream()
                .sorted(Comparator.comparing(kv -> -cardinality.applyAsInt(kv.getValue())))
                .limit(10)
                .forEach(kv -> System.out.println(kv.getKey() + ": " + cardinality.applyAsInt(kv.getValue())));
    }
}
