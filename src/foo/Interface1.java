package foo;

@FunctionalInterface
public interface Interface1 {
    void method1();
    default void log() {
        System.out.println("Printing log in interface 1");
    }
    public static void staticMethod() {
        System.out.println("Static method in Interface 1");
    }
    
}
