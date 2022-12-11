package com.example.interviewtest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import com.example.interviewtest.exception.LengthDictionaryException;
import com.example.interviewtest.model.Dictionary;
import com.example.interviewtest.model.Translation;
import com.example.interviewtest.repository.DictionaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DictionaryServiceTest {

  @Autowired
  private DictionaryRepository dictionaryRepository;
  private Dictionary dictionary;


  @BeforeEach
  void createDictionary(){
    dictionary = new Dictionary(1L, "WAW");
  }

  @Test
  public void shouldAddDictionary(){
    //when
    var result = dictionaryRepository.save(dictionary);
    //then
    assertThat(result).isEqualTo(dictionary);
  }

  @Test
  public void shouldThrowExceptionWhenAddDictionary(){
    LengthDictionaryException exception = assertThrows(LengthDictionaryException.class,
      () -> new Dictionary(2L, "WARSZAWA"));

    assertEquals("Country code should be equal", exception.getMessage());

  }
  @Test
  public void shouldGetAllDictionaries(){
    //when
    dictionaryRepository.save(dictionary);
    //when
    var result = dictionaryRepository.findAll();
    //then
    assertThat(result.isEmpty()).isFalse();
    assertThat(result.size()).isEqualTo(1);
  }

  @Test
  public void shouldDeleteDictionary(){
    //given
    dictionaryRepository.save(dictionary);
    //when
    dictionaryRepository.deleteById(dictionary.getId());
    //then
    assertThat(dictionaryRepository.findAll()).isEmpty();
  }

}