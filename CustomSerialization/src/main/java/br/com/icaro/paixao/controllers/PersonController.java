package br.com.icaro.paixao.controllers;


import br.com.icaro.paixao.data.dto.v1.PersonDTO;
import br.com.icaro.paixao.data.dto.v2.PersonDTOV2;
import br.com.icaro.paixao.services.PersonServices;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/test/v1")
public class PersonController {

    //JEITO ANTIGO DE INJETAR
    // @Autowired
    // private PersonServices personServices;

    // NOVO
    private final PersonServices personServices;
    public PersonController(PersonServices personServices) {
        this.personServices = personServices;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE) // indicando que a resposta é em formato JSON
    public List<PersonDTO> findAll() {
        return personServices.findAll();

    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO findById(@PathVariable("id") Long id) {

        var person = personServices.findById(id);
        person.setBirthDay(new Date());
        person.setPhoneNumber("+55(31)9725-1772");
        person.setSensitiveData("Foo Bar");
        return person;

    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO create(@RequestBody PersonDTO person) {

        return personServices.create(person);

    }

    // V2 de Create, com data de nascimento inclusa

    @PostMapping(value = "/v2",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTOV2 createV2(@RequestBody PersonDTOV2 person) {

        return personServices.createV2(person);

    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO update(@RequestBody PersonDTO person) {

        return personServices.update(person);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
       personServices.delete(id);
       return ResponseEntity.noContent().build();

    }





}
