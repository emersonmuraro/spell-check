package com.emerson.spell.check.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dictionary {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  private String word;

  protected Dictionary() {}

  public Dictionary(String word) {
    this.word = word;
  }

  @Override
  public String toString() {
    return String.format(
        "Customer[id=%d, word='%s']",
        id, word);
  }

  public Long getId() {
    return id;
  }

  public String getWord() {
    return word;
  }
}