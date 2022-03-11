package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


public class CartControllerTest {

    private CartController cartController;
    private ItemRepository itemRepo = mock(ItemRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private UserRepository userRepo = mock(UserRepository.class);

    @Before
    public void setUp(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController,"cartRepository",cartRepo);
        TestUtils.injectObjects(cartController,"userRepository",userRepo);
        TestUtils.injectObjects(cartController,"itemRepository",itemRepo);
    }


    @Test
    public void add_to_cart_happy_path() throws Exception{

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(3);

        given(userRepo.findByUsername("test"))
                .willReturn(new User(1L,"test", "testPassword", new Cart()));

        Item orange = new Item(1L,"Orange", new BigDecimal(2), "a round orange thing, juicy and sweet");
        Optional<Item> optItem = Optional.of(orange);
        given(itemRepo.findById(1L))
                .willReturn(optItem);


        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void add_to_cart_unhappy_path() throws Exception{

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(3);

        given(userRepo.findByUsername("test"))
                .willReturn(null);

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(404,response.getStatusCodeValue());

        given(userRepo.findByUsername("test"))
                .willReturn(new User(1L,"test", "testPassword", new Cart()));

        Optional<Item> optItem = Optional.empty();
        given(itemRepo.findById(1L))
                .willReturn(optItem);

        assertNotNull(response);
        assertEquals(404,response.getStatusCodeValue());
    }

    @Test
    public void remove_from_cart_happy_path(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(3);

        given(userRepo.findByUsername("test"))
                .willReturn(new User(1L,"test", "testPassword", new Cart()));

        Item orange = new Item(1L,"Orange", new BigDecimal(2), "a round orange thing, juicy and sweet");
        Optional<Item> optItem = Optional.of(orange);
        given(itemRepo.findById(1L))
                .willReturn(optItem);


        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);

        ModifyCartRequest removeFromCartRequest = new ModifyCartRequest();
        removeFromCartRequest.setUsername("test");
        removeFromCartRequest.setItemId(1L);
        removeFromCartRequest.setQuantity(1);

        ResponseEntity<Cart> removeResponse = cartController.removeFromcart(removeFromCartRequest);

        assertNotNull(removeResponse);
        assertEquals(200,removeResponse.getStatusCodeValue());
    }

    @Test
    public void remove_from_cart_unhappy_path(){
        given(userRepo.findByUsername("test"))
                .willReturn(null);

        ModifyCartRequest removeFromCartRequest = new ModifyCartRequest();
        removeFromCartRequest.setUsername("test");
        removeFromCartRequest.setItemId(1L);
        removeFromCartRequest.setQuantity(1);

        ResponseEntity<Cart> removeResponse = cartController.removeFromcart(removeFromCartRequest);
        assertNotNull(removeResponse);
        assertEquals(404,removeResponse.getStatusCodeValue());


        given(userRepo.findByUsername("test"))
                .willReturn(new User(1L,"test", "testPassword", new Cart()));
        Optional<Item> optItem = Optional.empty();
        given(itemRepo.findById(1L))
                .willReturn(optItem);

        ResponseEntity<Cart> removeResponseItemMissing = cartController.removeFromcart(removeFromCartRequest);
        assertNotNull(removeResponseItemMissing);
        assertEquals(404,removeResponseItemMissing.getStatusCodeValue());


    }

}
