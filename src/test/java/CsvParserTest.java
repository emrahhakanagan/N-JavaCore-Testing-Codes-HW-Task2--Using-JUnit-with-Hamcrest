import org.example.CsvJsonParser;
import org.example.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CsvParserTest {

    @Test
    public void testParseCSV_ShouldReturnNonEmptyList() {
        CsvJsonParser parser = new CsvJsonParser();
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";

        List<Employee> result = parser.parseCSV(columnMapping, fileName);

        assertThat(result, is(not(empty())));
        assertThat(result.size(), is(equalTo(2)));
    }

    @Test
    public void testListToJson_ShouldStartAndEndWithBrackets() {
        CsvJsonParser parser = new CsvJsonParser();
        List<Employee> employees = List.of(
                new Employee(1, "John", "Doe", "USA", 30),
                new Employee(2, "Anna", "Smith", "UK", 25)
        );

        String json = parser.listToJson(employees);

        assertThat(json, startsWith("["));
        assertThat(json, endsWith("]"));
    }

    @Test
    public void testEmployeeFields_ShouldBeCorrect() {
        Employee employee = new Employee(1, "John", "Doe", "USA", 30);

        assertThat(employee.getId(), is(equalTo(1L)));
        assertThat(employee.getFirstName(), allOf(notNullValue(), equalTo("John")));
    }

    @Test
    public void testParseCSV_ShouldContainSpecificEmployee() {
        CsvJsonParser parser = new CsvJsonParser();
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";

        List<Employee> result = parser.parseCSV(columnMapping, fileName);

        Employee expectedEmployee = new Employee(1, "John", "Smith", "USA", 25);
        assertThat(result, hasItem(hasProperty("firstName", equalTo("John"))));
        assertThat(result, hasItem(hasProperty("lastName", equalTo("Smith"))));
    }



}
