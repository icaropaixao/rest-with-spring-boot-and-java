package br.com.icaro.paixao.controllers;


import br.com.icaro.paixao.services.PersonServices;
import br.com.icaro.paixao.model.Person;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
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
    public List<Person> findAll() {
        return personServices.findAll();

    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findById(@PathVariable("id") Long id) {
        return personServices.findById(id);

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
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
       personServices.delete(id);
       return ResponseEntity.noContent().build();

    }





}
