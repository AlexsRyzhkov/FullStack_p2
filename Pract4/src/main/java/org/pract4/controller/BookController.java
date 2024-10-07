package org.pract4.controller;

import org.pract4.entity.Book;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final RSocketRequester requester;

    BookController(RSocketRequester.Builder rsBuilder) {
        requester = rsBuilder.tcp("localhost", 7000);
    }

    @PostMapping()
    public String createBook(@RequestBody Book book){
        requester
                .route("book.create")
                .data(book)
                .send()
                .block();

        return "Book is created: and result is " + book.getTitle();
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable Long id){
        Mono<Book> mono = requester
                .route("book.getByID")
                .data(id)
                .retrieveMono(Book.class);

        Book book = mono.block();
        if (book == null){
            return "Книга не найдена";
        }

        return book.toString();
    }

    @GetMapping
    public List<Book> getAllBooks(){
        Flux<Book> stream = requester
                .route("book.getAll")
                .retrieveFlux(Book.class);

        return stream.collectList().block();
    }
}
