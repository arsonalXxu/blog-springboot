package hello.strategy;

public class IsAllUpperCase implements ValidationStrategy {
    @Override
    public boolean execute(String s) {
        return s.matches("[A-Z]+");
    }
}
