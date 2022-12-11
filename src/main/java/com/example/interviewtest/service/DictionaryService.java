package com.example.interviewtest.service;

import java.util.List;
import com.example.interviewtest.dto.dictionary.DictionaryCreateRequest;
import com.example.interviewtest.exception.LengthDictionaryException;
import com.example.interviewtest.model.Dictionary;
import com.example.interviewtest.model.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.interviewtest.repository.DictionaryRepository;

@Service
public class DictionaryService {
  private final DictionaryRepository dictionaryRepository;
  private final int CODE_LENGTH = 3;

  @Autowired
  public DictionaryService(DictionaryRepository dictionaryRepository) {
    this.dictionaryRepository = dictionaryRepository;
  }


  public Dictionary addDictionary(DictionaryCreateRequest dictionary) throws LengthDictionaryException {
    if(dictionary.getCountryCode().length() != CODE_LENGTH){
      throw new LengthDictionaryException("Country code should be equal " + CODE_LENGTH);
    }
    Dictionary createdDictionary = new Dictionary(dictionary.getId(), dictionary.getCountryCode());
    return dictionaryRepository.save(createdDictionary);
  }

  public List<Dictionary> getDictionaries(){
    return dictionaryRepository.findAll();
  }

  public void deleteDictionary(Long id){
    dictionaryRepository.deleteById(id);
  }
}
