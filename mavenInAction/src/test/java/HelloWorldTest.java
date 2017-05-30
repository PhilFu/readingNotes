import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by fuweiwei02 on 2017/5/30.
 */
public class HelloWorldTest {

    @Test
    public void testHelloWorld() {
        HelloWorld helloWorld = new HelloWorld();
        String result = helloWorld.sayHello();
        assertThat(result, is("Hello World!"));
    }
}
