package foo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
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
        // If you want to filter some value from the collection then you can use filter method.
        myList.parallelStream().filter(p -> p > 90).forEach(p -> System.out.println("Parallel " + p));
        myList.stream().filter(p -> p > 90).forEach(p -> System.out.println("Sequential " + p));
        List<Integer> arr = myList.stream().filter(p -> p > 90).collect(Collectors.toList());
        System.out.println(arr);
        
        List<Integer> costBeforeTax = Arrays.asList(1, 2, 3, 4, 5, 5);
        // map will change to each value in the collection
        costBeforeTax.stream().map((cost) -> cost + 0.12 * cost).forEach(System.out::println);
        //mapToDouble will change each value in the collection to double. 
        costBeforeTax.stream().mapToDouble((i) -> i * i).forEach(System.out::println);
        double total = costBeforeTax.stream().map((cost) -> cost).reduce((sum, cost) -> sum + cost).get();
        System.out.println(total);
        List<String> strList = Arrays.asList("a", "b", "c", "d");
        String toString = strList.stream().map((s) -> s.toUpperCase()).collect(Collectors.joining(", "));
        System.out.println(toString);
        
        List<Integer> distinctList = costBeforeTax.stream().map((i) -> i).distinct().collect(Collectors.toList());
        System.out.println(distinctList);
        
        // summaryStatistics method has method like min, max, sum, average etc. 
        IntSummaryStatistics stats = costBeforeTax.stream().mapToInt((i) -> i).summaryStatistics();
        System.out.println("Highest number in List : " + stats.getMax());
        System.out.println("Lowest number in List : " + stats.getMin());
        System.out.println("Sum of all numbers : " + stats.getSum());
        System.out.println("Average of all numbers : " + stats.getAverage());
        
        List<Integer> list = Arrays.asList(4, 1, 5, 2, 5, 5);
        // get the count of list
        System.out.println(list.stream().count());
        // return true if any element in the list is equal to 5 else false.
        System.out.println(list.stream().anyMatch(i-> i==5));
        // return true if all the element in the list is equal to 5 else false.
        System.out.println(list.stream().allMatch(i-> i==5));
        // Method reference examples
        /*
         * You use lambda expressions to create anonymous methods. Sometimes, however,
         * a lambda expression does nothing but call an existing method. In those cases,
         * it's often clearer to refer to the existing method by name. Method references
         * enable you to do this; they are compact, easy-to-read lambda expressions for
         * methods that already have a name.
         * 
         *            Kind                                                   Example
         * Reference to a static method                             ContainingClass::staticMethodName
         * Reference to an instance method of a particular object   containingObject::instanceMethodName
         * Reference to an instance method of an arbitrary          ContainingType::methodName
         * object of a particular type 
         * Reference to a constructor                               ClassName::new
         * 
         */
        Interface3 interface3 = MyClass::new;
        System.out.println(interface3.method1());
        // print the distinct no in the list.
        list.stream().distinct().forEach(System.out::println);
        // find any element which matched the filter.
        Optional<Integer> value = list.stream().filter(x -> x > 6).findAny();
        // option has method orElse if the value is not null then value is returned
        // else the value you passed will be return in this case null.
        System.out.println(value.orElse(null));
        /*
         * If you have list of list(nested list) and you wanted to process on the element of each list
         * then in that case you have to use a flatMap which return a
         * stream for each collection.
         */
        List<List<Integer>> nestedListOfInteger = Arrays.asList(list, list) ;
        nestedListOfInteger.stream().flatMap(t -> t.stream()).forEach(System.out::println);
        List<List<List<Integer>>> multipleNestedListOfInteger = Arrays.asList(nestedListOfInteger, nestedListOfInteger);
        multipleNestedListOfInteger.stream().flatMap(x -> x.stream().flatMap(y -> y.stream()))
                .forEach(System.out::println);
        double totalSum = nestedListOfInteger.stream().flatMapToDouble(t -> t.stream().mapToDouble(x -> x + 0.2)).sum();
        System.out.println(totalSum);
        
        // limit means break the list with top 3 element in the list.
        list.stream().limit(0).forEach(System.out::println);
        // max method takes comparator and return the Optional object containing max value.
        System.out.println("Max in the list" + list.stream().max((x, y) -> x.compareTo(y)).orElse(null));
        // peek is for debugging it does not change anything in the stream.
        list.stream().peek(x -> System.out.println("Inside " + x)).filter(f -> f > 2)
                .peek(x -> System.out.println("Inside Inside " + x)).forEach(System.out::println);
        //reduce method will do reduction on the elements of this stream, using an accumulation function, 
        // and returns an {@code Optional} describing the reduced value, if any.
        System.out.println(list.stream().reduce((x, y) -> x + y).get());
        /*
         * Returns a stream consisting of the remaining elements of this stream
         * after discarding the first {@code n} elements of the stream.
         * If this stream contains fewer than {@code n} elements then an
         * empty stream will be returned.
         */
        list.stream().skip(3).forEach(System.out::println);
        /*
         * Returns a stream consisting of the elements of this stream, sorted
         * according to natural order.  If the elements of this stream are not
         * {@code Comparable}, a {@code java.lang.ClassCastException} may be thrown
         * when the terminal operation is executed.
         */
        list.stream().sorted().forEach(System.out::println);
        
        list.stream().sorted((x,y)->x.compareTo(y)).forEach(System.out::print);
        
        // Iterating over Map using java 8
        Map<Character, Integer> map = new HashMap<>();
        map.put('a', 1);
        map.put('b', 2);
        map.put('c', 1);
        // finding first matching key from the map when iterating over keyset.
        System.out.println(map.keySet().stream().filter(k -> map.get(k) == 1).findFirst().get());
        // finding first matching key from the map when iterating over entry set.
        System.out.println(map.entrySet().stream().filter(e -> e.getValue() == 1).map( i -> i.getKey()).findFirst().get());
        // Printing key value in custom format using stream.
        map.entrySet().forEach(i-> System.out.println("Key=" + i.getKey() + " Value=" + i.getValue()));
        // Iterating over Set using java 8
        Set<String> set = new HashSet<String>();
        set.add("t");
        set.add("y");
        set.add("z");
        System.out.println(set.stream().filter(i->i == "x").collect(Collectors.toList()));
        System.out.println(set.stream().filter(i->i == "x").findFirst().orElse(null));
    }
}
