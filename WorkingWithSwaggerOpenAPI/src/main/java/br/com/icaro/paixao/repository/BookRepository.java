package br.com.icaro.paixao.repository;

import br.com.icaro.paixao.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {


}
