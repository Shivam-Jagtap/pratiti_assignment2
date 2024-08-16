package demo.CURD.service;

//import demo.CURD.DAO.BookDao;
import demo.CURD.Entity.Book;
import demo.CURD.Entity.BookDto;
import demo.CURD.Exception.BookNotFoundException;
import demo.CURD.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    ModelMapper mapper;



    public Boolean saveBook(BookDto book){
        Book b1 = mapper.map(book,Book.class);

        System.out.println("Book entity : "+b1);
        Book b =  bookRepository.save(b1);
        if(b.equals(b1)){
            return true;
        }else {
            return false;
        }
    }

    @ExceptionHandler(Exception.class)
    public Optional<Book> getBook(Integer bookId){
//        System.out.println("req came to service for id = "+bookId);
        Optional<Book> book = bookRepository.findById(bookId);
        if(book.isPresent()){
            return book;
        }else{
            throw new BookNotFoundException("Book not found in database");
        }

    }

    public List<BookDto> getAllBooks(Integer pageNo){

        Pageable page=PageRequest.of(pageNo,10);

        List<Book> p=bookRepository.findAll(page).getContent();

        List<BookDto> pages = new ArrayList<>();

        for(Book b : p){
            BookDto newBook = mapper.map(b,BookDto.class);
            pages.add(newBook);
        }

        return pages;
    }
    public void deleteBook(Integer id){
        bookRepository.deleteById(id);

    }
    public boolean updateBook(BookDto b){
        Integer id = b.getBookId();

        Optional<Book> isBook = bookRepository.findById(id);
        if(isBook.isPresent()){
            Book b1 = isBook.get();

            b1.setAuthor(b.getAuthor());
            b1.setName(b.getName());
            b1.setPrize(b.getPrize());

            bookRepository.save(b1);
            return true;
        }else{
            return false;
        }
    }


    public List<BookDto> getByTitle(String title){
        List<Book> b = bookRepository.findByName(title);
        System.out.println(b);

        List<BookDto> books = new ArrayList<>();
        for(Book k : b){
            BookDto n = mapper.map(k,BookDto.class);
            books.add(n);
        }
        return books;
    }
}
