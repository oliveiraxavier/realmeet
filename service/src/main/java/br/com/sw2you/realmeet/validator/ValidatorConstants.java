package br.com.sw2you.realmeet.validator;

public class ValidatorConstants {
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

    private ValidatorConstants() {}
}
