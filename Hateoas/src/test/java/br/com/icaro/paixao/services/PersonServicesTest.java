package br.com.icaro.paixao.services;

import br.com.icaro.paixao.data.dto.v1.PersonDTO;
//import br.com.icaro.paixao.exception.RequiredObjectIsNullException;

import br.com.icaro.paixao.exception.RequiredObjectIsNullException;
import br.com.icaro.paixao.model.Person;
import br.com.icaro.paixao.repository.PersonRepository;
import br.com.icaro.paixao.unitetests.mapper.mocks.MockPerson;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


// Diz ao JUnit para usar a extensão do Mockito,
// que habilita os mocks automaticamente
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    // Classe auxiliar usada para gerar objetos mockados de teste
    MockPerson input;

    // Injeta automaticamente os mocks dentro da classe de service
    // Isso faz com que o repository falso seja inserido no service
    @InjectMocks
    private PersonServices service;

    // Cria um mock (objeto falso) do repositório
    // Nenhum acesso real ao banco acontece
    @Mock
    PersonRepository repository;

    // Executa antes de CADA teste
    // Aqui estamos inicializando o gerador de objetos fake
    @BeforeEach
    void setUp() {
        input = new MockPerson();
    }

    // ================= TESTE DE BUSCA POR ID =================
    @Test
    void findById() {

        // Cria uma entidade Person falsa
        Person person = input.mockEntity(1);

        // Define manualmente o ID
        person.setId(1L);

        // Quando o repository for chamado com findById(1),
        // ele deve retornar a pessoa criada acima
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        // Chama o service de verdade (já com o mock configurado)
        var result = service.findById(1L);

        // ================= VERIFICAÇÕES =================

        // Garante que o resultado não é nulo
        assertNotNull(result);

        // Garante que o ID veio preenchido
        assertNotNull(result.getId());

        // Garante que os links HATEOAS foram gerados
        assertNotNull(result.getLinks());

        // Verifica se existe o link "self" com o GET correto
        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("GET")
                ));

        // Verifica se existe o link para buscar todos
        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("findAll") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("GET")
                )
        );

        // Verifica se o link de criação existe
        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("create") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("POST")
                )
        );

        // Verifica se o link de atualização existe
        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("update") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("PUT")
                )
        );

        // Verifica se o link de exclusão existe
        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("delete") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("DELETE")
                )
        );

        // Verifica se os dados vieram corretos
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    // ================= TESTE DE CREATE =================

    @Test
    void create() {

        // Cria uma entidade falsa
        Person person = input.mockEntity(1);

        // Cria um "persistido" simulando o que o banco devolveria
        Person persisted = person;
        persisted.setId(1L);

        // Cria um DTO falso para entrada do service
        PersonDTO dto = input.mockDTO(1);

        // Configura o mock para simular o save do repository
        when(repository.save(person)).thenReturn(persisted);

        // Executa o metodo real do service
        var result = service.create(dto);

        // ================= VERIFICAÇÕES =================

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        // Verificações dos links HATEOAS (self, findAll, create, update, delete)
        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("findAll") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("create") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("update") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("delete") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("DELETE")
                )
        );

        // Verifica os dados
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }


    @Test
    void testCreateWithNullPerson() {

        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.create(null);
                });

        String expectedMessage = "It is not allowed to persisted a null object!";
        String actualMessage = exception.getMessage();


        assertTrue(actualMessage.contains(expectedMessage));

    }

    // ================= TESTE DE UPDATE =================

    @Test
    void update() {

        // Cria entidade falsa
        Person person = input.mockEntity(1);

        // Simula entidade já salva
        Person persisted = person;
        persisted.setId(1L);

        // DTO de entrada
        PersonDTO dto = input.mockDTO(1);

        // Mocka o findById e o save do repository
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(person)).thenReturn(persisted);

        // Chama o método real
        var result = service.update(dto);

        // ================= VERIFICAÇÕES =================
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        // Verificações de links HATEOAS
        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("findAll") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("create") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("update") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("delete") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("DELETE")
                )
        );

        // Verifica os dados
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdateWithNullPerson() {

        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.update(null);
                });

        String expectedMessage = "It is not allowed to persisted a null object!";
        String actualMessage = exception.getMessage();


        assertTrue(actualMessage.contains(expectedMessage));

    }

    // ================= TESTE DE DELETE =================
    @Test
    void delete() {

        // Cria uma pessoa falsa
        Person person = input.mockEntity(1);
        person.setId(1L);

        // Configura o mock para devolver a pessoa quando buscar pelo ID
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        // Executa o método delete do service
        service.delete(1L);

        // Verifica se o repository foi chamado corretamente
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Person.class));

        // Garante que não houve mais chamadas além das verificadas
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {


        List<Person> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        List<PersonDTO> people = service.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());


        // ================= TESTE DA PESSOA ONE=================


        var personOne = people.get(1);
        assertNotNull(personOne);
        assertNotNull(personOne.getId());
        assertNotNull(personOne.getLinks());

        // Verificações de links HATEOAS
        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("GET")
                ));

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("findAll") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("GET")
                )
        );

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("create") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("POST")
                )
        );

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("update") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("PUT")
                )
        );

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("delete") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("DELETE")
                )
        );

        // Verifica os dados
        assertEquals("Address Test1", personOne.getAddress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Last Name Test1", personOne.getLastName());
        assertEquals("Female", personOne.getGender());


        // ================= TESTE DA PESSOA 4 =================


        var personFour = people.get(4);

        assertNotNull(personFour);
        assertNotNull(personFour.getId());
        assertNotNull(personFour.getLinks());

        // Verificações de links HATEOAS
        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/person/v1/4") &&
                                link.getType().equals("GET")
                ));

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("findAll") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("GET")
                )
        );

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("create") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("POST")
                )
        );

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("update") &&
                                link.getHref().endsWith("/api/person/v1") &&
                                link.getType().equals("PUT")
                )
        );

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("delete") &&
                                link.getHref().endsWith("/api/person/v1/4") &&
                                link.getType().equals("DELETE")
                )
        );

        // Verifica os dados
        assertEquals("Address Test4", personFour.getAddress());
        assertEquals("First Name Test4", personFour.getFirstName());
        assertEquals("Last Name Test4", personFour.getLastName());
        assertEquals("Male", personFour.getGender());
    }

}


