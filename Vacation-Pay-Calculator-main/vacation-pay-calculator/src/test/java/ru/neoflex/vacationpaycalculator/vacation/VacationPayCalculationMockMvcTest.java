package ru.neoflex.vacationpaycalculator.vacation;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.neoflex.vacationpaycalculator.ResolversAbstractCommonConfiguration;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class VacationPayCalculationMockMvcTest extends ResolversAbstractCommonConfiguration {

    public final static String VACATION_PAY_API = "/calculacte";

    @Autowired
    protected MockMvc mockMvc;

    @Test
    @ApiOperation(value = "Test for calculating vacation pay by average salary and number of vacation days")
    void calculationOfVacationPayForEmployeeUsingSimpleQueryTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(VACATION_PAY_API)
                .param("averageSalary", String.valueOf(30500.00))
                .param("vacationDays", String.valueOf(30))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vacationPay").value(BigDecimal.valueOf(27168.80).stripTrailingZeros()))
                //.andExpect(MockMvcResultMatchers.content().string("27168.80"))
                .andReturn();

        log.info(result.getResponse().getContentAsString());
    }

    @Test
    @ApiOperation(value = "Test for calculating vacation pay by average salary, number of vacation days and date of vacation")
    void calculationOfVacationPayForEmployeeUsingQueryWithDateTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(VACATION_PAY_API)
                .param("averageSalary", String.valueOf(30500.00))
                .param("vacationDays", String.valueOf(30))
                .param("startVacationDate", "2022-02-25")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vacationPay").value(BigDecimal.valueOf(18113.20).stripTrailingZeros()))
                //.andExpect(MockMvcResultMatchers.content().string("18113.20"))
                .andReturn();

        log.info(result.getResponse().getContentAsString());
    }
}
