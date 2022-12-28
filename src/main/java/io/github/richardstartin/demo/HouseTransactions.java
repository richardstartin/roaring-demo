package io.github.richardstartin.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

public class HouseTransactions {

    private static final Map<String, Integer> INDEXES = Map.of("town", 11,
            "postcode", 3,
            "county", 13
            );

    public static void main(String... args) {
        stream(args[0]).forEach(System.err::println);
    }

    public static int numRows() {
        try {
            // downloaded from http://prod1.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-2022.csv
            return Files.readAllLines(Paths.get(System.getProperty("user.dir"))
                    .resolve("src/data/2022-house-prices.csv"))
                    .size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stream<String> stream(String attribute) {
        int index = INDEXES.get(attribute);
        try {
            // downloaded from http://prod1.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-2022.csv
            return Files.readAllLines(Paths.get(System.getProperty("user.dir"))
                            .resolve("src/data/2022-house-prices.csv"))
                    .stream()
                    .map(line -> getAttribute(line, index));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getAttribute(String line, int index) {
        int pos = -1;
        for (int i = 0; i < index; i++) {
            pos = line.indexOf(",", pos + 1);
        }
        int end = line.indexOf(",", pos + 1);
        return line.substring(pos + 2, end - 1);
    }
}
