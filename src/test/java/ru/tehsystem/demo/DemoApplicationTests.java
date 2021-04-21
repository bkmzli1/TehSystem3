package ru.tehsystem.demo;

import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void TestVoid() throws Exception {
        Assertions.assertEquals(0,0);

    }
}
