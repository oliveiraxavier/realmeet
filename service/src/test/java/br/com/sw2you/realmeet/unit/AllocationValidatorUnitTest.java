package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.util.DateUtils.now;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newCreateAllocationDto;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.sw2you.realmeet.core.BaseUnitTest;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import br.com.sw2you.realmeet.exception.InvalidRequestException;
import br.com.sw2you.realmeet.validator.AllocationValidator;
import br.com.sw2you.realmeet.validator.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class AllocationValidatorUnitTest extends BaseUnitTest {
    private AllocationValidator victim;

    @Mock
    private AllocationRepository allocationRepository;

    @BeforeEach
    void setupEach() {
        victim = new AllocationValidator(allocationRepository);
    }

    @Test
    void testValidateWhenAllocationIsValid() {
        victim.validate(newCreateAllocationDto());
    }

    @Test
    void testValidateWhenSubjectIsMissing() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateAllocationDto().subject(null))
        );

        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_SUBJECT, ALLOCATION_SUBJECT + MISSING),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenSubjectExceedsLength() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () ->
                victim.validate(newCreateAllocationDto().subject(rightPad("X", ALLOCATION_SUBJECT_MAX_LENGTH + 1, "z")))
        );

        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_SUBJECT, ALLOCATION_SUBJECT + EXCEEDS_MAX_LENGTH),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenEmployeeNameIsMissing() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateAllocationDto().employeeName(""))
        );

        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_EMPLOYEE_NAME, ALLOCATION_EMPLOYEE_NAME + MISSING),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenEmployeeNameExceedsLength() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () ->
                victim.validate(
                    newCreateAllocationDto().employeeName(rightPad("X", ALLOCATION_EMPLOYEE_NAME_MAX_LENGTH + 1, "z"))
                )
        );

        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_EMPLOYEE_NAME, ALLOCATION_EMPLOYEE_NAME + EXCEEDS_MAX_LENGTH),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenEmployeeEmailIsMissing() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateAllocationDto().employeeEmail(null))
        );

        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_EMPLOYEE_EMAIL, ALLOCATION_EMPLOYEE_EMAIL + MISSING),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenEmployeeEmailExceedsLength() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () ->
                victim.validate(
                    newCreateAllocationDto().employeeEmail(rightPad("X", ALLOCATION_EMPLOYEE_EMAIL_MAX_LENGTH + 1, "z"))
                )
        );

        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_EMPLOYEE_EMAIL, ALLOCATION_EMPLOYEE_EMAIL + EXCEEDS_MAX_LENGTH),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenStartAtIsMissing() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateAllocationDto().startAt(null))
        );

        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_START_AT, ALLOCATION_START_AT + MISSING),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenDateOrdering() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () ->
                victim.validate(
                    newCreateAllocationDto().startAt(now().plusDays(1)).endAt(now().plusMinutes(1).minusSeconds(20))
                )
        );
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_START_AT, ALLOCATION_START_AT + INCORRECT_DATES),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenEndAtIsMissing() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateAllocationDto().endAt(null))
        );

        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_END_AT, ALLOCATION_END_AT + MISSING),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenStartAtInThePast() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateAllocationDto().startAt(now().minusHours(1)).endAt(now().plusMinutes(10)))
        );

        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_START_AT, ALLOCATION_START_AT + IN_THE_PAST),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateInvalidDateDuration() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () ->
                victim.validate(
                    newCreateAllocationDto().startAt(now().plusDays(1)).endAt(now().plusDays(1).plusHours(5))
                )
        );

        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_END_AT, ALLOCATION_END_AT + ALLOCATION_EXCEEDS_DURATION),
            exception.getValidationErrors().getError(0)
        );
    }

    @Test
    void testValidateValidDateDuration() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () ->
                victim.validate(
                    newCreateAllocationDto()
                        .subject(ALLOCATION_SUBJECT)
                        .startAt(now().plusDays(1))
                        .endAt(now().plusDays(1).plusHours(5))
                )
        );

        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
    }

    @Test
    void testValidateAllocationTimeIsAvailable() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () ->
                victim.validate(
                    newCreateAllocationDto().startAt(now().plusDays(1)).endAt(now().plusDays(1).plusHours(5))
                )
        );

        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(
            new ValidationError(ALLOCATION_END_AT, ALLOCATION_END_AT + ALLOCATION_EXCEEDS_DURATION),
            exception.getValidationErrors().getError(0)
        );
    }
}
