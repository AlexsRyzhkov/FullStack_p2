package org.pract4;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pract4.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;

@SpringBootTest
class Pract4ApplicationTests {

    private static  RSocketRequester requester;

    @BeforeAll
    public static void setupOnce(@Autowired RSocketRequester.Builder builder, @Value("${spring.rsocket.server.port}") Integer port) {
        requester = builder.tcp("localhost", port);
    }

    @Test
    public void testFireAndForget(){
        Book book = new Book("test Autor");

        Book result = requester
                .route("book.create")
                .data(book)
                .retrieveMono(Book.class)
                .block();

        assertNull(result);
    }

    @Test
    public void testRequestResponse(){
        Book book = requester
                .route("book.getByID")
                .data(1)
                .retrieveMono(Book.class)
                .block();

        assert book != null;
        assertEquals(1, book.getId());
    }


    @Test
    public void testRequestStream(){
        List<Book> books = requester
                .route("book.getAll")
                .retrieveFlux(Book.class)
                .collectList()
                .block();

        assert books != null;
    }

    @Test
    public void testChannel(){
        Flux<String> clientMessages = Flux.just("Book 1", "Book 2", "Book 3")
                .delayElements(Duration.ofMillis(100));

        Flux<String> responseFlux = requester
                .route("book.returnMessage")
                .data(clientMessages)
                .retrieveFlux(String.class);

        StepVerifier.create(responseFlux)
                .expectNext("Server received: Book 1")
                .expectNext("Server received: Book 2")
                .expectNext("Server received: Book 3")
                .verifyComplete();
    }
}
