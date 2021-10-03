package br.com.sw2you.realmeet.validator;

public class ValidatorConstants {
    public static final String ROOM_ID = "id";
    public static final int ROOM_NAME_MAX_LENGTH = 20;
    public static final String ROOM_NAME = "name";
    public static final String ROOM_SEATS = "seats";
    public static final int ROOM_SEATS_MIN_VALUE = 1;
    public static final int ROOM_SEATS_MAX_VALUE = 20;
    public static final String MISSING = ".missing";
    public static final String BELOW_MIN_VALUE = ".belowMinValue";
    public static final String EXCEEDS_MAX_LENGTH = ".exceedsMaxLength";
    public static final String EXCEEDS_MAX_VALUE = ".exceedsMaxValue";
    public static final String MIN_VALUE = ".minValue";
    public static final String MAX_VALUE = ".maxValue";
    public static final String EXISTS_IN_DB = ".existsInDb";
    public static final String ALLOCATION_SUBJECT = "subject";
    public static final int ALLOCATION_SUBJECT_MAX_LENGTH = 60;
    public static final String ALLOCATION_EMPLOYEE_NAME = "employeeName";
    public static final int ALLOCATION_EMPLOYEE_NAME_MAX_LENGTH = 20;
    public static final String ALLOCATION_EMPLOYEE_EMAIL = "employeeEmail";
    public static final int ALLOCATION_EMPLOYEE_EMAIL_MAX_LENGTH = 30;
    public static final String ALLOCATION_START_AT = "start_At";
    public static final String ALLOCATION_END_AT = "end_At";
    public static final String INCORRECT_DATES = ".incorrectDateInterval";
    public static final String IN_THE_PAST = ".dateInPast";
    public static final int ALLOCATION_MAX_DURATION = 4 * 60 * 60; //4 horas
    public static final String ALLOCATION_EXCEEDS_DURATION = ".exceedsDuration";

    private ValidatorConstants() {}
}
