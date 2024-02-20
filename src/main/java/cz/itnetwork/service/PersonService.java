package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;
import cz.itnetwork.dto.PersonsPageDTO;
import cz.itnetwork.entity.filter.PersonFilter;

import java.util.List;

public interface PersonService {

    /**
     * Creates a new person.
     *
     * @param personDTO Person to create
     * @return New created person
     */
    PersonDTO addPerson(PersonDTO personDTO);

    /**
     * <p>Sets hidden flag to true for the person with the matching [id].</p>
     * <p>In case a person with the passed [id] isn't found, the method <b>silently fails.</b></p>
     *
     * @param id Person to delete
     */
    void removePerson(long id);

    /**
     * <p>Fetches one page non-hidden people. Uses the settings according to the data in the person Filter.</p>
     *
     * @param personFilter Data for filtering, sorting, limit for number of people, number of requested page
     * @return One sorted page of non-hidden people and information about the available number of people in
     * the database and the limit of the number of invoices per page
     */
    PersonsPageDTO getPage(PersonFilter personFilter);

    /**
     * Fetches all non-hidden people.
     *
     * @return List of all non-hidden people
     */
    List<PersonDTO> getPeople();

    /**
     * <p>Returns one person with the matching [personId].</p>
     * <p>In case a person with the passed [personId] isn't found, the method <b>throws an exception 404.</b></p>
     *
     * @param personId Person to find
     * @return One person
     */
    PersonDTO getPerson(long personId);

    /**
     * <p>Updates the data of the existing user in the database with the matching [personId].</p>
     * <p>In case a person with the passed [personId] isn't found, the method <b>throws an exception 404.</b></p>
     *
     * @param personId  Person to edit
     * @param personDTO Entered data to edit person
     * @return Data of the modified person
     */
    PersonDTO editPerson(long personId, PersonDTO personDTO);

    /**
     * Returns statistic of all persons for the last year and all time.
     *
     * @return List of statistics
     */
    List<PersonStatisticDTO> PersonStatistics();
}
