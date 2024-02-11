package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.example.CsvJsonParser.listToJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CsvJsonParserTest {

    private final String[] COLUMN_MAPPING_EMPLOYEE = {"id", "firstName", "lastName", "country", "age"};

    @Test
    @DisplayName("Test ParseCSV Positive")
    public void testParseCsvWithValidInputShouldReturnCorrectData() {
        final String CSV_VALID_FILE_NAME = "valid_data.csv";

        CsvJsonParser parser = new CsvJsonParser();

        List<Employee> resultEmpsList = parser.parseCSV(COLUMN_MAPPING_EMPLOYEE, CSV_VALID_FILE_NAME);

//        assertThat("Список не должен быть null", resultEmpsList, not(is(empty())));
        assertThat(resultEmpsList, allOf(
                hasSize(2),
                not(is(empty()))
        ));

        var firstEmployee = resultEmpsList.get(0);

        Employee expectedFirstEmployee = new Employee(1, "John", "Smith", "USA", 25);
        assertThat("Список не должен быть null", firstEmployee, allOf(
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
        List<Employee> employees = List.of(
                new Employee(1, "John", "Doe", "USA", 30),
                new Employee(2, "Anna", "Smith", "UK", 25)
        );

        String json = listToJson(employees);

        assertThat(json, notNullValue());
        assertThat(json, startsWith("["));
        assertThat(json, endsWith("]"));
    }

    @Test
    @DisplayName("Test ParseCSV Negative")
    public void testParseCSVWithInvalidInputShouldReturnEmptyList() {
        final String CSV_INVALID_FILE_NAME = "invalid_data.csv";
        CsvJsonParser parser = new CsvJsonParser();

        List<Employee> resultEmpsList = parser.parseCSV(COLUMN_MAPPING_EMPLOYEE, CSV_INVALID_FILE_NAME);

        assertThat(resultEmpsList, is(empty()));
    }

}