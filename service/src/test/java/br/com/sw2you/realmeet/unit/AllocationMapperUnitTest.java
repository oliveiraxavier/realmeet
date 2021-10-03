package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.utils.MapperUtils.allocationMapper;
import static br.com.sw2you.realmeet.utils.TestDataCreator.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.sw2you.realmeet.core.BaseUnitTest;
import br.com.sw2you.realmeet.mapper.AllocationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AllocationMapperUnitTest extends BaseUnitTest {
    private AllocationMapper victim;

    @BeforeEach
    void setupEach() {
        victim = allocationMapper();
    }

    @Test
    void testFromCreateAllocationDtoToEntity() {
        var createAllocationDto = newCreateAllocationDto();
        var allocation = victim.fromCreateAllocationDtoToEntity(createAllocationDto, newRoomBuilder().build());

        assertEquals(createAllocationDto.getSubject(), allocation.getSubject());
        assertNull(allocation.getRoom().getId());
        assertEquals(createAllocationDto.getEmployeeName(), allocation.getEmployee().getName());
        assertEquals(createAllocationDto.getEmployeeEmail(), allocation.getEmployee().getEmail());
        assertEquals(createAllocationDto.getStartAt(), allocation.getStartAt());
        assertEquals(createAllocationDto.getEndAt(), allocation.getEndAt());
    }

    @Test
    void testFromEntityToAllocationDto() {
        var allocation = newAllocationBuilder(newRoomBuilder().id(1L).build()).build();
        var allocationDTO = victim.fromEntityToAllocationDto(allocation);

        assertEquals(allocationDTO.getSubject(), allocation.getSubject());
        assertEquals(allocationDTO.getId(), allocation.getId());
        assertNotNull(allocationDTO.getRoomId());
        assertEquals(allocationDTO.getRoomId(), allocation.getRoom().getId());
        assertEquals(allocationDTO.getEmployeeName(), allocation.getEmployee().getName());
        assertEquals(allocationDTO.getEmployeeEmail(), allocation.getEmployee().getEmail());
        assertEquals(allocationDTO.getStartAt(), allocation.getStartAt());
        assertEquals(allocationDTO.getEndAt(), allocation.getEndAt());
    }
}
