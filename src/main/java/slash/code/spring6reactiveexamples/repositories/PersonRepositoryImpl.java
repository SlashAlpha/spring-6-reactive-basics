package slash.code.spring6reactiveexamples.repositories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import slash.code.spring6reactiveexamples.domain.Person;

public class PersonRepositoryImpl implements PersonRepository {

    Person michael = Person.builder().firstName("Michael").lastName("Weston").id(1).build();
    Person fiona = Person.builder().firstName("Fiona").lastName("Glenanne").id(2).build();
    Person sam = Person.builder().firstName("Sam").lastName("Axe").id(3).build();
    Person jesse = Person.builder().firstName("Jesse").lastName("Porter").id(4).build();

    @Override
    public Mono<Person> getById(Integer id) {
        return Mono.just(michael);
    }

    @Override
    public Flux<Person> findAll() {

        return Flux.just(michael,fiona,sam,jesse);
    }

    @Override
    public Mono<Person> findById(Integer id) {
        return findAll().filter(person -> person.getId().equals(id)).next();


    }
}
