package net.ljw.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Table(name = "items")
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=false, nullable=false, length=100)
    private String name;

    @Column(unique=false, length=100)
    private String imgPath;

    @Column(unique=false, length=100)
    private String price;

    @Column(unique=false, length=100)
    private String discountPer;
}
