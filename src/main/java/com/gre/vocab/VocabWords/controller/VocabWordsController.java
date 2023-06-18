package com.gre.vocab.VocabWords.controller;

import com.gre.vocab.VocabWords.entity.Word;
import com.gre.vocab.VocabWords.service.VocabWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

@Controller
public class VocabWordsController {

  @Autowired
  private VocabWordsService vocabWordsService;

  @Value("${spreadsheet.id}")
  private String spreadsheetId;

  @PostMapping(value = "/insertWord", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ModelAndView insertWord(@RequestBody MultiValueMap<String, String> formData) throws GeneralSecurityException, IOException {

    vocabWordsService.publishWordToGoogleSheet(formData, spreadsheetId);
    return new ModelAndView("redirect:/");
  }


}
