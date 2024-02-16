package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.example.CsvJsonParser.listToJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CsvJsonParserTest {
    private final String[] COLUMN_MAPPING_EMPLOYEE = {"id", "firstName", "lastName", "country", "age"};
    final String VALID_FILE_CSV_PATH = new File(getClass().getClassLoader().getResource("valid_data.csv")
            .getFile()).getAbsolutePath();
    final String INVALID_FILE_CSV_PATH = new File(getClass().getClassLoader().getResource("invalid_data.csv")
            .getFile()).getAbsolutePath();

    @Test
    @DisplayName("Test ParseCSV Positive")
    public void testParseCsvWithValidInputShouldReturnCorrectData() {
        CsvJsonParser parser = new CsvJsonParser();
        List<Employee> resultEmpsList = parser.parseCSV(COLUMN_MAPPING_EMPLOYEE, VALID_FILE_CSV_PATH);

        assertThat("Список не должен быть null", resultEmpsList, not(is(empty())));
        assertThat(resultEmpsList, allOf(
                hasSize(2),
                not(is(empty()))
        ));

        var firstEmployee = resultEmpsList.get(0);

        Employee expectedFirstEmployee = new Employee(1, "John", "Smith", "USA", 25);
        assertThat("Данные ожидаемые не соответствуют с реальными", firstEmployee, allOf(
                hasProperty("id", is(expectedFirstEmployee.getId())),
                hasProperty("firstName", equalTo(expectedFirstEmployee.getFirstName())),
                hasProperty("lastName", equalTo(expectedFirstEmployee.getLastName())),
                hasProperty("country", equalTo(expectedFirstEmployee.getCountry())),
                hasProperty("age", is(expectedFirstEmployee.getAge()))
        ));
    }

    @Test
    @DisplayName("Test ListToJson Positive")
    public void testListToJsonWithValidInputShouldReturnCorrectData() {
        CsvJsonParser parser = new CsvJsonParser();
        List<Employee> resultEmpsList = parser.parseCSV(COLUMN_MAPPING_EMPLOYEE, VALID_FILE_CSV_PATH);
        String jsonActual = listToJson(resultEmpsList);

        var employees = List.of(
                new Employee(1, "John", "Smith", "USA", 25),
                new Employee(2, "Ivan", "Petrov", "RU", 23)
        );
        String jsonExpected = listToJson(employees);

        assertThat("JSON строка должна соответствовать ожидаемой", jsonActual, is(jsonExpected));
        assertThat(jsonActual, notNullValue());
        assertThat(jsonActual, startsWith("["));
        assertThat(jsonActual, endsWith("]"));
    }

    @Test
    @DisplayName("Test ParseCSV Negative")
    public void testParseCSVWithInvalidInputShouldReturnEmptyList() {
        CsvJsonParser parser = new CsvJsonParser();
        List<Employee> resultEmpsList = parser.parseCSV(COLUMN_MAPPING_EMPLOYEE, INVALID_FILE_CSV_PATH);

        assertThat(resultEmpsList, is(empty()));
    }

}