package edu.norcocollege.cis18b.week5.mini06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Playlist implements Iterable<PlaylistItem> {

    private final List<PlaylistItem> items = new ArrayList<>();

    public void add(PlaylistItem item) {
        items.add(item);
    }

    @Override
    public Iterator<PlaylistItem> iterator() {
        // Default traversal: insertion order.
        return List.copyOf(items).iterator();
    }

    public Iterator<PlaylistItem> priorityIterator() {
        // Alternate traversal: highest priority first.
        List<PlaylistItem> sorted = new ArrayList<>(items);
        sorted.sort(Comparator.comparingInt(PlaylistItem::getPriority).reversed());
        return sorted.iterator();
    }

    public Iterator<PlaylistItem> alphabeticalIterator() {
        // Third traversal strategy: alphabetical order by title.
        // Useful when a user wants to browse the playlist by song name
        // without exposing the internal list structure.
        List<PlaylistItem> sorted = new ArrayList<>(items);
        sorted.sort(Comparator.comparing(PlaylistItem::getTitle));
        return sorted.iterator();
    }
}
