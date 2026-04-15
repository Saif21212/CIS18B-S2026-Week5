package edu.norcocollege.cis18b.week5.mini01;

import java.util.List;

public class CollectionSelectionClinic {

    private static class Scenario {
        private final String name;
        private final String bestChoice;
        private final String why;
        private final String implementationHint;

        Scenario(String name, String bestChoice, String why, String implementationHint) {
            this.name = name;
            this.bestChoice = bestChoice;
            this.why = why;
            this.implementationHint = implementationHint;
        }
    }

    public static void main(String[] args) {
        List<Scenario> scenarios = List.of(
            new Scenario("Attendance log", "List", "Duplicates and arrival order matter.", "ArrayList"),
            new Scenario("Unique course codes", "Set", "Values should appear once.", "LinkedHashSet"),
            new Scenario("Student score lookup by ID", "Map", "Key-based lookup is the core operation.", "HashMap"),
            new Scenario("Help desk intake", "Queue", "First ticket in should be processed first.", "ArrayDeque"),
            new Scenario("Undo operations", "Deque", "LIFO behavior is needed for undo.", "ArrayDeque"),
            new Scenario("Sorted student grades", "Set", "Grades must be unique and sorted for ranking.", "TreeSet")
        );

        for (Scenario scenario : scenarios) {
            System.out.println("Scenario: " + scenario.name);
            System.out.println("Best choice: " + scenario.bestChoice);
            System.out.println("Why: " + scenario.why);
            System.out.println("Implementation hint: " + scenario.implementationHint);
            System.out.println();
        }
    }
}
