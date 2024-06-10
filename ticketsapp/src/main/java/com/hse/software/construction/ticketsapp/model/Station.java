package com.hse.software.construction.ticketsapp.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "station")
public class Station {
    @org.springframework.data.annotation.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String station;
}
