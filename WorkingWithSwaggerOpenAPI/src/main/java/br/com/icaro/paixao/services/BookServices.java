package br.com.icaro.paixao.services;


import br.com.icaro.paixao.controllers.BookController;
import br.com.icaro.paixao.data.dto.v1.BookDTO;
import br.com.icaro.paixao.exception.RequiredObjectIsNullException;
import br.com.icaro.paixao.exception.ResourceNotFoundException;
import br.com.icaro.paixao.model.Book;
import br.com.icaro.paixao.model.Person;
import br.com.icaro.paixao.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.icaro.paixao.mapper.ObjectMapper.parseListObjects;
import static br.com.icaro.paixao.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private Logger logger = LoggerFactory.getLogger(BookServices.class.getName());

    // ==== Injection ====
    private BookRepository bookRepository;

    public BookServices(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // findAll
    public List<BookDTO> findAll() {

        logger.info("Finding All Books ");
        var books = parseListObjects(bookRepository.findAll(), BookDTO.class);
        return books;

    }

    //findById
    public BookDTO findById(Long id) {
        logger.info("Finding BOOK by id: " + id);

        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        var dto = parseObject(entity, BookDTO.class); // transformando em DTO

        return dto;
    }

    public BookDTO create(BookDTO book) {

        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Create One book: " + book);

        var entity = parseObject(book, Book.class);

        var dto = parseObject(bookRepository.save(entity), BookDTO.class);

        return dto;

    }

    public BookDTO update(BookDTO book) {

        if (book == null) throw new RequiredObjectIsNullException();


        logger.info("Updating One book: " + book);

        Book entity = bookRepository.findById(book.getId())
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var dto = parseObject(bookRepository.save(entity), BookDTO.class);

        return dto;
    }

    public void delete(Long id) {
        logger.info("deleting One book: ");

        Book entity = bookRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));

        bookRepository.delete(entity);


    }

    // LINKs HATEOAS para os métodos
    private void  addHateoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findAll()).withRel("delete").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));

    }

}







