package hello.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
//        Validator numericValidator = new Validator(new IsNumeric());
//        boolean b1 = numericValidator.validate("aaaa");
//        System.out.println(b1);
//        Validator lowerCaseValidator = new Validator(new IsAllLowerCase ());
//        boolean b2 = lowerCaseValidator.validate("bbbb");
//        System.out.println(b2);
//
//        // java8实现策略模式
//        Validator numeric = new Validator(s -> s.matches("\\d+"));
//        Validator lowerString = new Validator(s -> s.matches("[a-z]+"));
//
//        boolean num = numeric.validate("123");
//        boolean lowerStr = lowerString.validate("ABc");
//        System.out.println(num);
//        System.out.println(lowerStr);
//        List<Integer> list = new ArrayList<>();

        List<String> words = Arrays.asList("hello", "world");
        words.stream()
                .map(s -> s.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .forEach(System.out::println);
    }
}
