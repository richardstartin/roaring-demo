package io.github.richardstartin.demo;

public class ExploreBinaryNumbers {

    public static void main(String... args) {
        for (int i = 0, j = 0; i < 500_000; i += 1000, j++) {
            System.out.println(j + ": " + paddedBinaryStringSplitHighLow(i));
        }
    }
    
    public static String paddedBinaryStringSplitHighLow(int value) {
        int high = value >>> 16;
        int low = value & 0xFFFF;
        return "0".repeat(Integer.numberOfLeadingZeros(high | 1) - 16) + Integer.toBinaryString(high)
                + " " + "0".repeat(Integer.numberOfLeadingZeros(low | 1) - 16) + Integer.toBinaryString(low);
    }
}
