package com.aegis.transaksi.service;

import ch.qos.logback.core.joran.sanity.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.apache.coyote.http11.Constants.a;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {


    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTransaction() {
    }



    @Test
    void printListWithTeeing(){
        List<Integer> intList = List.of(1, 2, 3, 34, 2, 34, 55, 67, 90, 11, 2303, 32, 547, 10, 19, 88);

        var result = intList.stream()
                .collect(Collectors.teeing(
                        Collectors.summingInt(Integer::intValue),
                        Collectors.counting(),
                        (x, y) -> x + y
                ));

        System.out.println(result);
    }



    @Test
    void tryGenericsWithT(){
        Function<String, String> testFunction = String::toUpperCase;
        String test = testFunction.apply("Hello World");
        System.out.println(test);
    }


    @Test
    void testListAndForEach(){
        List<Integer> list = List.of(1, 3, 43, 55, 3, 5, 40, 40, 495, 595, 4585, 33);

        list.stream().forEach(System.out::println);
        list.stream().filter( a -> a < 1000).forEach(System.out::println);

        List<Integer> getListUsingCollectors = list.stream().filter(a -> a < 1000).toList();
    }


    @Test
    void testPredicate(){
//        Predicate<String> predicate = a -> a.isEmpty();
//        Boolean checkIfThisIsTrue =  predicate.test("String is not empty");
//        System.out.println(checkIfThisIsTrue);

        Predicate<String> p = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                System.out.println(s);
                if(s.isEmpty()){
                    return false;
                }
                return false;
            }
        };
        boolean justPrint = p.test("Bang Hoka");
        System.out.println(justPrint);
    }
}