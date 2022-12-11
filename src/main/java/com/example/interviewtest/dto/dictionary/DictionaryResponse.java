package com.example.interviewtest.dto.dictionary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryResponse {
  private Long id;
  private String countryCode;
}
