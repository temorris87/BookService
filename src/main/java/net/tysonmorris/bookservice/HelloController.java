package net.tysonmorris.bookservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {

    @Autowired
    private BookRepository repository;

    /**
     * Persistently store the book into the database.
     * @param isbn The unique ISBN number of the book.
     * @param title The title of the book.
     * @param authorFirstName The first name of the author.
     * @param authorLastName The last name of the author.
     * @param publisher The publisher of the book.
     * @return A String describing success as well as updates HTTP Status accordingly.
     */
    @PutMapping("/putbook")
    public ResponseEntity<String> testPut(String isbn,
                                          String title,
                                          String authorFirstName,
                                          String authorLastName,
                                          String publisher) {

        if (title.equals("") || authorFirstName.equals("") || authorLastName.equals("") || publisher.equals("")) {
            return new ResponseEntity<>(
                    "All fields required: title, authorFirstName, authorLastName, publisher",
                    HttpStatus.BAD_REQUEST);
        }

        if (repository.findByIsbn(isbn) != null) {
            return new ResponseEntity<>(
                    "ISBN value already exists.",
                    HttpStatus.BAD_REQUEST);
        }

        Person author = new Person(authorFirstName, authorLastName);
        repository.save(new Book(isbn, title, author, publisher));

        return new ResponseEntity<>(
                "Successfully added book.",
                HttpStatus.OK);

    }

    /**
     * Get a book by the target isbn value.
     * @param isbn The isbn of the targeted book.
     * @return The requested Book. HTTP Status is updated accordingly.
     */
    @GetMapping("/getbook")
    public ResponseEntity<Book> getBook(String isbn) {
        Book targetBook = repository.findByIsbn(isbn);

        if (targetBook != null)
            return new ResponseEntity<>(targetBook, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Get all books in the database.
     * @return A List<Book> containing all books in the database.
     */
    @GetMapping("/getbooks")
    public List<Book> getBooks() {
        return repository.findAll();
    }

    /**
     * Update the book with the matching ISBN number. Any parameter with the exception of ISBN can be the empty
     * string "". Any parameter with argument not equal to "" will be updated with that particular value. The
     * authorFirstName and authorLastName parameters must be updated simultaneously.
     * @param isbn Required: The ISBN of the target book for update.
     * @param title The new title of the book, set to "" if old title remains.
     * @param authorFirstName The new author's first name, set to "" with authorLastName set to "" for no change.
     * @param authorLastName The new author's last name, set to "" with authorFirstName set to "" for no change.
     * @param publisher The new publisher of the book, set to "" if old publisher remains.
     * @return Updated HTTP Status based on success.
     */
    @PostMapping("/updatebook")
    public ResponseEntity<Void> updateBook(String isbn, String title, String authorFirstName, String authorLastName, String publisher) {
        Book targetBook = repository.findByIsbn(isbn);

        if (targetBook == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        if (!title.equals(""))
            targetBook.setTitle(title);

        if (!authorFirstName.equals("") && !authorLastName.equals(""))
            targetBook.setAuthor(new Person(authorFirstName, authorLastName));

        if (!publisher.equals(""))
            targetBook.setPublisher(publisher);

        repository.save(targetBook);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * Delete a book with the matching ISBN number.
     * @param isbn The ISBN of the target book for deletion.
     * @return The value returned by mongodb delete query. HTTP Status is always 200.
     */
    @PostMapping("/deletebook")
    public Long deleteBook(String isbn) {
        return repository.deleteByIsbn(isbn);
    }

    /**
     * Delete a list of books with the matching ISBN numbers.
     * @param isbns The list of ISBN values to be deleted.
     * @return The value returned by the mongodb delete query. HTTP Status is always 200.
     */
    @PostMapping("/deletebooks")
    public Long deleteBooks(@RequestParam("isbn") List<String> isbns) {
        return repository.deleteByIsbnIn(isbns);
    }

}
