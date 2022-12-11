package com.example.interviewtest.service;


import java.util.List;
import com.example.interviewtest.dto.translation.TranslationCreateRequest;
import com.example.interviewtest.dto.translation.TranslationUpdateRequest;
import com.example.interviewtest.exception.LengthTranslationException;
import com.example.interviewtest.model.Dictionary;
import com.example.interviewtest.model.Translation;
import com.example.interviewtest.model.TranslationPage;
import com.example.interviewtest.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {
  private final TranslationRepository translationRepository;
  private final int MAX_TRANSLATION_LENGTH = 100;

  @Autowired
  public TranslationService(TranslationRepository translationRepository) {
    this.translationRepository = translationRepository;
  }

  public Translation addTranslation(TranslationCreateRequest translation) throws LengthTranslationException {
    if(translation.getTranslation().length() > MAX_TRANSLATION_LENGTH){
      throw new LengthTranslationException("Translation should be less than " + MAX_TRANSLATION_LENGTH);
    }
    Translation createdTranslation = new Translation(translation.getId(), translation.getTranslation());
    return translationRepository.save(createdTranslation);
  }

  public List<Translation> getTranslations(){
    return translationRepository.findAll();
  }

  public Page<Translation> getTranslationsWithPagination(TranslationPage translationPage){
    Sort sort = Sort.by(translationPage.getSortDirection(), translationPage.getSortBy());
    Pageable pageable = PageRequest.of(
      translationPage.getPageNumber(),
      translationPage.getPageSize(),
      sort);
    return translationRepository.findAll(pageable);
  }

  public Translation findTranslationById(Long id){
    return translationRepository.findById(id).orElseThrow();
  }

  public void deleteTranslation(Long id){
    translationRepository.deleteById(id);
  }

  public Translation updateTranslation(Long id, TranslationUpdateRequest toUpdate){
    Translation translationById = findTranslationById(id);
    translationById.setTranslation(toUpdate.getTranslation());
    return translationRepository.save(translationById);
  }
}
