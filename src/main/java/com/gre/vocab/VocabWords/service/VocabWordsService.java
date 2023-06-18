package com.gre.vocab.VocabWords.service;

import com.gre.vocab.VocabWords.entity.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

@Service
public class VocabWordsService implements IVocabWordsService{

    @Autowired
    private GoogleSheetsService googleSheetsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(VocabWordsService.class);

    private static final String WORD = "wor";
    private static final String TRICK = "tri";
    private static final String SENTENCE = "sen";
    private static final String DEFINITION = "def";
    private static final String SYNONYM = "syn";

    @Override
    public void publishWordToGoogleSheet(MultiValueMap<String, String> formData, String spreadsheetId) throws GeneralSecurityException, IOException {
        Word word = createWord(formData);
        Integer noOfCellsUpdated = googleSheetsService.updateSheetWithNewWord(word, spreadsheetId);
    }

    private Word createWord(MultiValueMap<String, String> formData) {
        Word word = new Word();
        formData.forEach(
                (key, value) -> {
                    if (null != value.get(0) && !"".equals(StringUtils.trimAllWhitespace(value.get(0)))) {
                        switch (key.substring(0, 3)) {
                            case WORD:
                                word.setWord(value.get(0));
                                break;
                            case TRICK:
                                word.setTrick(value.get(0));
                                break;
                            case SENTENCE:
                                if (null == word.getSentences()) {
                                    word.setSentences(new ArrayList<>());
                                }
                                word.getSentences().add(value.get(0));
                                break;
                            case SYNONYM:
                                if (null == word.getSynonyms()) {
                                    word.setSynonyms(new ArrayList<>());
                                }
                                word.getSynonyms().add(value.get(0));
                                break;
                            case DEFINITION:
                                if (null == word.getDefinitions()) {
                                    word.setDefinitions(new ArrayList<>());
                                }
                                word.getDefinitions().add(value.get(0));
                                break;
                            default:
                                // do nothing
                        }
                    }
                });
        // LOGGER.info("Word Object: "+word.toString());
        return word;
    }
}
