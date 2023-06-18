package com.gre.vocab.VocabWords.service;

import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IVocabWordsService {

    void publishWordToGoogleSheet(MultiValueMap<String, String> formData, String spreadsheetId) throws GeneralSecurityException, IOException;
}
