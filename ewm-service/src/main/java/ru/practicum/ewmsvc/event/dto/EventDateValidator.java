package ru.practicum.ewmsvc.event.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventDateValidator implements ConstraintValidator<EventDate, String> {
    private final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public void initialize(EventDate constraint) {
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {

        if (date == null) {
            return true;
        }

        if (parseLocalDateTime(date, DTF).isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Invalid date");
        }
        return true;
    }

    private static LocalDateTime parseLocalDateTime(CharSequence text, DateTimeFormatter DTF) {
        if (text == null) {
            return null;
        }
        return DTF.parse(text, LocalDateTime::from);
    }
}
