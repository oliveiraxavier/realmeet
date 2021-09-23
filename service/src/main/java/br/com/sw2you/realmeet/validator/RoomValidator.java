package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.domain.entity.repository.RoomRepository;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {
    private final RoomRepository roomRepository;

    public RoomValidator(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void validate(CreateRoomDTO createRoomDTO) {
        var validationErrors = new ValidationErrors();

        if (
            validateName(createRoomDTO.getName(), validationErrors) &&
            validateSeats(createRoomDTO.getSeats(), validationErrors)
        ) validateNameDuplicated(createRoomDTO.getName(), validationErrors);

        thrownOnError(validationErrors);
    }

    private boolean validateName(String name, ValidationErrors validationErrors) {
        return (
            validateRequired(name, ROOM_NAME, validationErrors) &&
            validateMaxLength(name, ROOM_NAME, ROOM_NAME_MAX_LENGTH, validationErrors)
        );
    }

    private boolean validateSeats(Integer seats, ValidationErrors validationErrors) {
        return (
            validateRequired(seats, ROOM_SEATS, validationErrors) &&
            validateMinValue(seats, ROOM_SEATS, ROOM_SEATS_MIN_VALUE, validationErrors) &&
            validateMaxValue(seats, ROOM_SEATS, ROOM_SEATS_MAX_VALUE, validationErrors)
        );
    }

    private void validateNameDuplicated(String name, ValidationErrors validationErrors) {
        roomRepository
            .findByNameAndActive(name, true)
            .ifPresent(__ -> validationErrors.add(ROOM_NAME, ROOM_NAME + EXISTS_IN_DB));
    }
}
