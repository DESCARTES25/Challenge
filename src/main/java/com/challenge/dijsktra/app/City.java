package com.challenge.dijsktra.app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class City {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  private String name;

  protected City() {}

  public City(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format(
        "City[id=%d, name='%s']",
        id, name);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

}