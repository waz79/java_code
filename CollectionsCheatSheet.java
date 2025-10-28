package com.karat.cheatsheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

// https://docs.oracle.com/en/java/javase/24/docs/api/index.html

public class CollectionsCheatSheet {
    public static void main(String[] args) {
        // ✅ Arrays — fixed-size, primitive-friendly
        int[] arr = {3, 1, 2};
        Arrays.sort(arr); // in-place sort
        System.out.println("Sorted array: " + Arrays.toString(arr));

        // ✅ Lists — dynamic, ordered, indexable
        List<String> list = new ArrayList<>(List.of("apple", "banana", "cherry"));
        list.add("date");
        list.remove("banana");
        list.set(0, "apricot");
        System.out.println("List: " + list);

        // ✅ Immutable Lists — safe for constants, not modifiable
        List<String> fixedList = List.of("one", "two", "three"); // ❌ throws on add/remove/sort

        // ✅ Convert immutable list to mutable
        List<String> mutableList = new ArrayList<>(fixedList); // ✅ safe to modify
        mutableList.add("four");
        mutableList.sort(Comparator.naturalOrder());
        System.out.println("Mutable copy: " + mutableList);

        // ✅ Sets — unordered, unique elements
        Set<String> set = new HashSet<>(list); // deduplicates
        set.add("apple");                      // no effect if already present
        System.out.println("Set: " + set);

        // ✅ LinkedHashSet — preserves insertion order
        Set<String> orderedSet = new LinkedHashSet<>(List.of("a", "b", "a", "c"));
        System.out.println("Ordered deduped set: " + orderedSet);

        // ✅ Maps — key-value store, great for frequency counts
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 2); // insert
        map.put("banana", map.getOrDefault("banana", 0) + 1); // increment safely
        map.computeIfAbsent("cherry", k -> 5); // lazy insert
        map.merge("apple", 1, Integer::sum); // atomic update
        System.out.println("Map: " + map);

        // ✅ LinkedHashMap — preserves insertion order
        Map<String, Integer> orderedMap = new LinkedHashMap<>();
        orderedMap.put("x", 1);
        orderedMap.put("y", 2);
        System.out.println("Ordered map: " + orderedMap);

        // ✅ Iteration — classic loop patterns
        for (String item : list) System.out.println("Item: " + item);
        for (Map.Entry<String, Integer> entry : map.entrySet())
            System.out.println(entry.getKey() + " → " + entry.getValue());

        // ✅ Safe removal while iterating
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().startsWith("a")) it.remove(); // ✅ avoids ConcurrentModificationException
        }

        // ✅ Index-safe removal — go backwards
        for (int i = mutableList.size() - 1; i >= 0; i--) {
            if (mutableList.get(i).equals("two")) mutableList.remove(i);
        }

        // ✅ Grouping by key (manual)
        Map<String, List<String>> grouped = new HashMap<>();
        for (String word : List.of("a", "b", "a", "c")) {
            grouped.computeIfAbsent(word, k -> new ArrayList<>()).add(word);
        }
        System.out.println("Grouped map: " + grouped);

        // ✅ Sorting Lists — natural and reverse
        list.sort(Comparator.naturalOrder());  // ascending (lexicographic)
        list.sort(Comparator.reverseOrder());  // descending

        // ✅ Sorting Map Entries — by value descending, then key ascending
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(map.entrySet());
        sortedEntries.sort(
            Comparator.comparing(Map.Entry<String, Integer>::getValue, Comparator.reverseOrder())
                      .thenComparing(Map.Entry::getKey)
        );
        System.out.println("Sorted map entries:");
        sortedEntries.forEach(e -> System.out.println(e.getKey() + " → " + e.getValue()));

        // ✅ Top-K with Stream — clean, declarative
        List<String> topK = map.entrySet().stream()
            .sorted(Comparator.comparing(Map.Entry<String, Integer>::getValue).reversed())
            .limit(2)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
        System.out.println("Top-K: " + topK);

        // ✅ Null-safe sort — avoids NullPointerException
        List<String> names = Arrays.asList("Alice", null, "Bob");
        names.sort(Comparator.nullsLast(Comparator.naturalOrder())); // nulls pushed to end
        System.out.println("Null-safe: " + names);

        // ✅ PriorityQueue for Top-K — efficient for streaming or large data
        Map<String, Integer> freqMap = Map.of("apple", 3, "banana", 2, "cherry", 5);
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
            Comparator.comparing(Map.Entry<String, Integer>::getValue, Comparator.reverseOrder())
                      .thenComparing(Map.Entry::getKey)
        );
        pq.addAll(freqMap.entrySet());

        List<String> topJ = new ArrayList<>();
        int k = 2;
        for (int i = 0; i < k && !pq.isEmpty(); i++) {
            topJ.add(pq.poll().getKey()); // extract top-K keys
        }
        System.out.println("Top " + k + " (priority queue): " + topJ);



        // ✅ Lambda-based Comparator — custom object, multi-field sort
        class LogEvent {
            String type;
            int timestamp;
            LogEvent(String type, int timestamp) {
                this.type = type;
                this.timestamp = timestamp;
            }
            public String toString() {
                return String.format("LogEvent(type=%s, time=%d)", type, timestamp);
            }
        }

        List<LogEvent> eventsList = Arrays.asList(
            new LogEvent("ERROR", 1002),
            new LogEvent("INFO", 1001),
            new LogEvent("ERROR", 1005),
            new LogEvent("WARN", 1000)
        );

        // Sort by type ascending, then timestamp descending
        eventsList.sort(
            Comparator.comparing((LogEvent e) -> e.type)
                      .thenComparing((LogEvent e) -> e.timestamp, Comparator.reverseOrder())
        );

        System.out.println("Sorted LogEvents (lambda comparator):");
        eventsList.forEach(System.out::println);

        // if wanting to return hard coded list whilst coding a method 
        // return (List.of(1,2,3));
    }
}
