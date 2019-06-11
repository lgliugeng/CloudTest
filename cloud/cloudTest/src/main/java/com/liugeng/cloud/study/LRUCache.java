package com.liugeng.cloud.study;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K,V> extends LinkedHashMap<K,V> {

    private static final int MAX_CACHE_SIZE = 100;

    private int limit;

    public LRUCache(){
        super(MAX_CACHE_SIZE);
    }

    public LRUCache(int cacheSize){
        super(cacheSize,0.75f,true);
        this.limit = cacheSize;
    }

    public V save(K key,V val){
        return put(key,val);
    }

    public V getOne(K key){
        return get(key);
    }

    public boolean isExits(K key){
        return containsKey(key);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > limit;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K,V> entry :entrySet()) {
            sb.append(String.format("%s:%s ",entry.getKey(),entry.getValue()));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LRUCache<String,Integer> lruCache = new LRUCache(3);
        for (int i = 0; i < 10; i++) {
            lruCache.save("I" + i,i * i);
        }
        System.out.println("插入10个键值对后，缓存内容为：");
        System.out.println(lruCache + "\n");

        System.out.println("访问键值为I8的节点后，缓存内容为：");
        lruCache.getOne("I8");
        System.out.println(lruCache + "\n");
        System.out.println("插入键值为I1的键值对后，缓存内容：");
        lruCache.save("I1", 1);
        System.out.println(lruCache);
    }
}
