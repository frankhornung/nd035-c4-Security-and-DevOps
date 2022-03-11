package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
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


public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepo = mock(ItemRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);

    @Before
    public void setUp(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController,"itemRepository",itemRepo);
    }

    @Test
    public void find_all_items() throws Exception{

        List<Item> items = new ArrayList<Item>();
        Item orange = new Item(1L,"Orange", new BigDecimal(2), "a round orange thing, juicy and sweet");
        items.add(orange);
        Item banana = new Item(1L,"Banana", new BigDecimal(1), "a long yellow thing, soft and sweet");
        items.add(banana);

        given(itemRepo.findAll())
                .willReturn(Arrays.asList(orange, banana));

        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(items,response.getBody());
    }

    @Test
    public void find_item_by_name() throws Exception{
        List<Item> items = new ArrayList<Item>();
        Item orange = new Item(1L,"Orange", new BigDecimal(2), "a round orange thing, juicy and sweet");
        items.add(orange);
        given(itemRepo.findByName("Orange"))
                .willReturn(Arrays.asList(orange));
        ResponseEntity<List<Item>> response = itemController.getItemsByName("Orange");
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(items,response.getBody());

        ResponseEntity<List<Item>> response_unhappy = itemController.getItemsByName("Bananaaa");
        assertNotNull(response);
        assertEquals(404,response_unhappy.getStatusCodeValue());
        assertNotEquals(items,response_unhappy.getBody());
    }

    @Test
    public void get_item_by_id() throws Exception{

        ResponseEntity<Item> response_unhappy = itemController.getItemById(1L);
        assertEquals(404, response_unhappy.getStatusCodeValue());

        Item orange = new Item(1L,"Orange", new BigDecimal(2), "a round orange thing, juicy and sweet");
        Optional<Item> optItem = Optional.of(orange);
        given(itemRepo.findById(1L))
                .willReturn(optItem);
        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(orange,response.getBody());

    }

}
