package org.example.MessafeProcessingTests;

import org.example.MessageProcessing.Parser;
import org.example.MessageProcessing.ParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


public class FilterTest {

    @Test
    public void FilterOutDataTest(){
        LocalDate localDate = LocalDate.of(LocalDate.now().getYear(), 10, 20);
        try {
            Assertions.assertEquals(Parser.parseData("20 10"), localDate);
        }catch (ParserException e){
            e.printStackTrace();
        }
    }

    @Test
    void testExpectedException() {

        ParserException thrown = Assertions.assertThrows(ParserException.class, () -> {
            Parser.parseData("20 20");
        });

        Assertions.assertEquals("Неверный формат ввода", thrown.getMessage());
    }

    @Test
    void testExpectedException2() {

        ParserException thrown = Assertions.assertThrows(ParserException.class, () -> {
            Parser.parseData("200");
        });

        Assertions.assertEquals("Неверный формат ввода", thrown.getMessage());
    }
}
