package hello.myOwnTest.java8Factory;

public class Main {
    public static void main(String[] args) {
        Product phone = ElectronicsFactory.getProduct("phone");
        phone.work();

        Product computer = ElectronicsFactory.getProduct("computer");
        computer.work();

        Product camera = ElectronicsFactory.getProduct("camera");
        camera.work();
    }
}
