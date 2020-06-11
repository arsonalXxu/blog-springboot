package hello.strategy;

public class IsStartWithC implements ValidationStrategy {
    @Override
    public boolean execute(String s) {
        return 'c' == s.toLowerCase().charAt(0);
    }
}
