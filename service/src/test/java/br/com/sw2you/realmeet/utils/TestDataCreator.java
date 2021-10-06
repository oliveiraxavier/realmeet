package br.com.sw2you.realmeet.utils;

import static br.com.sw2you.realmeet.utils.TestConstants.*;

import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.domain.entity.Room;
import br.com.sw2you.realmeet.domain.model.Employee;

public class TestDataCreator {

    private TestDataCreator() {}

    public static Room.Builder newRoomBuilder() {
        return Room.newBuilder().name(DEFAULT_ROOM_NAME).seats(DEFAULT_ROOM_SEATS);
    }

    public static Allocation.Builder newAllocationBuilder(Room room) {
        return Allocation
            .newBuilder()
            .subject(DEFAULT_ALLOCATION_SUBJECT)
            .room(room)
            .employee(Employee.newBuilder().name(DEFAULT_EMPLOYEER_NAME).email(DEFAULT_EMPLOYEER_EMAIL).build())
            .startAt(DEFAULT_ALLOCATION_START_AT)
            .endAt(DEFAULT_ALLOCATION_END_AT);
    }

    public static CreateRoomDTO newCreateRoomDto() {
        return (CreateRoomDTO) new CreateRoomDTO().name(DEFAULT_ROOM_NAME).seats(DEFAULT_ROOM_SEATS);
    }

    public static CreateAllocationDTO newCreateAllocationDto() {
        return new CreateAllocationDTO()
            .subject(DEFAULT_ALLOCATION_SUBJECT)
            .roomId(DEFAULT_ROOM_ID)
            .employeeName(DEFAULT_EMPLOYEER_NAME)
            .employeeEmail(DEFAULT_EMPLOYEER_EMAIL)
            .startAt(DEFAULT_ALLOCATION_START_AT)
            .endAt(DEFAULT_ALLOCATION_END_AT);
    }
}
