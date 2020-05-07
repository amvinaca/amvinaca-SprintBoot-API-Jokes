package com.jokes.jokes.Models;
import org.springframework.data.repository.CrudRepository;


public interface JokesRepo extends CrudRepository<Jokes, String> {
}
