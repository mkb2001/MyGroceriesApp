package com.example.groceries.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.groceries.Entity.Item;
import com.example.groceries.Request.ItemRequest;
import com.example.groceries.Service.ItemService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api/v1/category/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping(path = "/add")
    public ResponseEntity<?> addItem(@RequestBody Item item){
        try {
            Item savedItem = itemService.addItem(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Item> getItems(){
        return itemService.getItems();
    }

    @PutMapping(path="/{itemId}")
    public ResponseEntity<String> updateItem(@PathVariable("itemId") @NonNull UUID itemId,
        @RequestBody ItemRequest item){
            try {
                String updatedItem = itemService.updateItem(itemId, item);
                return ResponseEntity.status(HttpStatus.CREATED).body(updatedItem);
            } catch (IllegalStateException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }

    @DeleteMapping(path = "/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable("itemId") @NonNull UUID itemId){
        try {
            String savedItem = itemService.removeItem(itemId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
