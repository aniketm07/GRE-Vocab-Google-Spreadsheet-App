package com.gre.vocab.VocabWords.controller;

import com.gre.vocab.VocabWords.service.GoogleSheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class GoogleSheetsController {

  @Autowired GoogleSheetsService service;

  @Value("${spreadsheet.id}")
  private String spreadsheetId;

  @GetMapping(value = "/print")
  public void printLogs() throws GeneralSecurityException, IOException {
    service.getSpreadsheetValues(spreadsheetId);
  }
}
