package org.pract4.controller;

import org.apache.logging.log4j.message.Message;
import org.pract4.entity.Book;
import org.pract4.repository.BookRepository;
import org.reactivestreams.Publisher;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Controller
public class RSocketController {

    private final BookRepository bookRepository;

    public RSocketController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /// Fire and forget
    @MessageMapping("book.create")
    public Mono<Void> addBook(Book book){
        bookRepository.save(book);
        System.out.println("book added - and forget"+book.getTitle());

        return Mono.empty();
    }

    /// request-response
    @MessageMapping("book.getByID")
    public Mono<Book> getBook(Long id){
        return Mono
                .fromCallable(()->bookRepository.findById(id))
                .flatMap(optionalBook -> optionalBook.map(Mono::just).orElseGet(Mono::empty));
    }

    /// request-stream
    @MessageMapping("book.getAll")
    public Flux<Book> getAllBooks(){
        return Flux.fromIterable(bookRepository.findAll());
    }

    /// channel
    @MessageMapping("book.returnMessage")
    public Flux<String> bookName(final Flux<String> messages){
        return messages
                .map(msg -> "Server received: " + msg)
                .doOnNext(System.out::println);
    }
}
