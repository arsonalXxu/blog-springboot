package hello.strategy;

public interface ValidationStrategy {
    boolean execute(String s);
    default void test() {};
}
