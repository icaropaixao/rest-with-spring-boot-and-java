package br.com.icaro.paixao.controllers;

import br.com.icaro.paixao.data.dto.v1.BookDTO;
import br.com.icaro.paixao.services.BookServices;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
public class BookController {


    // INJECTION
    private final BookServices bookServices;
    public BookController(BookServices bookServices) {
        this.bookServices = bookServices;
    }


    // findAll
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDTO> findAll() {
        return bookServices.findAll();
    }


    // findById
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDTO findById(@PathVariable("id") Long id) {
        return bookServices.findById(id);

    }


    // create



    // update

}
