package com.philfu.chapter2;

import com.google.common.base.Stopwatch;
import com.philfu.Exercise;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by DeBen on 2016/5/29.
 */
public class Main implements Exercise{

    Stopwatch stopwatch = Stopwatch.createUnstarted();

    @Override
    @Test
    public void perform() {
        List<String> words = getWordsAsList();

        stopwatch.start();

        int count = 0;
        for (String w : words) {
            if (w.length() > 12) count++;
        }
        printResult(count);

        long count1 = words.stream().filter(w -> w.length() > 12).count();
        printResult(count1);

        long count2 = words.parallelStream().filter(w -> w.length() > 12).count();
        printResult(count2);

        Stream<String> wordStream = Stream.of(getWordsAsArray());
        Stream<String> song = Stream.of("gently", "down", "the", "stream");
        Stream<String> silence = Stream.empty();
        Stream<String> echos = Stream.generate(() -> "Echo");
        Stream<Double> randoms = Stream.generate(Math::random);
        Stream<BigInteger> integerStream = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE));
        Stream<String> wordsFromPattern = Pattern.compile("[\\P{L}]+").splitAsStream("");
        try(Stream<String> lines = Files.lines(Paths.get("/home/"))) {
            // Stream接口有一个父接口AutoCloseable。当在某个Stream上调用close方法时，底层的文件也会被关闭。为了确保关闭，最好使用 try-with-resources 语句
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stream<String> lowercaseWords = words.parallelStream().map(String::toLowerCase);
        Stream<Character> firstChars = words.parallelStream().map(s -> s.charAt(0));
        Stream<Stream<Character>> result = words.stream().map(w -> characterStream(w));
        Stream<Character> letters = words.stream().flatMap(w -> characterStream(w));
        Stream<Double> limitRandoms = Stream.generate(Math::random).limit(100);
        Stream<Double> skipRandoms = Stream.generate(Math::random).skip(10);
        Stream<Character> combined = Stream.concat(characterStream("Hello"), characterStream("World"));
        Object[] powers = Stream.iterate(1.0, p -> p * 2).peek(e -> System.out.println("Fetching " + e)).limit(20).toArray();
        Stream<String> uniqueWords = Stream.of("merrily", "merrily", "merrily", "merrily", "gently").distinct();
        Stream<String> longestFirst = words.stream().sorted(Comparator.comparing((String::length)).reversed());
        Optional<String> largest = words.stream().max(String::compareToIgnoreCase);
        if (largest.isPresent()) {
            System.out.println("Largest: " + largest.get());
        }
        Optional<String> firstStartQ = words.stream().filter(s -> s.startsWith("Q")).findFirst();
        Optional<String> startWithQ = words.parallelStream().filter(s -> s.startsWith("Q")).findAny();
        boolean hasStartQ = words.parallelStream().anyMatch(s -> s.startsWith("Q"));
    }

    private void printResult(Object object) {
        System.out.print("Count is " + object.toString() + "; and cost: ");
        System.out.println(stopwatch.elapsed(TimeUnit.NANOSECONDS));

        stopwatch.reset();
        stopwatch.start();
    }

    private static Stream<Character> characterStream(String s) {
        List<Character> result = new ArrayList<>();
        for (char c : s.toCharArray()) {
            result.add(c);
        }
        return result.stream();
    }
}
