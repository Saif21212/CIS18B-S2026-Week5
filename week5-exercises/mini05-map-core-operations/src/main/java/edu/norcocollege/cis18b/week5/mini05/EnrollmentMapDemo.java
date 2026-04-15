package edu.norcocollege.cis18b.week5.mini05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnrollmentMapDemo {

    public static void main(String[] args) {
        Map<String, Integer> enrollment = new HashMap<>();

        enrollment.put("SEC-101", 30);
        enrollment.put("SEC-102", 28);
        enrollment.put("SEC-099", 12);

        // Core map lifecycle operations.
        enrollment.put("SEC-101", 32);
        enrollment.remove("SEC-099");

        // Lambda-based updates.
        enrollment.merge("SEC-101", 1, Integer::sum);
        enrollment.compute("SEC-102", (key, value) -> value == null ? 1 : value + 2);
        enrollment.replace("SEC-102", enrollment.get("SEC-102"), 28);

        enrollment.forEach((section, count) -> System.out.println(section + " -> " + count));
        System.out.println("Removed SEC-099");
        System.out.println("Updated with merge/compute operations.");

        Map<String, Integer> defaults = Map.of("retryLimit", 3, "waitlistCap", 10);
        System.out.println("Defaults: " + defaults);

        Map<String, List<String>> departments = new HashMap<>();
        departments.computeIfAbsent("CIS", key -> new ArrayList<>()).add("SEC-101");
        departments.computeIfAbsent("CIS", key -> new ArrayList<>()).add("SEC-102");
        System.out.println("Grouped sections: " + departments);

        // Mutable keys are hazardous in hash-based maps because a HashMap stores
        // each key at a bucket determined by its hashCode() at insertion time.
        // If a mutable key object is modified after insertion, its hashCode changes,
        // so the map can no longer find it at the original bucket — the entry becomes
        // permanently unreachable even though it still exists in the map.
        // Always use immutable objects (like String or Integer) as map keys.
        System.out.println("Note: mutable keys break HashMap lookup after mutation.");
    }
}
