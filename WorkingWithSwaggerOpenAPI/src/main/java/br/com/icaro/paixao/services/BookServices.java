package br.com.icaro.paixao.services;


import br.com.icaro.paixao.data.dto.v1.BookDTO;
import br.com.icaro.paixao.exception.ResourceNotFoundException;
import br.com.icaro.paixao.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.icaro.paixao.mapper.ObjectMapper.parseListObjects;
import static br.com.icaro.paixao.mapper.ObjectMapper.parseObject;

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
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));

        var dto = parseObject(entity, BookDTO.class); // transformando em DTO



        return dto;
    }



}
