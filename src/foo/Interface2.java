package foo;

@FunctionalInterface
public interface Interface2 {
  void method2();
  default void log() {
      System.out.println("Printing log in Interface 2");
  }
  static void staticMethod() {
      System.out.println("Static method in Interface 2");
  }
}
