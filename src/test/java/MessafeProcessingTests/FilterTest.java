package MessafeProcessingTests;

import MessageProcessing.Filter;
import MessageProcessing.FilterException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


public class FilterTest {

    @Test
    public void FilterOutDataTest(){
        LocalDate localDate = LocalDate.of(LocalDate.now().getYear(), 10, 20);
        try {
            Assertions.assertEquals(Filter.toFilterOutData("20 10"), localDate);
        }catch (FilterException e){
            e.printStackTrace();
        }
    }

    @Test
    void testExpectedException() {

        FilterException thrown = Assertions.assertThrows(FilterException.class, () -> {
            Filter.toFilterOutData("20 20");
        });

        Assertions.assertEquals("Неверный формат ввода", thrown.getMessage());
    }

    @Test
    void testExpectedException2() {

        FilterException thrown = Assertions.assertThrows(FilterException.class, () -> {
            Filter.toFilterOutData("200");
        });

        Assertions.assertEquals("Неверный формат ввода", thrown.getMessage());
    }
}
