package nl.timmybankers.guava;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;

import java.util.ArrayList;
import java.util.Collection;

public class FilterNulls {

    public static <T> Collection<T> filterNullsGuava(Collection<T> input) {
        return Collections2.filter(input, new Predicate<T>() {
            @Override
            public boolean apply(T input) {
                return input != null;
            }
        });
    }

    public static <T> Collection<T> filterNullsPlain(Collection<T> input) {
        ArrayList<T> list = new ArrayList<>();
        for (T i : input) {
            if(i != null) {
                list.add(i);
            }
        }
        return list;
    }
}
