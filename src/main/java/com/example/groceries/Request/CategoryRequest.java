package com.example.groceries.Request;

import java.util.List;

import com.example.groceries.Entity.Item;

public record CategoryRequest(
    String name,
    List<Item> items
) {
    
}
