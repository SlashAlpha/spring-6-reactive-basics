package slash.code.spring6reactiveexamples.repositories;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import slash.code.spring6reactiveexamples.domain.Person;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class PersonRepositoryImplTest {

    PersonRepository personRepository = new PersonRepositoryImpl();

    @Test
    void testMonoByIdBlock() {
        Mono<Person> personMono = personRepository.getById(1);

        Person person = personMono.block();
        System.out.println(person.toString());
    }

    @Test
    void testGetByIdSubscriber() {
        Mono<Person> personMono = personRepository.getById(1);
        personMono.subscribe(person -> {
            System.out.println(person.toString());

        });

    }

    @Test
    void TestMapOperation() {
        Mono<Person> personMono = personRepository.getById(1);
        personMono.map(Person::getFirstName).subscribe(System.out::println);
    }

    @Test
    void testFluxBlockFirst() {
        Flux<Person> personFlux = personRepository.findAll();
        Person person = personFlux.blockFirst();
        System.out.println(person.toString());
    }

    @Test
    void testFluxSubscriber() {
        Flux<Person> personFlux = personRepository.findAll();
        personFlux.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void TestMapFlux() {
        Flux<Person> personFlux = personRepository.findAll();
        personFlux.map(Person::getFirstName).subscribe(System.out::println);
    }

    @Test
    void testFluxToList() {
        Flux<Person> personFlux = personRepository.findAll();
        Mono<List<Person>> listMono = personFlux.collectList();

        listMono.subscribe(list -> {
            list.forEach(System.out::println);
        });
    }

    @Test
    void testFilterOnName() {
        personRepository.findAll().filter(person -> person.getFirstName().equals("Fiona")).subscribe(System.out::println);

    }

    @Test
    void getById() {
        Mono<Person> personMono = personRepository.findAll().filter(person -> person.getId().equals(2)).next();

        personMono.subscribe(person -> {
            System.out.println(person.toString());
            }); ;
    }
    @Test
    void findByIdNotFound() {
        Flux<Person> personFlux=personRepository.findAll();
        final  Integer id=8;
        //trigger chain of event
        Mono<Person> personMono = personRepository.findAll().filter(person -> person.getId().equals(id)).single()
                .doOnError(throwable -> {
                    System.out.println("Error occurred in flux");
                    System.out.println(throwable.toString());

                });

        personMono.subscribe(person -> {
            System.out.println(person.toString());},throwable-> {
            System.out.println("Err occurred in the mono");
            System.out.println(throwable.toString());
        });
        ;
    }

    @Test
    void findByIdTestNotFound() {
     Mono<Person>personMono=   personRepository.findById(8);
     assertFalse(personMono.hasElement().block());
    }
    @Test
    void findByIdTestFound() {
        personRepository.findById(1).subscribe(System.out::println);
    }
    //use stepVerifer instead of using block
    @Test
    void findByIdTestFoundStepVerifier() {
      Mono<Person>personMono=  personRepository.findById(1);
        StepVerifier.create(personMono).expectNextCount(1).verifyComplete();
        personMono.subscribe(System.out::println);
    }
    @Test
    void findByIdTestNotFoundStepVerifier() {
        Mono<Person>personMono=   personRepository.findById(8);
        assertFalse(personMono.hasElement().block());
        StepVerifier.create(personMono).expectNextCount(0).verifyComplete();
        personMono.subscribe(System.out::println);
    }
}