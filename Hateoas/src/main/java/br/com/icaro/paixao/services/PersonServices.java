package br.com.icaro.paixao.services;

import br.com.icaro.paixao.controllers.PersonController;
import br.com.icaro.paixao.data.dto.v1.PersonDTO;
import br.com.icaro.paixao.data.dto.v2.PersonDTOV2;
import br.com.icaro.paixao.exception.ResourceNorFoundException;
import static br.com.icaro.paixao.mapper.ObjectMapper.parseListObjects;
import static br.com.icaro.paixao.mapper.ObjectMapper.parseObject;

import br.com.icaro.paixao.mapper.custom.PersonMapper;
import br.com.icaro.paixao.model.Person;
import br.com.icaro.paixao.repository.PersonRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;


@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    // INJECTION
    private final PersonRepository personRepository;
    private final PersonMapper converter;
    public PersonServices(PersonRepository personRepository, PersonMapper converter) {
        this.personRepository = personRepository;
        this.converter = converter;

    }

    public List<PersonDTO> findAll() {
        logger.info("Finding All Persons ");

        return parseListObjects(personRepository.findAll(), PersonDTO.class);

    }

    public PersonDTO findById(Long id) {
        logger.info("Finding person by id: " + id);

        var entity = personRepository.findById(id)
                .orElseThrow(()-> new ResourceNorFoundException("No records found for this ID"));

        var dto = parseObject(entity, PersonDTO.class); // trasnformando em DTO

        // LINK HATEOAS REFERENCIANDO AO FINDBYID
        dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel().withType("GET"));



        return dto;
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Create One person: " + person);

        var entity = parseObject(person, Person.class);

        return parseObject(personRepository.save(entity), PersonDTO.class);


    }

    public PersonDTOV2 createV2(PersonDTOV2 person) {
        logger.info("Create One person V2: " + person);

        var entity = converter.convertDTOToEntity(person);

        return converter.convertEntityToDTO(personRepository.save(entity));


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


