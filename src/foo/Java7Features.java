package foo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Java7Features {

    public static void main(String args[]) throws FileNotFoundException, IOException {
        stringInSwitch();
        binaryIntegralLiterals();
        underscoresNumericLiterals();
        diamondSyntax();
        multiCatch();
        tryWithResourceStatement();
        javaRethrowException();
    }
    
    public static void stringInSwitch() {
        String param = "Java 6";
        switch (param) {
        case "Java 5":
            System.out.println("Inside Java 5"); break;
        case "Java 6":
            System.out.println("Inside Java 6"); break;
        case "Java 7":
            System.out.println("Inside Java 7"); break;
        }
    }

    public static void binaryIntegralLiterals() {
        int binary = 0b1000;
        if (binary == 8) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }
    
    public static void underscoresNumericLiterals() {
        int oneMillon_ = 1_000_000;
        int oneMillon = 1000000;
        
        if(oneMillon == oneMillon_) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }
    
    /**
     * You can replace the type arguments required to invoke the constructor 
     * of a generic class with an empty set of type parameters (<>)
     * as long as the compiler can infer the type arguments from the context.
     * This pair of angle brackets is informally called the diamond.
     */
    public static void diamondSyntax() {
       List<String> stringListWithDiamond = new ArrayList<>(); // java 7
       List<String> stringList = new ArrayList<String>(); // before java 7
       stringListWithDiamond.add("a");
       stringList.add("a");
       stringListWithDiamond.add("b");
       stringList.add("b");
       stringListWithDiamond.add("c");
       stringList.add("c");
       System.out.println(stringList + " >> " + stringListWithDiamond);
    }
    
    /**
     * The catch clause specifies the types of exceptions that the block can handle,
     * and each exception type is separated with a vertical bar (|).
     *
     * Note: If a catch block handles more than one exception type,
     * then the catch parameter is implicitly final. In this example,
     * the catch parameter ex is final and therefore you cannot assign any values to it within the catch block.
     * 
     * can’t write child and parent exception in same catch with pipe, it will give compilation error.
     * e.g.catch (IOException | Exception e){}
     * 
     */
    public static void multiCatch() {
        try {
            throw new FileNotFoundException("FileNotFoundException");
        } catch (FileNotFoundException | ArithmeticException ex) {
            //ex = null; ex is final hence value cannot be assigned.
            ex.printStackTrace();
        }
    }
    /**
     * The try-with-resources statement is a try statement that declares one or more resources. 
     * A resource is as an object that must be closed after the program is finished with it. 
     * The try-with-resources statement ensures that each resource is closed at the end of the statement. 
     * Any object that implements java.lang.AutoCloseable, which includes all objects which implement java.io.Closeable,
     * can be used as a resource.
     * 
     * In this example, the resource declared in the try-with-resources statement is a BufferedReader. 
     * The declaration statement appears within parentheses immediately after the try keyword. The class BufferedReader,
     * in Java SE 7 and later, implements the interface java.lang.AutoCloseable. Because the BufferedReader instance is 
     * declared in a try-with-resource statement, it will be closed regardless of whether the try statement completes normally
     * or abruptly (as a result of the method BufferedReader.readLine throwing an IOException).
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void tryWithResourceStatement() throws FileNotFoundException, IOException {
        try(BufferedReader br = new BufferedReader(new FileReader("resources//java7.txt"))) {
            System.out.println(br.readLine());
        }
    }
    
    /**
     * As you can see that in rethrow method, catch block is catching Exception but it’s not part of throws clause.
     * Java 7 compiler analyze the complete try block to check what types of exceptions are thrown and
     * then rethrown from the catch block.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void javaRethrowException() throws FileNotFoundException, IOException {
        try {
            String s = "First";
            if (s.equals("First")) {
                throw new FileNotFoundException();
            } else {
                throw new IOException();
            }
        } catch (Exception e) {
            System.out.println("Catched and rethrow");
            throw e;
        }
    }
}

