/**
 * Created by fuweiwei02 on 2017/5/30.
 */
public class HelloWorld {
    public String sayHello() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new HelloWorld().sayHello());
    }
}
