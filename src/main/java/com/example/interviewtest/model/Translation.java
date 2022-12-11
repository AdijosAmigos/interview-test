package com.example.interviewtest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Translation" )
@Data
@NoArgsConstructor
public class Translation {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String translation;
  @OneToOne
  @JoinColumn(name = "dictionary_id", referencedColumnName = "id")
  private Dictionary dictionary;

  public Translation(Long id, String translation) {
    this.id = id;
    this.translation = translation;
  }
}
