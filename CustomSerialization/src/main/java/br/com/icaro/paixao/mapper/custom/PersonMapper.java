package br.com.icaro.paixao.mapper.custom;

import br.com.icaro.paixao.data.dto.v2.PersonDTOV2;
import br.com.icaro.paixao.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {


    // Entidade para DTO
    public PersonDTOV2 convertEntityToDTO(Person person) {

        PersonDTOV2 dto = new PersonDTOV2();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setBirthDate(new Date());
        dto.setAddress(person.getAddress());
        dto.setGender(person.getGender());

        return dto;

    }


    // DTO Para Entidade
    public Person convertDTOToEntity(PersonDTOV2 person) {

        Person entity = new Person();
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        // entity.setBirthDate(new Date());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return entity;

    }

}
