package br.com.icaro.paixao;

import br.com.icaro.paixao.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;


@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public List<Person> findAll() {

        logger.info("Finding All Persons ");

        List<Person> persons = new ArrayList<Person>();

        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);

            persons.add(person);
        }

        return persons;
    }

    public Person findById(String id) {
        logger.info("Finding person by id: " + id);

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Ícaro");
        person.setLastName("Paixão");
        person.setAddress("Betim - Minas Gerais - Brasil");
        person.setGender("Male");

        return person;

    }

    public Person create(Person person) {
        logger.info("Create One person: " + person);


        return person;
    }

    public Person update(Person person) {
        logger.info("Updating One person: " + person);


        return person;
    }

    public void delete(String id) {
        logger.info("deleting One person: ");

    }


    private Person mockPerson(int i) {

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("FirstName " + i);
        person.setLastName("LastName " + i);
        person.setAddress("Some address in Brasil ");
        person.setGender("Male");

        return person;


    }

}
