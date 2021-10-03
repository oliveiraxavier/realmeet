package br.com.sw2you.realmeet.mapper;

import br.com.sw2you.realmeet.api.model.AllocationDTO;
import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.domain.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class AllocationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "room", target = "room")
    @Mapping(source = "createAllocationDTO.employeeName", target = "employee.name")
    @Mapping(source = "createAllocationDTO.employeeEmail", target = "employee.email")
    public abstract Allocation fromCreateAllocationDtoToEntity(CreateAllocationDTO createAllocationDTO, Room room);

    @Mapping(source = "employee.name", target = "employeeName")
    @Mapping(source = "employee.email", target = "employeeEmail")
    @Mapping(source = "room.id", target = "roomId")
    public abstract AllocationDTO fromEntityToAllocationDto(Allocation allocation);
}
