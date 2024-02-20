package cz.itnetwork.controller;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;
import cz.itnetwork.dto.PersonsPageDTO;
import cz.itnetwork.entity.filter.PersonFilter;
import cz.itnetwork.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/people")
    public List<PersonDTO> getPeople() {
        return personService.getPeople();
    }

    @PostMapping("/people")
    public PersonDTO addPerson(@RequestBody @Valid PersonDTO personDTO) {
        return personService.addPerson(personDTO);
    }

    @GetMapping("/people/page")
    public PersonsPageDTO getPeoplePage(PersonFilter personFilter) {
        return personService.getPage(personFilter);
    }

    @DeleteMapping("/people/{personId}")
    public void deletePerson(@PathVariable Long personId) {
        personService.removePerson(personId);
    }

    @GetMapping("/people/{personId}")
    public PersonDTO getPerson(@PathVariable Long personId) {
        return personService.getPerson(personId);
    }

    @PutMapping("/people/{personId}")
    public PersonDTO editPerson(@PathVariable Long personId, @RequestBody @Valid PersonDTO personDTO) {
        return personService.editPerson(personId, personDTO);
    }

    @GetMapping("/people/statistics")
    public List<PersonStatisticDTO> personStatistics() {
        return personService.PersonStatistics();
    }

    @GetMapping("/people/sort")
    public PersonsPageDTO changeSorting(PersonFilter personFilter) {
        return personService.getPage(personFilter);
    }
}
