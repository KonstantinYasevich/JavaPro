package dz1;

import dz1.custom_annotations.*;
import dz1.custom_annotations.Test;
import dz1.custom_extensions.TestAssertionError;

public class Tests {
    @BeforeSuite
    public static void beforeSuite(){
        System.out.println("Выполняюсь до всех тестов");
    }

    @AfterSuite
    public static void afterSuite(){
        System.out.println("Выполняюсь после всех тестов");
    }

    @BeforeEach
    public void beforeEach(){
        System.out.println("Выполняюсь перед каждым тестом");
    }

    @AfterEach
    public void afterEach(){
        System.out.println("Выполняюсь после каждого теста");
    }

    @Test(testName = "test1", priority = 1)
    @Order(value = 1)
    public void test(){
        System.out.println("Run test1");
    }

    @Test(testName = "test2", priority = 2)
    @Order(value = 2)
    public void test2(){
        System.out.println("Run test2");
    }

    @Test()
    public void testError(){
        System.out.println("Run testError");
        throw  new AssertionError("Ошибка");
    }

    @Test()
    public void testFail(){
        System.out.println("Run testFail");
        throw new TestAssertionError("трампампам");
    }

    @Test()
    public void testFail1(){
        System.out.println("Run testFail");
        throw new TestAssertionError("трампампам");
    }

    @Test()
    public void testFail2(){
        System.out.println("Run testFail");
        throw new TestAssertionError("трампампам");
    }
}
