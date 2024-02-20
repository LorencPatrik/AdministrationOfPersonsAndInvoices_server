package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonsPageDTO;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.service.model.PersonStatisticAllTime;
import cz.itnetwork.dto.PersonStatisticDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonEntity toEntity(PersonDTO source);

    void updatePersonEntity(PersonDTO source, @MappingTarget PersonEntity target);

    PersonDTO toDTO(PersonEntity source);

    List<PersonDTO> toDTO(List<PersonEntity> source);

    List<PersonStatisticDTO> toDTOStatisticList(List<PersonStatisticAllTime> source);

    /**
     * <p>Transfers the persons List to another DTO and adds data about the available number of persons in the
     * database and the limit of the number of persons per page.</p>
     *
     * @param pageEntities One sorted page of persons
     * @param limit        Limit of the number of persons per page
     * @return One sorted page of persons and information about the available number of persons and the limit
     * of the number of persons per page
     */
    default PersonsPageDTO mapToDTO(Page<PersonEntity> pageEntities, int limit) {
        PersonsPageDTO personsPageDTO = new PersonsPageDTO();
        personsPageDTO.setPersons(toDTO(pageEntities.getContent()));
        personsPageDTO.setCount(pageEntities.getTotalElements());
        personsPageDTO.setLimit(limit);
        return personsPageDTO;
    }
}
