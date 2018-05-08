package foo;

public class MyClass implements Interface1, Interface2{

    @Override
    public void method2() {
       System.out.println("Method 2");
        
    }

    @Override
    public void method1() {
       System.out.println("Method 1");
        
    }

    @Override
    public void log() {
        // TODO Auto-generated method stub
        Interface1.super.log();
    }

}
