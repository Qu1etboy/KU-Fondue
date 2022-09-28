package ku.cs.datastructure;

import java.util.ArrayList;
import java.util.List;

public class ListMap<K, V> {
    private List<Pair<K, V>> list;

    public ListMap() {
        list = new ArrayList<>();
    }

    public int size() {
        return list.size();
    }

    private Pair<K, V> find(K key) {
        for (Pair<K, V> pair : list) {
            if (pair.isKey(key)) {
                return pair;
            }
        }
        return null;
    }

    public void put(K key, V value) {
        Pair<K, V> pair = find(key);
        if (pair == null) {
            list.add(new Pair<>(key, value));
        } else {
            pair.setValue(value);
        }
    }

    public V get(K key) {
        Pair<K, V> pair = find(key);
        if (pair == null) return null;
        return pair.getValue();
    }

    public boolean contains(K key) {
        Pair<K, V> pair = find(key);
        return pair != null;
    }

    public boolean remove(K key) {
        Pair<K, V> pair = find(key);
        if (pair == null) return false;
        return list.remove(pair);
    }

    public void clear(){
        list.clear();
    }

    public List<K> keyList() {
        List<K> keyList = new ArrayList<>();
        for (Pair<K, V> pair: list) {
            keyList.add(pair.getKey());
        }
        return keyList;
    }
}
