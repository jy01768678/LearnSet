package com.lorin.design.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lorin on 2018/4/27.
 */
public class LruCache {

    class Node {
        String userId;
        UserAccount userAccount;
        Node previous;
        Node next;

        public Node(String userId, UserAccount userAccount) {
            this.userId = userId;
            this.userAccount = userAccount;
        }
    }

    int capacity;
    Map<String, Node> cache = new HashMap<>();

    Node head;
    Node end;

    public LruCache(int capacity) {
        this.capacity = capacity;
    }

    public UserAccount get(String userId) {
        if (cache.containsKey(userId)) {
            Node node = cache.get(userId);
            remove(node);
            setHead(node);
            return node.userAccount;
        }
        return null;
    }

    public void remove(Node node) {
        if (node.previous != null) {
            node.previous.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.previous = node.previous;
        } else {
            end = node.previous;
        }
    }

    public void setHead(Node node) {
        node.next = head;
        node.previous = null;
        if (head != null) {
            head.previous = node;
        }
        head = node;
        if (end == null) {
            end = head;
        }
    }

    public void set(String userId, UserAccount userAccount) {
        if (cache.containsKey(userId)) {
            Node old = cache.get(userId);
            old.userAccount = userAccount;
            remove(old);
            setHead(old);
        } else {
            Node newNode = new Node(userId, userAccount);
            if (cache.size() >= capacity) {
                cache.remove(end.userId); // remove LRU data from cache.
                remove(end);
                setHead(newNode);
            } else {
                setHead(newNode);
            }
            cache.put(userId, newNode);
        }
    }

    public boolean contains(String userId) {
        return cache.containsKey(userId);
    }

    /**
     * Invalidate cache for user
     */
    public void invalidate(String userId) {
        Node toBeRemoved = cache.remove(userId);
        if (toBeRemoved != null) {
            remove(toBeRemoved);
        }
    }

    public boolean isFull() {
        return cache.size() >= capacity;
    }

    public void clear() {
        head = null;
        end = null;
        cache.clear();
    }
    public void setCapacity(int newCapacity) {
        if (capacity > newCapacity) {
            clear(); // Behavior can be modified to accommodate for decrease in cache size. For now, we'll
            // just clear the cache.
        } else {
            this.capacity = newCapacity;
        }
    }
}
