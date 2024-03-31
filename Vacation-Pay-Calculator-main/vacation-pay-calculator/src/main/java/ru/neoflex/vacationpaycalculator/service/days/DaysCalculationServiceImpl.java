package ru.neoflex.vacationpaycalculator.service.days;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DaysCalculationServiceImpl implements DaysCalculationService {

    /** Текущий год */
    public final static int CURRENT_YEAR = LocalDate.now().getYear();

    /**
     * Метод для расчёта количества оплачиваемых дней в отпуске с учётом праздников и выходных
     * @param vacationDays      - общее количество дней отпуска
     * @param startVacationDate - дата начала отпуска
     * @return возвращает количество оплачиваемых дней отпуска
     */
    @Override
    public int calculatePaidDays(int vacationDays,
                                 LocalDate startVacationDate) {

        Predicate<LocalDate> holidays = getHolidays()::contains;

        List<LocalDate> listPaidVacationDate = Stream.iterate(startVacationDate, nextVacationDate -> nextVacationDate.plusDays(1)).limit(vacationDays)
                .filter(vacationDate -> !(holidays.test(vacationDate)))
                .filter(vacationDate -> !(vacationDate.getDayOfWeek() == DayOfWeek.SATURDAY || vacationDate.getDayOfWeek() == DayOfWeek.SUNDAY))
                .collect(Collectors.toList());

        // Выходные дни в число дней ежегодного отпуска включаются. То есть второго вызова filter не должно быть.
        // Но по заданию должен проводиться рассчет отпускных с учётом праздников и выходных.
        // Если бы я был на работе, то этот момент обязательно уточнил!

        return listPaidVacationDate.size();
    }

    /**
     * Метод для хранения праздничных дней в формате LocalDate
     * @return возвращает список List, состоящий из праздничных дней
     */
    public static List<LocalDate> getHolidays() {
        List<LocalDate> holidays = Stream.of(
                LocalDate.of(CURRENT_YEAR, 1, 1),
                LocalDate.of(CURRENT_YEAR, 1, 2),
                LocalDate.of(CURRENT_YEAR, 1, 3),
                LocalDate.of(CURRENT_YEAR, 1, 4),
                LocalDate.of(CURRENT_YEAR, 1, 5),
                LocalDate.of(CURRENT_YEAR, 1, 6),
                LocalDate.of(CURRENT_YEAR, 1, 7),
                LocalDate.of(CURRENT_YEAR, 1, 8),
                LocalDate.of(CURRENT_YEAR, 2, 23),
                LocalDate.of(CURRENT_YEAR, 3, 8),
                LocalDate.of(CURRENT_YEAR, 5, 1),
                LocalDate.of(CURRENT_YEAR, 5, 9),
                LocalDate.of(CURRENT_YEAR, 6, 12),
                LocalDate.of(CURRENT_YEAR, 11, 4)
        ).collect(Collectors.toList());

        return Collections.unmodifiableList(holidays);
    }
}
