package br.com.icaro.paixao.services;

import br.com.icaro.paixao.data.dto.PersonDTO;
import br.com.icaro.paixao.exception.ResourceNorFoundException;
import static br.com.icaro.paixao.mapper.ObjectMapper.parseListObjects;
import static br.com.icaro.paixao.mapper.ObjectMapper.parseObject;
import br.com.icaro.paixao.model.Person;
import br.com.icaro.paixao.repository.PersonRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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


    public List<PersonDTO> findAll() {
        logger.info("Finding All Persons ");

        return parseListObjects(personRepository.findAll(), PersonDTO.class);

    }

    public PersonDTO findById(Long id) {
        logger.info("Finding person by id: " + id);

        var entity = personRepository.findById(id)
                .orElseThrow(()-> new ResourceNorFoundException("No records found for this ID"));

        return parseObject(entity, PersonDTO.class); // trasnformando em DTO

    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Create One person: " + person);

        var entity = parseObject(person, Person.class);

        return parseObject(personRepository.save(entity), PersonDTO.class);


    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Updating One person: " + person);

        Person entity = personRepository.findById(person.getId())
                .orElseThrow(()-> new ResourceNorFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("deleting One person: ");

        Person entity = personRepository.findById(id)
                .orElseThrow(()-> new ResourceNorFoundException("No records found for this ID"));

        personRepository.delete(entity);
    }


}


