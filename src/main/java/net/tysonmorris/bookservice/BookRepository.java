package net.tysonmorris.bookservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    Long deleteByIsbn(String isbn);
    Long deleteByIsbnIn(List<String> isbns);
    Book findByIsbn(String isbn);

}
