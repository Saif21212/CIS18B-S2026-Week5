package edu.norcocollege.cis18b.week5.mini02;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class AgendaEditor {

    public static void main(String[] args) {
        List<String> agenda = new ArrayList<>(List.of("Intro", "Demo", "Optional", "Quiz"));

        applyEdits(agenda);
        System.out.println("Forward: " + agenda);

        printReverse(agenda);
    }

    static void applyEdits(List<String> agenda) {
        ListIterator<String> iterator = agenda.listIterator();

        while (iterator.hasNext()) {
            String step = iterator.next();

            if ("Demo".equals(step)) {
                iterator.set("Live Demo");
                iterator.add("Reflection");
            }

            if ("Optional".equals(step)) {
                iterator.remove();
            }
        }
    }

    static void printReverse(List<String> agenda) {
        ListIterator<String> reverse = agenda.listIterator(agenda.size());
        List<String> reversed = new ArrayList<>();

        while (reverse.hasPrevious()) {
            reversed.add(reverse.previous());
        }

        System.out.println("Reverse: " + reversed);
    }
}
