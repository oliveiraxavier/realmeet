package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.com.sw2you.realmeet.exception.InvalidRequestException;

public final class ValidatorUtils {

    private ValidatorUtils() {}

    public static void thrownOnError(ValidationErrors validationErrors) {
        if (validationErrors.hasErrors()) {
            throw new InvalidRequestException(validationErrors);
        }
    }

    public static boolean validateRequired(String fieldValue, String fieldName, ValidationErrors validationErrors) {
        if (isBlank(fieldValue)) {
            validationErrors.add(fieldName, fieldName + MISSING);
            return false;
        }
        return true;
    }

    public static boolean validateRequired(Object fieldValue, String fieldName, ValidationErrors validationErrors) {
        if (isNull(fieldValue)) {
            validationErrors.add(fieldName, fieldName + MISSING);
            return false;
        }
        return true;
    }

    public static boolean validateMaxLength(
        String fieldValue,
        String fieldName,
        int maxLength,
        ValidationErrors validationErrors
    ) {
        if (!isBlank(fieldValue) && fieldValue.length() > maxLength) {
            validationErrors.add(fieldName, fieldName + EXCEEDS_MAX_LENGTH);
            return false;
        }
        return true;
    }

    public static boolean validateMaxValue(
        String fieldValue,
        String fieldName,
        int maxValue,
        ValidationErrors validationErrors
    ) {
        if (!isNull(fieldValue) && fieldValue.trim().length() > maxValue) {
            validationErrors.add(fieldName, fieldName + EXCEEDS_MAX_LENGTH);
            return false;
        }
        return true;
    }

    public static boolean validateMaxValue(
        Integer fieldValue,
        String fieldName,
        int maxValue,
        ValidationErrors validationErrors
    ) {
        if (!isNull(fieldValue) && fieldValue > maxValue) {
            validationErrors.add(fieldName, fieldName + EXCEEDS_MAX_VALUE);
            return false;
        }
        return true;
    }

    public static boolean validateMinValue(
        Integer fieldValue,
        String fieldName,
        int minValue,
        ValidationErrors validationErrors
    ) {
        if (!isNull(fieldValue) && fieldValue < minValue) {
            validationErrors.add(fieldName, fieldName + BELOW_MIN_VALUE);
            return false;
        }
        return true;
    }
}
