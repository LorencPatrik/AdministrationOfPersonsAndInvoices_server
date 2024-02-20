package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;
import cz.itnetwork.dto.PersonsPageDTO;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.filter.PersonFilter;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.specification.PersonSpecification;
import cz.itnetwork.service.model.PersonStatisticAllTime;
import cz.itnetwork.service.model.PersonStatisticLastYear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonMapper personMapper;

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonMapper personMapper,
                             PersonRepository personRepository) {
        this.personMapper = personMapper;
        this.personRepository = personRepository;
    }

    public PersonDTO addPerson(PersonDTO personDTO) {
        final PersonEntity entity = personMapper.toEntity(personDTO);
        return personMapper.toDTO(personRepository.save(entity));
    }

    @Override
    public void removePerson(long personId) {
        try {
            final PersonEntity person = fetchPersonById(personId);
            person.setHidden(true);
            personRepository.save(person);
        } catch (NotFoundException ignored) {
            // The contract in the interface states, that no exception is thrown, if the entity is not found.
        }
    }

    @Override
    public PersonsPageDTO getPage(PersonFilter personFilter) {
        final Page<PersonEntity> pageEntities = getPageEntities(personFilter);
        return personMapper.mapToDTO(pageEntities, personFilter.getLimit());
    }

    @Override
    public List<PersonDTO> getPeople() {
        return personMapper.toDTO(personRepository.findAllByHidden(false));
    }

    @Override
    public PersonDTO getPerson(long personId) {
        final PersonEntity personEntity = fetchPersonById(personId);
        PersonDTO personDTO = personMapper.toDTO(personEntity);
        return personDTO;
    }

    @Override
    public PersonDTO editPerson(long personId, PersonDTO personDTO) {
        final PersonEntity person = fetchPersonById(personId);
        person.setHidden(true);
        personRepository.save(person);
        personDTO.setId(null);
        final PersonEntity editedPerson = new PersonEntity();
        personMapper.updatePersonEntity(personDTO, editedPerson);
        return personMapper.toDTO(personRepository.save(editedPerson));
    }

    @Override
    public List<PersonStatisticDTO> PersonStatistics() {
        List<PersonStatisticAllTime> statisticsAllTime = personRepository.getStatisticsAllTime();
        List<PersonStatisticLastYear> statisticsLastYear = personRepository.getStatisticsLastYear();
        List<PersonStatisticDTO> statisticsAllTimeDTO = personMapper.toDTOStatisticList(statisticsAllTime);
        return addLastYearStatistic(statisticsAllTimeDTO, statisticsLastYear);
    }

    // region: Private methods

    /**
     * <p>Attempt to fetch a person.</p>
     * <p>In case a person with the passed [id] doesn't exist or is hidden a NotFoundException is thrown.</p>
     *
     * @param id Person to fetch
     * @return Fetched entity
     * @throws org.webjars.NotFoundException In case a person with the passed [id] isn't found or is hidden
     */
    private PersonEntity fetchPersonById(long id) {
        PersonEntity personEntitiy = personEntitiy = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
        if (personEntitiy.isHidden()) {
            throw new NotFoundException("Person with id " + id + " is hidden!");
        }
        return personEntitiy;
    }

    /**
     * <p>Sets sorting and checks that the entity page request is in the correct range, otherwise returns the first
     * or last page.</p>
     *
     * @param personFilter Data for filtering, sorting, limit for number of people, number of requested page
     * @return One sorted page of non-hidden people
     */
    private Page<PersonEntity> getPageEntities(PersonFilter personFilter) {
        PersonSpecification personSpecification = new PersonSpecification(personFilter);
        Sort sortBy = Sort.by(personFilter.getNameOfAttribute().toString());
        Sort sortSet = (personFilter.isAscending()) ? sortBy.ascending() : sortBy.descending();
        if (personFilter.getPageNumber() < 0)
            personFilter.setPageNumber(0);
        Pageable pageableSet = PageRequest.of(personFilter.getPageNumber(), personFilter.getLimit(), sortSet);
        final Page<PersonEntity> pageEntities = personRepository.findAll(personSpecification, pageableSet);
        final Page<PersonEntity> controlledPageEntities;
        if (pageEntities.getTotalPages() < (personFilter.getPageNumber() + 1) && pageEntities.getTotalPages() > 0) {
            personFilter.setPageNumber((pageEntities.getTotalPages() - 1));
            Pageable pageableSetLast = PageRequest.of(personFilter.getPageNumber(), personFilter.getLimit(), sortSet);
            controlledPageEntities = personRepository.findAll(personSpecification, pageableSetLast);
        } else
            controlledPageEntities = pageEntities;
        return controlledPageEntities;
    }

    /**
     * <p>Returns individual person statistics for the last year and all time.</p>
     * <p>Adds last year's personal statistics to the all time statistics List.</p>
     *
     * @param statisticsAllTimeDTO List containing statistics of all persons for all time
     * @param statisticsLastYear   List containing statistics of persons who had an income last year
     * @return List of individual person statistics
     */
    private List<PersonStatisticDTO> addLastYearStatistic(List<PersonStatisticDTO> statisticsAllTimeDTO,
                                                          List<PersonStatisticLastYear> statisticsLastYear) {
        Map<Long, PersonStatisticDTO> statisticMapAllTime = new LinkedHashMap<>();
        for (PersonStatisticDTO item : statisticsAllTimeDTO)
            statisticMapAllTime.put(item.getPersonId(), item);
        for (int i = 0; i < statisticsLastYear.size(); i++) {
            PersonStatisticLastYear oneStatisticLastYear = statisticsLastYear.get(i);
            Long id = oneStatisticLastYear.getPersonId();
            PersonStatisticDTO oneAllTimeStatisticDTO = statisticMapAllTime.get(id);
            if (oneAllTimeStatisticDTO != null) {
                oneAllTimeStatisticDTO.setLastYearCount(oneStatisticLastYear.getLastYearCount());
                oneAllTimeStatisticDTO.setLastYearRevenue(oneStatisticLastYear.getLastYearRevenue());
                statisticMapAllTime.put(id, oneAllTimeStatisticDTO);
            }
        }
        return statisticMapAllTime.values().stream().toList();
    }
    // endregion
}
