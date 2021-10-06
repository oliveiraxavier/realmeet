package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.util.DateUtils.now;
import static br.com.sw2you.realmeet.utils.TestDataCreator.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.sw2you.realmeet.api.facade.AllocationApi;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

class AllocationApiIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private AllocationApi api;

    @Autowired
    public RoomRepository roomRepository;

    @Autowired
    public AllocationRepository allocationRepository;

    @Override
    protected void setupEach() throws Exception {
        setLocalHostBasePath(api.getApiClient(), "/v1");
    }

    @Test
    void testCreateAllocationSuccess() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        var createAllocationDto = newCreateAllocationDto().roomId(room.getId());
        var allocationDTO = api.createAllocation(createAllocationDto);
        assertEquals(room.getId(), allocationDTO.getRoomId());

        assertEquals(createAllocationDto.getSubject(), allocationDTO.getSubject());
        assertEquals(createAllocationDto.getEmployeeName(), allocationDTO.getEmployeeName());
        assertEquals(createAllocationDto.getEmployeeEmail(), allocationDTO.getEmployeeEmail());
        assertTrue(createAllocationDto.getStartAt().isEqual(allocationDTO.getStartAt()));
        assertTrue(createAllocationDto.getEndAt().isEqual(allocationDTO.getEndAt()));
    }

    @Test
    void testCreateAllocationWithValidationError() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        var createAllocationDto = newCreateAllocationDto().roomId(room.getId()).subject(null);
        //var allocationDTO = api.createAllocation(createAllocationDto.subject(null));

        assertThrows(
            HttpClientErrorException.UnprocessableEntity.class,
            () -> api.createAllocation(createAllocationDto)
        );
    }

    @Test
    void testCreateAllocationWhenRoomNotExists() {
        assertThrows(HttpClientErrorException.NotFound.class, () -> api.createAllocation(newCreateAllocationDto()));
    }

    @Test
    void testDeleteAllocationSuccess() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        var allocation = allocationRepository.saveAndFlush(newAllocationBuilder(room).build());

        api.deleteAllocation(allocation.getId());

        assertFalse(allocationRepository.findById(allocation.getId()).isPresent());
    }

    @Test
    void testDeleteAllocationInThePast() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        var allocation = allocationRepository.saveAndFlush(
            newAllocationBuilder(room).startAt(now().minusDays(1)).endAt(now().minusDays(1).plusHours(1)).build()
        );

        assertThrows(
            HttpClientErrorException.UnprocessableEntity.class,
            () -> api.deleteAllocation(allocation.getId())
        );
    }

    @Test
    void testDeleteAllocationDoesExists() {
        assertThrows(HttpClientErrorException.NotFound.class, () -> api.deleteAllocation(1L));
    }
}
