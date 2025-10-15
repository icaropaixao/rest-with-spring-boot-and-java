package br.com.icaro.paixao.services;

import br.com.icaro.paixao.exception.ResourceNorFoundException;
import br.com.icaro.paixao.model.Person;
import br.com.icaro.paixao.repository.PersonRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;


@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    // INJECTION
    private final PersonRepository personRepository;
    public PersonServices(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public List<Person> findAll() {
        logger.info("Finding All Persons ");

        return personRepository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding person by id: " + id);

        return personRepository.findById(id).orElseThrow(()-> new ResourceNorFoundException("No records found for this ID"));

    }

    public Person create(Person person) {
        logger.info("Create One person: " + person);


        return personRepository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating One person: " + person);

        Person entity = personRepository.findById(person.getId()).orElseThrow(()-> new ResourceNorFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(person);
    }

    public void delete(Long id) {
        logger.info("deleting One person: ");

        Person entity = personRepository.findById(id)
                .orElseThrow(()-> new ResourceNorFoundException("No records found for this ID"));

        personRepository.delete(entity);
    }


}


