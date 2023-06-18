package com.gre.vocab.VocabWords.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import com.gre.vocab.VocabWords.authorization.GoogleAuthorizationConfig;
import com.gre.vocab.VocabWords.entity.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class GoogleSheetsService implements IGoogleSheetsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(GoogleSheetsService.class);
  private static final String valueInputOption = "RAW";
  private static final String BLANK = "";

  @Autowired private GoogleAuthorizationConfig googleAuthorizationConfig;

  @Override
  public List<List<Object>> getSpreadsheetValues(String spreadsheetId)
      throws IOException, GeneralSecurityException {
    Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
    Sheets.Spreadsheets.Values.BatchGet request =
        sheetsService.spreadsheets().values().batchGet(spreadsheetId);
    request.setRanges(getSpreadSheetRange(spreadsheetId));
    request.setMajorDimension("ROWS");
    BatchGetValuesResponse response = request.execute();
    return response.getValueRanges().get(0).getValues();
  }

  @Override
  public Integer updateSheetWithNewWord(Word word, String spreadsheetId)
      throws GeneralSecurityException, IOException {
    // LOGGER.info("word: " + word);
    List<List<Object>> spreadSheetValues = getSpreadsheetValues(spreadsheetId);
    if (spreadSheetValues.stream()
        .anyMatch(row -> row != null && !row.isEmpty() && word.getWord().equalsIgnoreCase(row.get(0).toString().toLowerCase()))) {
      return 0;
    }
    int nextRow = spreadSheetValues.size() + 2;
    List<List<Object>> newWordRowData = createNewWordRow(word);
    UpdateValuesResponse response = updateValues(spreadsheetId, getRangeString(nextRow), newWordRowData);
    return 1;
  }

  private String getRangeString(int nextRow) {
    return "R" + nextRow + "C1:R" + nextRow + "C7";
  }

  private List<List<Object>> createNewWordRow(Word word) {
    List<List<Object>> rowData = new ArrayList<>();
    List<Object> columnData = new ArrayList<>();
    columnData.add("");
    columnData.add(word.getWord());
    columnData.add("");
    columnData.add(formatData(word.getDefinitions(), false));
    columnData.add(formatData(word.getSynonyms(), true));
    columnData.add(word.getTrick());
    if (null == word.getTrick() || BLANK.equals(word.getTrick())) {
      columnData.add(BLANK);
    }
    columnData.add(formatData(word.getSentences(), false));
    rowData.add(columnData);
    return rowData;
  }

  private Object formatData(List<String> listOfValues, boolean commaSeparated) {
    if (Objects.isNull(listOfValues) || Objects.requireNonNull(listOfValues).isEmpty()) {
      return BLANK;
    }
    if (listOfValues.size() == 1) {
      return listOfValues.get(0);
    }
    StringBuilder formattedList = new StringBuilder();

    if(commaSeparated) {
      listOfValues.forEach(synonym -> formattedList.append(synonym).append(","));
      formattedList.deleteCharAt(formattedList.length() - 1);
    } else {
      int counter = 0;
      for (String value : listOfValues) {
        counter++;
        formattedList.append(counter).append(". ").append(value).append("\n");
      }
    }
    return formattedList.toString();
  }

  private List<String> getSpreadSheetRange(String spreadsheetId)
      throws IOException, GeneralSecurityException {
    Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
    Sheets.Spreadsheets.Get request = sheetsService.spreadsheets().get(spreadsheetId);
    Spreadsheet spreadsheet = request.execute();
    Sheet sheet = spreadsheet.getSheets().get(1);
    System.out.println(sheet.getProperties().getTitle());
    int row = sheet.getProperties().getGridProperties().getRowCount();
    int col = sheet.getProperties().getGridProperties().getColumnCount();
    return Collections.singletonList(
        "R1C1:R".concat(String.valueOf(row)).concat("C").concat(String.valueOf(col)));
  }

  /**
   * Sets values in a range of a spreadsheet.
   *
   * @param spreadsheetId - id of the spreadsheet.
   * @param range - Range of cells of the spreadsheet.
   * @param values - List of rows of values to input.
   * @return spreadsheet with updated values
   * @throws IOException - if credentials file not found.
   */
  private UpdateValuesResponse updateValues(
      String spreadsheetId, String range, List<List<Object>> values)
      throws IOException {

    UpdateValuesResponse result = null;
    try {
      Sheets sheetService = googleAuthorizationConfig.getSheetsService();
      // Updates the values in the specified range.
      ValueRange body = new ValueRange().setValues(values);
      result =
          sheetService
              .spreadsheets()
              .values()
              .update(spreadsheetId, range, body)
              .setValueInputOption(valueInputOption)
              .execute();
    } catch (GoogleJsonResponseException | GeneralSecurityException e) {
      // TODO(developer) - handle error appropriately
    }
    return result;
  }
}
