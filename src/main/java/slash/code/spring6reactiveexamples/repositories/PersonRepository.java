package slash.code.spring6reactiveexamples.repositories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import slash.code.spring6reactiveexamples.domain.Person;

public interface PersonRepository {
    Mono<Person> getById(Integer id);

    Flux<Person> findAll();
    Mono<Person> findById(Integer id);

}
