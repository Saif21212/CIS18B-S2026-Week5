package edu.norcocollege.cis18b.week5.mini04;

import java.util.ArrayDeque;
import java.util.Deque;

public class DequeWorkbench {

    public static void main(String[] args) {
        runFifoDemo();
        runLifoDemo();
    }

    private static void runFifoDemo() {
        Deque<String> helpDesk = new ArrayDeque<>();

        helpDesk.addLast("import roster");
        helpDesk.addLast("email reminder");
        helpDesk.addFirst("urgent outage");

        while (!helpDesk.isEmpty()) {
            System.out.println("FIFO processed: " + helpDesk.removeFirst());
        }
    }

    private static void runLifoDemo() {
        Deque<String> undoStack = new ArrayDeque<>();

        undoStack.push("rename file");
        undoStack.push("insert checkpoint");
        undoStack.push("delete paragraph");

        while (!undoStack.isEmpty()) {
            System.out.println("Undo popped: " + undoStack.pop());
        }
    }
}
