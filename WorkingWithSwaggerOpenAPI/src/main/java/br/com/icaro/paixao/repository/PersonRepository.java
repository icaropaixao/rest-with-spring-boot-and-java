package br.com.icaro.paixao.repository;

import br.com.icaro.paixao.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {


}
