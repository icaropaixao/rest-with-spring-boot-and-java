package br.com.icaro.paixao.services;

import br.com.icaro.paixao.data.dto.v1.BookDTO;
import br.com.icaro.paixao.exception.RequiredObjectIsNullException;
import br.com.icaro.paixao.model.Book;
import br.com.icaro.paixao.repository.BookRepository;
import br.com.icaro.paixao.unitetests.mapper.mocks.MockBook;
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

@ExtendWith(MockitoExtension.class)
class BookServicesTest {

    MockBook input;

    @InjectMocks
    private BookServices service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockBook();
    }

    // ================= TESTE DE BUSCA POR ID =================
    @Test
    void findById() {

        Book book = input.mockEntity(1);

        book.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(book));

        var result = service.findById(1L);

        // ================= VERIFICAÇÕES =================

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/book/v1/1") &&
                                link.getType().equals("GET")
                ));

        // Verifica se existe o link para buscar todos
        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("findAll") &&
                                link.getHref().endsWith("/api/book/v1") &&
                                link.getType().equals("GET")
                )
        );

        // Verifica se o link de criação existe
        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("create") &&
                                link.getHref().endsWith("/api/book/v1") &&
                                link.getType().equals("POST")
                )
        );

        // Verifica se o link de atualização existe
        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("update") &&
                                link.getHref().endsWith("/api/book/v1") &&
                                link.getType().equals("PUT")
                )
        );

        // Verifica se o link de exclusão existe
        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("delete") &&
                                link.getHref().endsWith("/api/book/v1/1") &&
                                link.getType().equals("DELETE")
                )
        );

        // Verifica se os dados vieram corretos
        assertEquals("Some Author", result.getAuthor());
        assertEquals(25D, result.getPrice());
        assertEquals("Some Title", result.getTitle());
        assertNotNull(result.getLaunchDate());
    }


    @Test
    void create() {

        // Cria uma entidade falsa
        Book book = input.mockEntity(1);

        // Cria um "persistido" simulando o que o banco devolveria
        Book persisted = book;
        persisted.setId(1L);

        // Cria um DTO falso para entrada do service
        BookDTO dto = input.mockDTO(1);

        // Configura o mock para simular o save do repository
        when(repository.save(book)).thenReturn(persisted);

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
                                link.getHref().endsWith("/api/book/v1/1") &&
                                link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("findAll") &&
                                link.getHref().endsWith("/api/book/v1") &&
                                link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("create") &&
                                link.getHref().endsWith("/api/book/v1") &&
                                link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("update") &&
                                link.getHref().endsWith("/api/book/v1") &&
                                link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("delete") &&
                                link.getHref().endsWith("/api/book/v1/1") &&
                                link.getType().equals("DELETE")
                )
        );

        // Verifica se os dados vieram corretos
        assertEquals("Some Author", result.getAuthor());
        assertEquals(25D, result.getPrice());
        assertEquals("Some Title", result.getTitle());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void testCreateWithNullBook() {

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
        Book book = input.mockEntity(1);

        // Simula entidade já salva
        Book persisted = book;
        persisted.setId(1L);

        // DTO de entrada
        BookDTO dto = input.mockDTO(1);

        // Mocka o findById e o save do repository
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(persisted);

        // Chama o metodo real
        var result = service.update(dto);

        // ================= VERIFICAÇÕES =================
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        // Verificações de links HATEOAS
        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/book/v1/1") &&
                                link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("findAll") &&
                                link.getHref().endsWith("/api/book/v1") &&
                                link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("create") &&
                                link.getHref().endsWith("/api/book/v1") &&
                                link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("update") &&
                                link.getHref().endsWith("/api/book/v1") &&
                                link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("delete") &&
                                link.getHref().endsWith("/api/book/v1/1") &&
                                link.getType().equals("DELETE")
                )
        );

        // Verifica se os dados vieram corretos
        assertEquals("Some Author", result.getAuthor());
        assertEquals(25D, result.getPrice());
        assertEquals("Some Title", result.getTitle());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void testUpdateWithNullBook() {

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
        Book book = input.mockEntity(1);
        book.setId(1L);

        // Configura o mock para devolver a pessoa quando buscar pelo ID
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        // Executa o metodo delete do service
        service.delete(1L);

        // Verifica se o repository foi chamado corretamente
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Book.class));

        // Garante que não houve mais chamadas além das verificadas
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        List<Book> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<BookDTO> books = service.findAll();

        assertNotNull(books);
        assertEquals(14, books.size());

        var bookOne = books.get(1);

        assertNotNull(bookOne);
        assertNotNull(bookOne.getId());
        assertNotNull(bookOne.getLinks());

        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Some Author1", bookOne.getAuthor());
        assertEquals(25D, bookOne.getPrice());
        assertEquals("Some Title1", bookOne.getTitle());
        assertNotNull(bookOne.getLaunchDate());

        var bookFour = books.get(4);

        assertNotNull(bookFour);
        assertNotNull(bookFour.getId());
        assertNotNull(bookFour.getLinks());

        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/4")
                        && link.getType().equals("GET")
                ));

        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/4")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Some Author4", bookFour.getAuthor());
        assertEquals(25D, bookFour.getPrice());
        assertEquals("Some Title4", bookFour.getTitle());
        assertNotNull(bookFour.getLaunchDate());

    }
}


