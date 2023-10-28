package com.example.groceries.Request;

public record ItemRequest(
    String name,
    Double quantity,
    Double price,
    String unit  
) {
    
}
