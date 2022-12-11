package com.example.interviewtest.controller;

import com.example.interviewtest.dto.dictionary.DictionaryCreateRequest;
import com.example.interviewtest.dto.dictionary.DictionaryResponse;
import com.example.interviewtest.exception.LengthDictionaryException;
import com.example.interviewtest.model.Dictionary;
import com.example.interviewtest.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
  private final DictionaryService dictionaryService;

  @Autowired
  public DictionaryController(DictionaryService dictionaryService) {
    this.dictionaryService = dictionaryService;
  }

  @PostMapping
  public ResponseEntity<DictionaryResponse> addDictionary(
    @RequestBody DictionaryCreateRequest dictionaryCreateRequest) throws LengthDictionaryException {
    Dictionary dictionary = dictionaryService.addDictionary(dictionaryCreateRequest);
    return new ResponseEntity<>(toDictionaryResponse(dictionary), HttpStatus.CREATED);
  }

  private DictionaryResponse toDictionaryResponse(Dictionary dictionary){
    return new DictionaryResponse(dictionary.getId(), dictionary.getCountryCode());
  }
}
