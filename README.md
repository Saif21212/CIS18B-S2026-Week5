## Compile & Run (Student)
This repo uses Maven. To build and run all mini-assignments and tests, run `mvn test` from the repository root.
You can also `cd` into a mini-assignment directory under `week5-exercises/` and run `mvn test` there.
# Week 5 Lesson: Collections, Iterators, and Data Structure Tradeoffs

# How to use this README.md

This repository contains the lesson for this week (`README.md`, the file you're reading now), as well as your assignments for this week (`week5-exercises/`).  Each exercise has its own `README.md` file along with instructions on how to run the code and tests.

Work through the lesson in order, pause at each checkpoint, and then complete the mini-assignments.  Note that the final mini-lesson (mini06) is your lab assignment for this lesson, so be sure to give yourself adequate time to complete it.

## Required External Readings

1. Java Collections Framework tutorial series:
   [https://docs.oracle.com/javase/tutorial/collections/](https://docs.oracle.com/javase/tutorial/collections/)
2. Iterator pattern overview:
   [https://refactoring.guru/design-patterns/iterator](https://refactoring.guru/design-patterns/iterator)

## Learning Objectives

By the end of this lesson, you will be able to:

* choose between `List`, `Set`, `Map`, `Queue`, and `Deque` based on program behavior
* explain the behavioral differences between common implementations such as `ArrayList`, `LinkedList`, `HashSet`, `LinkedHashSet`, `TreeSet`, and `ArrayDeque`
* use immutable collection factories and recognize when immutability is helpful
* apply map operations such as `forEach`, `replace`, `compute`, and `merge`
* use `Iterator` and `ListIterator` safely for traversal and mutation
* explain fail-fast iterator behavior and common misuse cases
* distinguish built-in Java iteration from the Iterator design pattern
* implement a custom `Iterable` type and justify when a custom iterator is worth the added complexity

---

# Part 1 - Collections Framework

Estimated time: 35 to 45 minutes

## Reading Focus

Use the Collections Framework tutorial series to review:

* interface hierarchy
* collection ordering and uniqueness rules
* maps, queues, and deques
* algorithms and implementation tradeoffs

---

## 1. Hierarchy Model

Java organizes related data structures through interfaces so your code can depend on behavior first and implementation second.

```text
Iterable
 └── Collection
      ├── List
      ├── Set
      └── Queue

Map
```

Important detail: `Map` is part of the Collections Framework, but it does not extend `Collection`.

Practical rule:

* start with the interface that expresses the behavior you need
* choose the implementation that best matches access patterns, ordering needs, and mutation cost

### Example

```java
List<String> alerts = new ArrayList<>();
Set<String> tags = new HashSet<>();
Map<String, Integer> counts = new HashMap<>();
Queue<String> jobs = new ArrayDeque<>();
```

Checkpoint:

* Why is programming to `List` usually better than programming directly to `ArrayList`?
* Why is `Map` shown beside `Collection` instead of under it?

---

## 2. List, Set, and Map Semantics

Choose the abstraction based on what the program must guarantee.

### `List`

Use a `List` when:

* position matters
* duplicates are allowed
* indexed access is useful

```java
List<String> tasks = new ArrayList<>();
tasks.add("grade quiz");
tasks.add("post feedback");
tasks.add("grade quiz");
System.out.println(tasks.get(0));
```

### `Set`

Use a `Set` when:

* uniqueness matters
* you want to reject duplicates automatically
* ordering is either irrelevant or controlled by implementation choice

```java
Set<String> sections = new LinkedHashSet<>();
sections.add("MON-1");
sections.add("TUE-2");
sections.add("MON-1");
System.out.println(sections);
```

### `Map`

Use a `Map` when:

* you need lookup by key
* each key should point to one value
* key semantics matter more than position

```java
Map<String, Integer> enrollment = new HashMap<>();
enrollment.put("CIS-18B", 34);
enrollment.put("CIS-5", 28);
System.out.println(enrollment.get("CIS-18B"));
```

Checkpoint:

* If a scenario says "store each username only once," why is `Set` a better first choice than `List`?
* If a scenario says "find the current score for a student ID," why is `Map` the correct abstraction?

---

## 3. Queue and Deque Usage

`Queue` models first-in, first-out behavior. `Deque` supports both front and back operations and can model queue or stack-like behavior.

Prefer `ArrayDeque` for most queue or stack-style workflows in modern Java.

```java
Deque<String> workItems = new ArrayDeque<>();

workItems.addLast("import roster");
workItems.addLast("send reminder");
workItems.addFirst("urgent alert");

System.out.println(workItems.removeFirst());
System.out.println(workItems.removeLast());
```

Practical caveat:

* avoid the legacy `Stack` class for new code
* use `ArrayDeque` for LIFO behavior with `push`, `pop`, and `peek`

Checkpoint:

* Which end of a `Deque` changes when you model a stack?
* Why is `ArrayDeque` usually preferred over `LinkedList` for queue and stack tasks?

---

## 4. Immutable Factory Methods

Java 9+ introduced convenient immutable collection factories.

```java
List<String> statuses = List.of("NEW", "IN_PROGRESS", "DONE");
Set<String> roles = Set.of("student", "ta", "instructor");
Map<String, Integer> defaults = Map.of("retryLimit", 3, "timeoutSeconds", 15);
```

Use these when:

* the data should not change after creation
* you want to express a fixed configuration clearly
* you want defensive simplicity in constructors or service setup

If you attempt to modify them, Java throws `UnsupportedOperationException`.

Checkpoint:

* When is `List.of(...)` a clearer choice than `new ArrayList<>()`?
* Why can immutability reduce accidental bugs in larger programs?

---

## 5. Map Lambda Operations

Modern map operations reduce boilerplate and keep update logic near the data by using Lambda functions.  Lambda functions are like regular functions, except instead of giving the function a name and declaring a return type, the function is written directly where you would use a named function.  Lambda functions can't be referenced or re-used, so make sure to use them with care, but one excellent use-case for them is when you're processing the values in a list and need to conduct more complicated operations.

```java
Map<String, Integer> ticketCounts = new HashMap<>();

ticketCounts.merge("open", 1, Integer::sum);
ticketCounts.merge("open", 1, Integer::sum);
// Note: the LAMBDA function starts at (key,value)
ticketCounts.compute("closed", (key, value) -> value == null ? 1 : value + 1);

ticketCounts.forEach((status, count) ->
    System.out.println(status + " -> " + count)
);
```

Useful patterns:

* `forEach` for reporting
* `replace` for targeted updates
* `compute` when new value depends on old value
* `merge` for counting and accumulation

Checkpoint:

* Why is `merge` often cleaner than checking `containsKey` first?
* What makes `compute` more general than `replace`?

---

## 6. Implementation Choice Tradeoffs

Interface choice tells readers what the code means. Implementation choice affects performance and ergonomics.

### `ArrayList` vs `LinkedList`

Use `ArrayList` by default when:

* you read by index often
* append-at-end is common
* you want good cache locality and predictable general performance

Use `LinkedList` only when you have a clear reason, such as specialized frequent insertions or removals at known ends and you have measured or justified the need.

Practical caveat:

* many students overestimate `LinkedList`
* in typical applications, `ArrayList` is the better default

### Ordered Set Choices

* `HashSet`: fastest general-purpose uniqueness, no stable iteration order
* `LinkedHashSet`: uniqueness plus insertion order
* `TreeSet`: uniqueness plus sorted order, requires comparable elements or a comparator

Checkpoint:

* Why is `ArrayList` the default choice even though insertion in the middle can be expensive?
* When is `TreeSet` worth the extra complexity compared with `HashSet`?

---

# Part 2 - Iterators

Estimated time: 35 to 45 minutes

## Reading Focus

Use the Iterator pattern reading to understand the design pattern itself. Then connect that pattern to Java's built-in iterator APIs.

---

## 7. Built-In `Iterator` Usage Patterns

An `Iterator` gives sequential access without exposing internal representation.

```java
List<String> queue = new ArrayList<>(List.of("A", "B", "C"));
Iterator<String> iterator = queue.iterator();

while (iterator.hasNext()) {
    String item = iterator.next();
    if (item.equals("B")) {
        iterator.remove();
    }
}

System.out.println(queue);
```

This is the safe way to remove items during iteration.

Common failure mode:

```java
for (String item : queue) {
    if (item.equals("B")) {
        queue.remove(item);
    }
}
```

This often causes `ConcurrentModificationException` because the collection was structurally changed outside the iterator.

Checkpoint:

* Why is `iterator.remove()` allowed when `queue.remove(...)` inside the loop is not?
* What does fail-fast behavior help you detect?

---

## 8. `ListIterator` for Bidirectional Traversal

`ListIterator` extends `Iterator` with list-specific capabilities.

```java
List<String> agenda = new ArrayList<>(List.of("Intro", "Demo", "Quiz"));
ListIterator<String> iterator = agenda.listIterator();

while (iterator.hasNext()) {
    String step = iterator.next();
    if (step.equals("Demo")) {
        iterator.set("Live Demo");
        iterator.add("Reflection");
    }
}

System.out.println(agenda);
```

Use `ListIterator` when you need:

* forward and backward traversal
* in-place replacement with `set`
* insertion relative to the current cursor with `add`

Checkpoint:

* What can `ListIterator` do that plain `Iterator` cannot?
* Why is `ListIterator` only available for lists?

---

## 9. Iterator Design Pattern

The Iterator pattern separates traversal from the aggregate object.

Intent:

* expose sequential access without exposing internal structure

Structure:

* aggregate or collection-like object
* iterator object holding traversal state
* client code using the iterator contract

Minimal conceptual example:

```java
public interface CatalogIterator<T> {
    boolean hasNext();
    T next();
}

public class CourseCatalog implements Iterable<String> {
    private final List<String> courses;

    public CourseCatalog(List<String> courses) {
        this.courses = courses;
    }

    @Override
    public Iterator<String> iterator() {
        return courses.iterator();
    }
}
```

In Java, many custom iterator designs are expressed by implementing `Iterable<T>` and returning a suitable `Iterator<T>`.

Checkpoint:

* What state does an iterator object usually need to track?
* Why is separating traversal logic from storage logic useful?

---

## 10. When Built-In Iteration Is Enough vs When Custom Iterators Are Justified

Built-in iteration is enough when:

* your object already stores data in a standard collection
* one obvious traversal order exists
* callers only need straightforward looping

Custom iterator objects are justified when:

* the traversal order is domain-specific
* you want multiple traversal strategies over the same data
* you need to hide internal representation details
* iteration rules themselves are part of the design problem

Examples:

* built-in enough: loop through a list of student names
* custom justified: traverse a playlist in release order, by priority, or by due date without exposing internal sorting details

Checkpoint:

* In your own words, what is the extra design value of a custom iterator?
* When would building a custom iterator be unnecessary overhead?

---

# Follow-Along Prompts

Pause after the reading and answer these in your notes:

1. Which collection interface would you choose for an attendance tracker, and why?
2. Which set implementation would you choose if insertion order matters?
3. When would `Map.merge` be cleaner than repeated `if` checks?
4. What causes fail-fast iterator errors?
5. What is one example of a domain object that should implement `Iterable`?

---

# Week 5 Assignment Preview

This week includes six mini-assignments.

* Minis 1 through 4 are formative and focus on API fluency and implementation choice.
* Mini 5 is bridge practice that combines map correctness with map lambda operations.
* Mini 6 is the summative capstone focused on custom traversal, iterator strategy design, and testing.

Go next to `week5-exercises/README.md` for the exercise index and submission expectations.
