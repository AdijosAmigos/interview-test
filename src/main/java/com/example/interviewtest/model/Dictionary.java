package com.example.interviewtest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Dictionary" )
@Data
@NoArgsConstructor
public class Dictionary {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String countryCode;
  @OneToOne(mappedBy = "dictionary")
  private Translation translation;

  public Dictionary(Long id, String countryCode) {
    this.id = id;
    this.countryCode = countryCode;
  }
}
