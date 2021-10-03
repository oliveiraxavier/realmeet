package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.utils.TestDataCreator.newCreateAllocationDto;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newRoomBuilder;
import static org.junit.jupiter.api.Assertions.*;

import br.com.sw2you.realmeet.api.facade.AllocattionApi;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

class AllocationApiIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private AllocattionApi api;

    @Autowired
    public RoomRepository roomRepository;

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
}
