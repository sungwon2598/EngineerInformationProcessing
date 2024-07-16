package com.example.ass.service;

import com.example.ass.domain.Question;
import com.example.ass.domain.Answer;
import com.example.ass.repository.QuestionRepository;
import com.example.ass.repository.AnswerRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;

@Service
public class ExcelService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public ExcelService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public void saveExcelData(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        while (rows.hasNext()) {
            Row currentRow = rows.next();
            if (currentRow.getRowNum() == 0) { // skip header row
                continue;
            }

            Cell questionCell = currentRow.getCell(0);
            Cell answerCell = currentRow.getCell(1);

            String questionContent = getCellValueAsString(questionCell);
            String answerContent = getCellValueAsString(answerCell);

            Question question = new Question();
            question.setContent(questionContent);

            Answer answer = new Answer();
            answer.setContent(answerContent);
            answer.setQuestion(question);

            question.setAnswer(answer);

            questionRepository.save(question);
        }

        workbook.close();
    }

    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}