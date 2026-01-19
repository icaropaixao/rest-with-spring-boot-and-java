package br.com.icaro.paixao.controllers;


import br.com.icaro.paixao.controllers.docs.BookControllerDocs;
import br.com.icaro.paixao.data.dto.v1.BookDTO;
import br.com.icaro.paixao.services.BookServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "People", description = "EndPoints for Managing People")
public class BookController implements BookControllerDocs {

    //JEITO ANTIGO DE INJETAR
    // @Autowired
    // private BookServices bookServices;
    // NOVO
    private final BookServices bookServices;
    public BookController(BookServices bookServices) {
        this.bookServices = bookServices;
    }


    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public List<BookDTO> findAll() {
        return bookServices.findAll();

    }



    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})

    @Override
    public BookDTO findById(@PathVariable("id") Long id) {
        return bookServices.findById(id);

    }

    @PostMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE})
    @Override
    public BookDTO create(@RequestBody BookDTO book) {

        return bookServices.create(book);

    }



    @PutMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE})
    @Override
    public BookDTO update(@RequestBody BookDTO book) {

        return bookServices.update(book);

    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
       bookServices.delete(id);
       return ResponseEntity.noContent().build();

    }


}
