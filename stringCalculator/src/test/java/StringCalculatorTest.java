import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorTest {

    StringCalculator sc = new StringCalculator();

    @Test
    public void stringCalculatorTestWithEmptyString() {
        assertEquals(0, sc.Add("0"));

        assertEquals(1, sc.Add("1"));

        assertEquals(2, sc.Add("2"));

        assertEquals(3, sc.Add("1,2"));
    }

    @Test
    public void stringCalculatorTestWithUnknownNumberOfString() {
        int result = sc.Add("1,2,3,4");
        assertEquals(10, result);
    }

    @Test
    public void stringCalculatorTestWithNewLine() {
        assertEquals(6, sc.Add("1\n2,3"));
    }

    @Test
    public void stringCalculatorTestWithNewLineBetweenString() {
        assertEquals(6, sc.Add("1\n2,3"));

        assertEquals(0, sc.Add("1,2,3,\n"));
    }

    @Test
    public void stringCalculatorTestWithDelimiter() {
        assertEquals(3, sc.Add("//;\n1;2"));
    }

    @Test
    public void stringCalculatorTestWithNegativeNumber() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sc.Add("-1,2"));
        assertTrue(e.getMessage().equals("Negatives not allowed:-1"));

        e = assertThrows(IllegalArgumentException.class, () -> sc.Add("2,-4,3,-5"));
        assertTrue(e.getMessage().equals("Negatives not allowed:-4, -5"));
    }

    @Test
    public void stringCalculatorTestWithNumberGreaterThan1000() {
        assertEquals(2, sc.Add("1001,2"));
    }

    @Test
    public void stringCalculatorTestWithLengthyDelimiters() {
        assertEquals(6, sc.Add("//[|||]\n1|||2|||3"));
    }

    @Test
    public void stringCalculatorTestWithMultipleDelimiters() {
        assertEquals(6, sc.Add("//[|][%]\n1|2%3"));
    }

    @Test
    public void stringCalculatorTestWithMultipleDelimitersOfAnyLength() {
        assertEquals(6, sc.Add("//[|||][%]\n1|||2%3"));

        assertEquals(15, sc.Add("//[@@@@@@@@@@@@@@@][%%%]\n1@@@@@@@@@@@@@@@2@@@@@@@@@@@@@@@3@@@@@@@@@@@@@@@4%%%5"));
    }
}
