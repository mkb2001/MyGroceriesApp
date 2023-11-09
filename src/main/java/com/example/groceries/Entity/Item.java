package com.example.groceries.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long itemId;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @Column(nullable = false)
    private String name;

    private double price;

    private double quantity;

    private String unit;
}
