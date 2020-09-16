package com.nikkodasig.springbudgetapi.model;

import javax.persistence.*;

@Entity
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @Enumerated(value = EnumType.STRING)
  private CategoryType type;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public CategoryType getType() {
    return type;
  }
}
