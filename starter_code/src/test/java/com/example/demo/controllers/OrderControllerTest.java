package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class OrderControllerTest {

    private OrderController orderController;
    private OrderRepository orderRepo = mock(OrderRepository.class);
    private UserRepository userRepo = mock(UserRepository.class);

    @Before
    public void setUp(){
        orderController = new OrderController();
        TestUtils.injectObjects(orderController,"userRepository",userRepo);
        TestUtils.injectObjects(orderController,"orderRepository",orderRepo);
    }


    @Test
    public void submit_order_happy_path() throws Exception{

        User user = new User();
        user.setUsername("test");
        user.setPassword("testPassword");

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setTotal(BigDecimal.valueOf(3));

        List<Item> items = new ArrayList<Item>();
        Item orange = new Item(1L,"Orange", new BigDecimal(2), "a round orange thing, juicy and sweet");
        items.add(orange);
        Item banana = new Item(1L,"Banana", new BigDecimal(1), "a long yellow thing, soft and sweet");
        items.add(banana);
        cart.setItems(items);

        given(userRepo.findByUsername("test"))
                .willReturn(new User(1L,"test", "testPassword", cart));

        ResponseEntity<UserOrder> response = orderController.submit("test");

        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void submit_order_unhappy_path() throws Exception{

        given(userRepo.findByUsername("test"))
                .willReturn(null);

        ResponseEntity<UserOrder> response = orderController.submit("test");

        assertNotNull(response);
        assertEquals(404,response.getStatusCodeValue());
    }

    @Test
    public void orderhistory_user_unhappy_path(){

        given(userRepo.findByUsername("test"))
                .willReturn(null);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("test");

        assertNotNull(response);
        assertEquals(404,response.getStatusCodeValue());

    }

    @Test
    public void orderhistory_user_happy_path(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("testPassword");

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setTotal(BigDecimal.valueOf(3));

        List<Item> items = new ArrayList<Item>();
        Item orange = new Item(1L,"Orange", new BigDecimal(2), "a round orange thing, juicy and sweet");
        items.add(orange);
        Item banana = new Item(1L,"Banana", new BigDecimal(1), "a long yellow thing, soft and sweet");
        items.add(banana);
        cart.setItems(items);
        UserOrder userOrder = UserOrder.createFromCart(cart);
        List<UserOrder> userOrderList = Arrays.asList(userOrder);

        given(userRepo.findByUsername("test"))
                .willReturn(new User(1L,"test", "testPassword", cart));

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("test");

        given(orderRepo.findByUser(user))
                .willReturn(userOrderList);

        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());

    }

}
