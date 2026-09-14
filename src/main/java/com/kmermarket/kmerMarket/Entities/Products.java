package com.kmermarket.kmerMarket.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "products")
@Data
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "description")
    private String desc;

    private double price;
    private String image;
    private String category;

    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id")
    private Users user;
}