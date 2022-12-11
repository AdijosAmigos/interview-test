package com.example.interviewtest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import com.example.interviewtest.dto.translation.TranslationCreateRequest;
import com.example.interviewtest.dto.translation.TranslationUpdateRequest;
import com.example.interviewtest.exception.LengthTranslationException;
import com.example.interviewtest.model.Translation;
import com.example.interviewtest.service.TranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TranslationControllerTestIT {
  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate testRestTemplate;
  @Autowired
  private TranslationService translationService;

  private final String domain = "http://localhost:";

  private TranslationCreateRequest translation;

  @BeforeEach
  void addTranslation(){
    translation = new TranslationCreateRequest(1L, "translation");
  }

  @Test
  public void shouldAddTranslation(){

    var result = testRestTemplate.postForEntity(domain + port + "/translation", translation, Translation.class);

    assertThat(result.getStatusCodeValue()).isEqualTo(201);
    assertThat(result.hasBody()).isTrue();
    assertThat(result.getBody().getId()).isEqualTo(translation.getId());
    assertThat(result.getBody().getTranslation()).isEqualTo(translation.getTranslation());
    assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
  }

  @Test
  public void shouldGetAllTranslations() throws LengthTranslationException {

    translationService.addTranslation(translation);

    var result = testRestTemplate.getForEntity(domain + port + "/translation", Translation[].class);

    assertThat(result.getStatusCodeValue()).isEqualTo(200);
    assertThat(result.hasBody()).isTrue();
    assertThat(result.getBody()).isInstanceOf(Translation[].class);
    assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
  }

  @Test
  public void shouldGetTranslationById() throws LengthTranslationException {

    translationService.addTranslation(translation);

    var result = testRestTemplate.getForEntity(domain + port + "/translation/1", Translation.class);

    assertThat(result.getStatusCodeValue()).isEqualTo(200);
    assertThat(result.hasBody()).isTrue();
    assertThat(result.getBody()).isInstanceOf(Translation.class);
    assertThat(result.getBody().getId()).isEqualTo(1L);
    assertThat(result.getBody().getTranslation()).isEqualTo("translation");
    assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
  }

  @Test
  void shouldDeleteTranslation() throws LengthTranslationException {

    TranslationCreateRequest translationCreateRequest = new TranslationCreateRequest(2L, "translation2");
    translationService.addTranslation(translationCreateRequest);
    translationService.addTranslation(translation);

    testRestTemplate.delete(domain + port + "/translation/delete/2");

    assertThat(translationService.getTranslations()).size().isEqualTo(1);
    assertThat(translation).isNotIn(translationService.getTranslations());

  }

  @Test
  void shouldUpdateUser() throws LengthTranslationException {
    translationService.addTranslation(translation);

    TranslationUpdateRequest translationUpdateRequest = new TranslationUpdateRequest("translation2");

    HttpEntity<TranslationUpdateRequest> httpEntity = new HttpEntity<>(translationUpdateRequest);

    var result = testRestTemplate.exchange(
      domain + port + "/translation/1", HttpMethod.PUT, httpEntity, Translation.class);

    assertThat(result.getStatusCodeValue()).isEqualTo(200);
    assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(result.hasBody()).isTrue();
    assertThat(result.getBody()).isInstanceOf(Translation.class);
    assertThat(result.getBody().getTranslation()).isEqualTo(httpEntity.getBody().getTranslation());
  }

}