package com.mttjj.maven.dependency.plugin.demo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.common.math.BigDecimalMath;

public class Demo {
    public static void main(String[] args) {
        System.out.println("Here I'm using guava directly: " + BigDecimalMath.roundToDouble(BigDecimal.valueOf(123.456), RoundingMode.FLOOR));

        // fully qualified class name from transitive dependency: FAILURE
        String transitiveClassName = "org.checkerframework.framework.qual.TypeKind";
        System.out.println("But I have to admit that my favorite class is actually the one called: " + transitiveClassName);

        // fully qualified class name from transitive dependency embedded in a larger string: NO FAILURE
        System.out.println("I also kind of like: com.google.errorprone.annotations.FormatString");

        // fully qualified class name from dependency that is not pulled in transitively: NO FAILURE
        String nonTransitiveClassName = "org.apache.commons.lang3.StringUtils";
        System.out.println("Though I suppose there's something to be said for this class, too: " + nonTransitiveClassName);
    }
}
