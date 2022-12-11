package com.example.interviewtest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import com.example.interviewtest.model.Dictionary;
import com.example.interviewtest.model.Translation;
import com.example.interviewtest.service.TranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DictionaryControllerTestIT {

  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate testRestTemplate;

  private final String domain = "http://localhost:";

  private Dictionary dictionary;

  @BeforeEach
  void addTranslation(){
    dictionary = new Dictionary(1L, "WAW");
  }

  @Test
  public void shouldAddDictionary(){

    var result = testRestTemplate.postForEntity(domain + port + "/dictionary", dictionary, Dictionary.class);

    assertThat(result.getStatusCodeValue()).isEqualTo(201);
    assertThat(result.hasBody()).isTrue();
    assertThat(result.getBody().getId()).isEqualTo(dictionary.getId());
    assertThat(result.getBody().getCountryCode()).isEqualTo(dictionary.getCountryCode());
    assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
  }

}