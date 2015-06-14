package nl.timmybankers.guava;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
            if (i != null) {
                list.add(i);
            }
        }
        return list;
    }

    public static <K> Map<K, String> filterBlanksInMapGuava(Map<K, String> input) {
        return Maps.filterValues(input, new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return StringUtils.isNotBlank(input);
            }
        });
    }

    public static <K> Map<K, String> filterBlanksInMapPlain(Map<K, String> input) {
        final Map<K, String> result = new HashMap<K, String>();

        for (Map.Entry<K, String> entry : input.entrySet()) {
            final String value = entry.getValue();
            if (StringUtils.isNotBlank(value)) {
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }

}
