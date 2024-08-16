package demo.CURD;

import demo.CURD.Entity.Book;
import demo.CURD.Entity.BookDto;
import demo.CURD.service.BookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("bookLibrary")
public class Controller {

    @Autowired
    public BookServiceImpl bookService;


    @PostMapping("/saveBook")
    public ResponseEntity<String> saveBook(@RequestBody @Valid BookDto book){
        System.out.println("request received...");
        if(bookService.saveBook((book))){
            return new ResponseEntity<>("Book saved successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Book not saved", HttpStatusCode.valueOf(300));
    }


    @GetMapping("getBook/{bookId}")
    public ResponseEntity<Optional<Book>> getBook(@PathVariable Integer bookId){
        return  ResponseEntity.status(HttpStatus.CREATED).body(bookService.getBook(bookId));
    }

    @GetMapping("getAllBooks/{pageNo}")
    public ResponseEntity<List<BookDto>> getAllBooks(@PathVariable Integer pageNo){
        return ResponseEntity.status(HttpStatus.FOUND).body(bookService.getAllBooks(pageNo));
    }

    @PutMapping("updateBook")
    public ResponseEntity<String> updateBook(@RequestBody @Valid BookDto bookToUpdate){
        if(bookService.updateBook(bookToUpdate)){
            return ResponseEntity.status(HttpStatus.OK).body("Book updated successfully");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
    }

    @DeleteMapping("deleteBook/{bookId}")
    public void deleteBook(@PathVariable Integer bookId){
        bookService.deleteBook((bookId));
    }

    @GetMapping("getBookByTitle")
    public ResponseEntity<List<BookDto>> getByTitle(@RequestParam("bookTitle") String bookTitle){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getByTitle(bookTitle));
    }
}
