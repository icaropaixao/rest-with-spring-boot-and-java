package br.com.icaro.paixao.services;

import br.com.icaro.paixao.controllers.PersonController;
import br.com.icaro.paixao.data.dto.v1.PersonDTO;
import br.com.icaro.paixao.data.dto.v2.PersonDTOV2;
import br.com.icaro.paixao.exception.RequiredObjectIsNullException;
import br.com.icaro.paixao.exception.ResourceNotFoundException;
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
import org.slf4j.Logger;


@Service
public class PersonServices {

    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    // INJECTION
    private final PersonRepository personRepository;
    private final PersonMapper converter;

    public PersonServices(PersonRepository personRepository, PersonMapper converter) {
        this.personRepository = personRepository;
        this.converter = converter;

    }

    public List<PersonDTO> findAll() {

        // Registra no log que vai buscar todas as pessoas.
        logger.info("Finding All Persons ");

        // Busca todas as pessoas no banco e converte a lista para PersonDTO.
        var persons = parseListObjects(personRepository.findAll(), PersonDTO.class);

        // Adiciona os links HATEOAS em cada DTO da lista.
        persons.forEach(this::addHateoasLinks);

        // Retorna a lista final.
        return persons;
    }


    public PersonDTO findById(Long id) {
        logger.info("Finding person by id: " + id);

        var entity = personRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));

        var dto = parseObject(entity, PersonDTO.class); // trasnformando em DTO

        // LINK HATEOAS REFERENCIANDO AO FINDBYID

        addHateoasLinks(dto);

        return dto;
    }

    public PersonDTO create(PersonDTO person) {

        if (person == null) throw new RequiredObjectIsNullException();


        logger.info("Create One person: " + person);

        var entity = parseObject(person, Person.class);

        var dto = parseObject(personRepository.save(entity), PersonDTO.class);

        addHateoasLinks(dto);
        return dto;

    }

    public PersonDTOV2 createV2(PersonDTOV2 person) {
        logger.info("Create One person V2: " + person);

        var entity = converter.convertDTOToEntity(person);

        return converter.convertEntityToDTO(personRepository.save(entity));


    }

    public PersonDTO update(PersonDTO person) {

        if (person == null) throw new RequiredObjectIsNullException();


        logger.info("Updating One person: " + person);

        Person entity = personRepository.findById(person.getId())
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(personRepository.save(entity), PersonDTO.class);

        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("deleting One person: ");

        Person entity = personRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));

        personRepository.delete(entity);


    }


    // LINKs HATEOAS para os métodos
    private void  addHateoasLinks(PersonDTO dto) {
         dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
         dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("delete").withType("GET"));
         dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
         dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
         dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));

    }

}


