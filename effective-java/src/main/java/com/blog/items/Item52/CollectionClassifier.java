package com.blog.items.Item52;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CollectionClassifier {
    public static String classify(Set<?> set) { return "집합";}
    public static String classify(List<?> list) { return "집합";}
    public static String classify(Collection<?> collection) { return "집합";}
}
