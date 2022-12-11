package com.example.interviewtest.controller;

import java.util.List;
import com.example.interviewtest.dto.translation.TranslationCreateRequest;
import com.example.interviewtest.dto.translation.TranslationResponse;
import com.example.interviewtest.dto.translation.TranslationUpdateRequest;
import com.example.interviewtest.exception.LengthTranslationException;
import com.example.interviewtest.model.Translation;
import com.example.interviewtest.model.TranslationPage;
import com.example.interviewtest.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/translation")
public class TranslationController {

  private final TranslationService translationService;

  @Autowired
  public TranslationController(TranslationService translationService) {
    this.translationService = translationService;
  }

  @PostMapping
  public ResponseEntity<TranslationResponse> addTranslation(
    @RequestBody TranslationCreateRequest translationCreateRequest) throws LengthTranslationException {
    Translation translation = translationService.addTranslation(translationCreateRequest);
    return new ResponseEntity<>(toTranslationResponse(translation), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<TranslationResponse>> getAllTranslations(){
    List<TranslationResponse> translations = translationService.getTranslations()
      .stream()
      .map(this::toTranslationResponse)
      .toList();
    return new ResponseEntity<>(translations, HttpStatus.OK);
  }

  @GetMapping("/pagination")
  public ResponseEntity<Page<Translation>> getTranslationWithPaging(TranslationPage translationPage){
    return new ResponseEntity<>(translationService.getTranslationsWithPagination(translationPage), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TranslationResponse> getTranslationById(@PathVariable Long id){
    if(id < 0){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Translation translationById = translationService.findTranslationById(id);
    return new ResponseEntity<>(toTranslationResponse(translationById), HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<TranslationResponse> deleteTranslationById(@PathVariable Long id){
    translationService.deleteTranslation(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TranslationResponse> updateTranslation(
    @RequestBody TranslationUpdateRequest translationUpdateRequest,
    @PathVariable Long id){
    Translation translation = translationService.updateTranslation(id, translationUpdateRequest);
    return new ResponseEntity<>(toTranslationResponse(translation), HttpStatus.OK);
  }

  private TranslationResponse toTranslationResponse(Translation translation){
    return new TranslationResponse(translation.getId(), translation.getTranslation());
  }

}

