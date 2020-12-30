package com.challenge.dijsktra.app.DTOs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class CityDTO {

  private Long id;
  private String name;

  protected CityDTO() {}

  public CityDTO(String name) {
    this.name = name;
  }

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
  
  public void setName(String name) {
	    this.name = name;
  }
  
  public void setId(Long id) {
	    this.id = id;
  }
	  

}