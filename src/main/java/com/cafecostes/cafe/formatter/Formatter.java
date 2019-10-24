package com.cafecostes.cafe.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Formatter {
    ObjectMapper objectMapper;


    public Formatter() {
        objectMapper = new ObjectMapper();
    }

    public void format_jackson(Object obj){
        System.out.println("== Jackson ==");
        try {
            String mappedValue = objectMapper.writeValueAsString(obj);
            System.out.println("toJson : " + mappedValue);

            // format to POJO
            Car customer = objectMapper.readValue(mappedValue, Car.class);
            System.out.println("fromJson : " + customer);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String args[]){
        Formatter formatter = new Formatter();

        Car customer = new Car();
        customer.setName("10");
        customer.setColor("Gompang");

        List orderList = new ArrayList();
        orderList.add("order1");
        orderList.add("order2");
        orderList.add("order3");

        customer.setOrders(orderList);

        formatter.format_jackson(customer);

    }
}
