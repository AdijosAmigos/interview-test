package com.example.interviewtest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import com.example.interviewtest.model.Dictionary;
import com.example.interviewtest.model.Translation;
import com.example.interviewtest.repository.TranslationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TranslationServiceTest {

  @Autowired
  private TranslationRepository translationRepository;

  private Translation translation;

  @BeforeEach
  void addTranslation(){
    translation = new Translation(1L, "translation");
  }

  @Test
  public void shouldAddTranslation(){
    //when
    var result = translationRepository.save(translation);
    //then
    assertThat(result).isEqualTo(translation);
  }

  @Test
  public void shouldGetAllTranslations(){
    //given
    translationRepository.save(translation);
    //when
    var result = translationRepository.findAll();
    //then
    assertThat(result.isEmpty()).isFalse();
    assertThat(result.size()).isEqualTo(1);
  }

  @Test
  public void shouldGetTranslationById(){
    //given
    translationRepository.save(translation);
    //when
    var result = translationRepository.findById(translation.getId());
    //then
    assertThat(result.isPresent()).isTrue();
    assertThat(result.get().getId()).isEqualTo(translation.getId());
  }

  @Test
  public void shouldDeleteTranslation(){
    //given
    translationRepository.save(translation);
    //when
    translationRepository.deleteById(translation.getId());
    //then
    assertThat(translationRepository.findAll().isEmpty()).isTrue();
  }

  @Test
  public void shouldUpdateTranslation(){
    //given
    Translation updatedTranslation = new Translation(1L, "updatedTranslation");
    translationRepository.save(translation);
    //when
    var result = translationRepository.findById(translation.getId()).orElseThrow();
    result.setTranslation(updatedTranslation.getTranslation());
    Translation finalTranslation = translationRepository.save(result);
    //then
    assertThat(finalTranslation.getId()).isEqualTo(updatedTranslation.getId());
    assertThat(finalTranslation.getTranslation()).isEqualTo(updatedTranslation.getTranslation());
  }

}