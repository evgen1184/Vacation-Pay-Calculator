package ru.neoflex.vacationpaycalculator.service.vacation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.neoflex.vacationpaycalculator.dto.VacationPayResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Component
@RequiredArgsConstructor
public class VacationPayCalculatorServiceImpl implements VacationPayCalculatorService {

    /** Среднее количество дней в месяце без учета федеральных праздников */
    private static final double AVERAGE_NUMBER_DAYS_IN_MOUNT = 29.3;
    /** Процент НДФЛ */
    private static final double NDFL_PERCENT = 0.13;

    /**
     * Функция для расчёта отпускных сотрудника в рублях
     * @param averageSalaryPerYear - средняя зарплата в рублях за 12 месяцев
     * @param vacationDays         - количество дней отпуска
     * @return возвращает сумму отпускных в рублях, которые придут сотруднику
     */
    @Override
    public VacationPayResponse getVacationPayCalculation(BigDecimal averageSalaryPerYear,
                                                         int vacationDays) {

        BigDecimal averageEarningsPerDay = averageSalaryPerYear.divide(BigDecimal.valueOf(AVERAGE_NUMBER_DAYS_IN_MOUNT), 2, RoundingMode.HALF_EVEN);
        log.info("Средний дневной заработок = {} RUB", averageEarningsPerDay);

        BigDecimal totalPayWithoutNDFL = averageEarningsPerDay.multiply(BigDecimal.valueOf(vacationDays));
        log.info("Сумма отпускных без вычета НДФЛ = {} RUB", totalPayWithoutNDFL);

        BigDecimal amountNDFL = totalPayWithoutNDFL.multiply(BigDecimal.valueOf(NDFL_PERCENT)).setScale(0, RoundingMode.HALF_UP);
        log.info("Сумма НДФЛ = {} RUB", amountNDFL);

        BigDecimal totalPay = totalPayWithoutNDFL.subtract(amountNDFL);
        log.info("К выплате с вычетом НДФЛ = {} RUB", totalPay);

        return new VacationPayResponse("Сумма отпускных в рублях (с вычетом НДФЛ)", totalPay);
    }
}
