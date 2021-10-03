package br.com.sw2you.realmeet.utils;

import br.com.sw2you.realmeet.util.DateUtils;
import java.time.OffsetDateTime;

public class TestConstants {
    public static final long DEFAULT_ROOM_ID = 1L;
    public static final String DEFAULT_ROOM_NAME = "Room A";
    public static final int DEFAULT_ROOM_SEATS = 6;
    public static final String DEFAULT_ALLOCATION_SUBJECT = "Some subject";
    public static final String DEFAULT_EMPLOYEER_NAME = "User Name";
    public static final String DEFAULT_EMPLOYEER_EMAIL = "username@mail.com";
    public static final OffsetDateTime DEFAULT_ALLOCATION_START_AT = DateUtils.now().plusDays(1);
    public static final OffsetDateTime DEFAULT_ALLOCATION_END_AT = DEFAULT_ALLOCATION_START_AT.plusHours(1);

    private TestConstants() {}
}
