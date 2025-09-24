package br.com.icaro.paixao.controllers;


import br.com.icaro.paixao.PersonServices;
import br.com.icaro.paixao.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {


    // @Autowired
    // private PersonServices personServices; ANTIGO
    private final PersonServices personServices;

    // o Spring injeta automaticamente a dependência pelo construtor
    public PersonController(PersonServices personServices) {
        this.personServices = personServices;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findById(@PathVariable("id") String id) {
        return personServices.findById(id);

    }

    @GetMapping()
    public List<Person> findAll() {
        return personServices.findAll();

    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Person create(@RequestBody Person person) {

        return personServices.create(person);

    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Person update(@RequestBody Person person) {

        return personServices.update(person);

    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") String id) {
       personServices.delete(id);

    }





}
