package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.utils.TestDataCreator.newCreateRoomDto;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newRoomBuilder;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.sw2you.realmeet.api.facade.RoomApi;
import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

class RoomApiIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private RoomApi api;

    @Autowired
    public RoomRepository roomRepository;

    @Override
    protected void setupEach() throws Exception {
        setLocalHostBasePath(api.getApiClient(), "/v1");
    }

    @Test
    void testGetRoomSuccess() {
        var room = newRoomBuilder().build();
        roomRepository.saveAndFlush(room);

        assertNotNull(room.getId());
        assertTrue(room.getActive());

        var dto = api.getRoom(room.getId());
        assertEquals(room.getId(), dto.getId());
        assertEquals(room.getName(), dto.getName());
        assertEquals(room.getSeats(), dto.getSeats());
    }

    @Test
    void testGetRoomInactive() {
        var room = newRoomBuilder().active(false).build();
        roomRepository.saveAndFlush(room);

        assertFalse(room.getActive());
        assertThrows(HttpClientErrorException.NotFound.class, () -> api.getRoom(room.getId()));
    }

    @Test
    void testCreateRoomSuccess() {
        var createRoomDto = newCreateRoomDto();
        var roomDto = api.createRoom(createRoomDto);

        assertEquals(createRoomDto.getName(), roomDto.getName());
        assertEquals(createRoomDto.getSeats(), roomDto.getSeats());
        assertNotNull(roomDto.getId());

        var room = roomRepository.findById(roomDto.getId()).orElseThrow();

        assertEquals(room.getName(), roomDto.getName());
        assertEquals(room.getSeats(), roomDto.getSeats());
    }

    @Test
    void testCreateRoomValidationError() {
        assertThrows(
            HttpClientErrorException.UnprocessableEntity.class,
            () -> api.createRoom((CreateRoomDTO) newCreateRoomDto().name(null).seats(201))
        );
    }

    @Test
    void testDeleteRoomSuccess() {
        var roomId = roomRepository.saveAndFlush(newRoomBuilder().build()).getId();
        api.deleteRoom(roomId);

        assertFalse(roomRepository.findById(roomId).orElseThrow().getActive());
    }

    @Test
    void testDeleteRoomNotFound() {
        assertThrows(HttpClientErrorException.NotFound.class, () -> api.deleteRoom(1L));
    }

    @Test
    void testUpdateRoom() {
        var roomId = roomRepository.saveAndFlush(newRoomBuilder().build());

        var updateRoomDto = new UpdateRoomDTO().name(roomId.getName() + "_").seats(roomId.getSeats() + 1);

        api.updateRoom(roomId.getId(), updateRoomDto);

        var updatedRoom = roomRepository.findById(roomId.getId()).orElseThrow();
        assertEquals(updateRoomDto.getName(), updatedRoom.getName());
        assertEquals(updateRoomDto.getSeats(), updatedRoom.getSeats());
    }

    @Test
    void testUpdateRoomNotExists() {
        assertThrows(
            HttpClientErrorException.NotFound.class,
            () -> api.updateRoom(1L, new UpdateRoomDTO().name("Room").seats(10))
        );
    }

    @Test
    void testUpdateRoomValidationError() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        assertThrows(
            HttpClientErrorException.UnprocessableEntity.class,
            () -> api.updateRoom(room.getId(), new UpdateRoomDTO().name(null).seats(534))
        );
    }
}
