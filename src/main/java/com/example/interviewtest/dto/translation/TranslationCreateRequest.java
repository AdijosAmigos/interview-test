package com.example.interviewtest.dto.translation;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationCreateRequest {
  private Long id;
  private String translation;
}
