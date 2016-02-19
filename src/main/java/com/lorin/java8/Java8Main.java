package com.lorin.java8;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by taodong on 16/1/30.
 */
public class Java8Main {
    public static void main(String[] args) {
        //接口的默认方法---start
        ExtensionMethods em = new ExtensionMethods() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };
        em.calculate(100);
        em.sqrt(16);
        //接口的默认方法---end
        //Lambda表达式---start
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names,(String a,String b) ->{return b.compareTo(a);});
        Collections.sort(names,(String a,String b) -> b.compareTo(a));
        Collections.sort(names,(a,b) -> b.compareTo(a));
        //Lambda表达式---end
        //函数式接口---start
        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted);
        //函数式接口---end
        //方法和构造器引用---start
        Converter<String,Integer> converter1 = Integer::new;
        //方法和构造器引用---end
        //Clock---start
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);
        //Clock---end
        //Timezones---start
        System.out.println(ZoneId.getAvailableZoneIds());
        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());
        //Timezones---end

    }

}
