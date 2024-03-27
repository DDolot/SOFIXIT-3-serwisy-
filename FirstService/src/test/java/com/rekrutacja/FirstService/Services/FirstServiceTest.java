package com.rekrutacja.FirstService.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FirstServiceTest {

    private FirstService firstService;

    @BeforeEach
    public void setUp() {
        firstService = new FirstService();
    }

    @Test
    public void Test(){
//        List<String> jsons = firstService.generateJsonList(10);

//        System.out.println(jsons.get(1));

    }
}