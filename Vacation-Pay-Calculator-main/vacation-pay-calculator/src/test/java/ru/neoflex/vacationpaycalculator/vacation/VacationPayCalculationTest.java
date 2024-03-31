package ru.neoflex.vacationpaycalculator.vacation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.neoflex.vacationpaycalculator.service.days.DaysCalculationServiceImpl;
import ru.neoflex.vacationpaycalculator.service.vacation.VacationPayCalculatorServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class VacationPayCalculationTest {

    private VacationPayCalculatorServiceImpl vacationPayCalculatorService;
    private DaysCalculationServiceImpl daysCalculationService;

    private final BigDecimal testAverageSalaryPerYear = new BigDecimal("30500.00");
    private final int testVacationDays = 30;

    @BeforeEach
    void init() {
        log.info("startup");
        vacationPayCalculatorService = new VacationPayCalculatorServiceImpl();
        daysCalculationService = new DaysCalculationServiceImpl();
    }

    @AfterEach
    public void teardown() {
        log.info("teardown");
        vacationPayCalculatorService = null;
        daysCalculationService = null;
    }

    @Test
    @DisplayName("Vacation pay test")
    void calculationOfVacationPayForEmployeeUsingSimpleQueryTest() {

        BigDecimal actual = vacationPayCalculatorService.getVacationPayCalculation(testAverageSalaryPerYear, testVacationDays).getVacationPay();
        assertEquals(BigDecimal.valueOf(27168.80).stripTrailingZeros(), actual.stripTrailingZeros());
    }

    @Test
    @DisplayName("Vacation pay test including holidays and weekends")
    void calculationOfVacationPayForEmployeeUsingQueryWithDateTest() {

        LocalDate testStartVacationDate = LocalDate.of(2022, 2, 25);

        int testPaidVacationDays = daysCalculationService.calculatePaidDays(testVacationDays, testStartVacationDate);
        BigDecimal actual = vacationPayCalculatorService.getVacationPayCalculation(testAverageSalaryPerYear, testPaidVacationDays).getVacationPay();
        assertEquals(BigDecimal.valueOf(18113.20).stripTrailingZeros(), actual.stripTrailingZeros());
    }
}
