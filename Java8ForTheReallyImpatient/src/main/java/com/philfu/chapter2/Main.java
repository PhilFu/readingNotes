package com.philfu.chapter2;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.philfu.Exercise;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

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

        List<String> resultList = Lists.newArrayList();
        startWithQ.ifPresent(v -> resultList.add(v));
        startWithQ.ifPresent(resultList::add);
        Optional<Boolean> added = startWithQ.map(resultList::add);
        String resultStr = startWithQ.orElse("");
        String resultStr2 = startWithQ.orElseGet(() -> System.getProperty("user.dir"));
        String resultStr3 = startWithQ.orElseThrow(NoSuchElementException::new);

        Optional<Double> resultzx = inverse(4.0).flatMap(Main::squareRoot);
        Optional<Double> resultzx2 = Optional.of(4.0).flatMap(Main::inverse).flatMap(Main::squareRoot);

        Stream<Integer> values = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Optional<Integer> sum = values.reduce((x, y) -> x +y);
        Optional<Integer> sum2 = values.reduce(Integer::sum);
        Integer sum3 = values.reduce(0, (x, y) -> x + y);
        int resultA = words.stream().reduce(0, (total, word) -> total + word.length(), (total1, total2) -> total1 + total2);
        String[] resultB = words.stream().toArray(String[]::new);
        HashSet<String> resultC = words.stream().collect(HashSet::new, HashSet::add, HashSet::addAll);
        Set<String> resultD = words.stream().collect(Collectors.toSet());
        List<String> resultE = words.stream().collect(Collectors.toList());
        TreeSet<String> resultF = words.stream().collect(Collectors.toCollection(TreeSet<String>::new));
        HashSet<String> resultG = words.stream().collect(Collectors.toCollection(HashSet<String>::new));
        String resultH = words.stream().collect(Collectors.joining());
        String resultI = words.stream().collect(Collectors.joining(", "));
        String resultJ = words.stream().map(Object::toString).collect(Collectors.joining(", "));
        IntSummaryStatistics summaryStatistics = words.stream().collect(Collectors.summarizingInt(String::length));
        double averageWordsLength = summaryStatistics.getAverage();
        int maxLength = summaryStatistics.getMax();
        int minLength = summaryStatistics.getMin();
        words.stream().forEach(System.out::print);

        Map<Integer, Integer> resultMap = words.stream().collect(Collectors.toMap(String::length, String::length));
        Map<Integer, String> resultMap2 = words.stream().collect(Collectors.toMap(String::length, Function.identity()));

        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        Map<String, String> languageNames = locales.collect(Collectors.toMap(l -> l.getDisplayLanguage(), l -> l.getDisplayLanguage(), (existValue, newValue) -> existValue));
        Map<String, Set<String>> countryLanuageSets = locales.collect(Collectors.toMap(l -> l.getDisplayCountry(), l -> Collections.singleton(l.getDisplayLanguage()),
                (a, b) -> {
                    Set<String> r = new HashSet<>(a);
                    r.addAll(b);
                    return r;
                }));

        Map<String, List<Locale>> countryToLocales = locales.collect(Collectors.groupingBy(Locale::getCountry));
        List<Locale> swissLocale = countryToLocales.get("CH");
        Map<Boolean, List<Locale>> englishAndOther = locales.collect(Collectors.partitioningBy(l -> l.getLanguage().equals("en")));
        List<Locale> englistLocales = englishAndOther.get(true);

        Map<String, Set<Locale>> countryToLocaleSet = locales.collect(groupingBy(Locale::getCountry, toSet()));
        Map<String, Long> countryToLocaleCount = locales.collect(groupingBy(Locale::getCountry, counting()));
//        Map<String, Long> stateToCityPop = locales.collect(groupingBy(Locale::getCountry, summarizingLong(Locale::hashCode)));
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

    private static Optional<Double> inverse(Double x) {
        Optional.ofNullable(x);
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    private static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }
}
