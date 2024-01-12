package com.blog.items.item45;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class Anagrams {
    public void withMap(String filePath, String minGroupSizeStr) throws FileNotFoundException {
        File dictionary = new File(filePath);
        int minGroupSize = Integer.parseInt(minGroupSizeStr);

        Map<String, Set<String>> groups = new HashMap<>();
        try(Scanner s = new Scanner(dictionary)) {
            while(s.hasNext()) {
                String word = s.next();
                groups.computeIfAbsent(alphabetize(word), (unused) -> new TreeSet<>()).add(word);
            }
        }

        for (Set<String> group : groups.values()) {
            if(group.size() >= minGroupSize)
                System.out.println(group.size() + ": " + group);
        }
    }

    public void withOverStream(String filePath, String minGroupSizeStr) throws IOException {
        Path dictionary = Paths.get(filePath);
        int minGroupSize = Integer.parseInt(minGroupSizeStr);

        try(Stream<String> words = Files.lines(dictionary)) {
            words.collect(
                    groupingBy(word -> word.chars().sorted()
                            .collect(StringBuilder::new, (sb, c) -> sb.append((char) c), StringBuilder::append).toString()))
                    .values().stream()
                    .filter(group -> group.size() >= minGroupSize)
                    .map(group -> group.size() + ": " + group)
                    .forEach(System.out::println);
        }

    }

    public void withSuitStream(String filePath, String minGroupSizeStr) throws IOException {
        Path dictionary = Paths.get(filePath);
        int minGroupSize = Integer.parseInt(minGroupSizeStr);

        try (Stream<String> words = Files.lines(dictionary)) {
            words.collect(groupingBy(this::alphabetize))
                    .values().stream()
                    .filter(group -> group.size() >= minGroupSize)
                    .forEach(group -> System.out.println(group.size() + ": " + group));
        }
    }



    private String alphabetize(String word) {
        char[] a = word.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}
