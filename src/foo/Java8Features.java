package foo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Java8Features {

    public static void main(String[] args) {
        forEach();
        defaultAndStaticMethod();
        lambdaExpression();
        streamAPIForCollection();
    }
    
    /**
     * Java 8 has introduced forEach method in java.lang.Iterable interface so that while writing code we focus on business logic only.
     * forEach method takes java.util.function.Consumer object as argument, so it helps in having our business
     * logic at a separate location that we can reuse. 
     */
    public static void forEach() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.forEach(new Consumer<Integer>() {
            public void accept(Integer i) {
                System.out.println("forEach anonymous class value " + i);
            }
        });
        list.forEach(n -> System.out.println(n));
        list.forEach(System.out::println);
    }
    
    /**
     * From Java 8, interfaces are enhanced to have method with implementation. We can use default
     * and static keyword to create interfaces with method implementation
     * 
     * We know that Java doesn’t provide multiple inheritance in Classes because it leads to Diamond Problem.
     * So how it will be handled with interfaces now, since interfaces are now similar to abstract classes.
     * The solution is that compiler will throw exception in this scenario and we will have to provide
     * implementation logic in the class implementing the interfaces.
     * 
     */
    public static void defaultAndStaticMethod() {
        MyClass myClass = new MyClass();
        myClass.method1();
        myClass.method2();
        myClass.log();
        Interface1.staticMethod();
        Interface2.staticMethod();
    }
    /**
     * Functional interfaces are new concept introduced in Java 8. An interface with exactly
     * one abstract method becomes Functional Interface. We don’t need to use @FunctionalInterface
     * annotation to mark an interface as Functional Interface. @FunctionalInterface annotation is a
     * facility to avoid accidental addition of abstract methods in the functional interfaces. You 
     * can think of it like @Override annotation and it’s best practice to use it. java.lang.Runnable
     * with single abstract method run() is a great example of functional interface.
     * 
     * 
     * 
     */
    public static void lambdaExpression() {
        Runnable r = () -> { System.out.println("Hello"); };
        Runnable r1 = () -> System.out.println("Hello");
        Thread t = new Thread(r);
        t.start();
    }
    
    /**
     * A new java.util.stream has been added in Java 8 to perform filter/map/reduce like operations with the collection.
     * Stream API will allow sequential as well as parallel execution. This is one of the best feature for me because
     * I work a lot with Collections and usually with Big Data, we need to filter out them based on some conditions.
     */
    public static void streamAPIForCollection() {
        List<Integer> myList = new ArrayList<>();
        for(int i=0; i<100; i++) myList.add(i);
        myList.parallelStream().filter(p -> p > 90).forEach(p -> System.out.println("Parallel " + p));
        myList.stream().filter(p -> p > 90).forEach(p -> System.out.println("Sequential " + p));
        List<Integer> arr = myList.stream().filter(p -> p > 90).collect(Collectors.toList());
        System.out.println(arr);

        List<Integer> costBeforeTax = Arrays.asList(1, 2, 3, 4, 5, 5);
        costBeforeTax.stream().map((cost) -> cost + 0.12 * cost).forEach(System.out::println);
        costBeforeTax.stream().mapToDouble((i) -> i * i).forEach(System.out::println);
        double total = costBeforeTax.stream().map((cost) -> cost).reduce((sum, cost) -> sum + cost).get();
        System.out.println(total);
        List<String> strList = Arrays.asList("a", "b", "c", "d");
        String toString = strList.stream().map((s) -> s.toUpperCase()).collect(Collectors.joining(", "));
        System.out.println(toString);
        
        List<Integer> distinctList = costBeforeTax.stream().map((i) -> i).distinct().collect(Collectors.toList());
        System.out.println(distinctList);
        
        IntSummaryStatistics stats = costBeforeTax.stream().mapToInt((i) -> i).summaryStatistics();
        System.out.println("Highest number in List : " + stats.getMax());
        System.out.println("Lowest number in List : " + stats.getMin());
        System.out.println("Sum of all numbers : " + stats.getSum());
        System.out.println("Average of all numbers : " + stats.getAverage());
    }
}
