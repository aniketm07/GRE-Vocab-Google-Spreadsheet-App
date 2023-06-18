package com.gre.vocab.VocabWords.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Word {
  private String word;
  private String trick;
  private List<String> definitions;
  private List<String> sentences;
  private List<String> synonyms;
}
