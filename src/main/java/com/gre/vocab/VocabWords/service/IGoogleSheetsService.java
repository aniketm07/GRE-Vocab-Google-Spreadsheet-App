package com.gre.vocab.VocabWords.service;

import com.gre.vocab.VocabWords.entity.Word;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface IGoogleSheetsService {
  List<List<Object>> getSpreadsheetValues(String spreadsheetId)
      throws IOException, GeneralSecurityException;

  Integer updateSheetWithNewWord(Word word, String spreadsheetId)
      throws GeneralSecurityException, IOException;
}
