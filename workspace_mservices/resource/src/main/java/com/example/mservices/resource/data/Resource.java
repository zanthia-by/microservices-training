package com.example.mservices.resource.data;

import jakarta.persistence.*;

@Entity
@Table(name = "RESOURCE")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resource_generator")
    @SequenceGenerator(name="resource_generator", sequenceName = "RESOURCE_SEQ", allocationSize=1)
    @Column(name = "ID")
    private long id;

    @Column(name = "LOCATION")
    private String location;
}
