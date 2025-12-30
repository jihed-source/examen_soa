// FILE: RestRouter.java (UPDATED)
package com.revision.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revision.entities.Person;
import com.revision.service.PersonServiceImpl;

@RestController
@RequestMapping("/exercice/revision/api")
public class RestRouter {

    @Autowired
    private PersonServiceImpl personService;

    @PostMapping("/persons")
    public ResponseEntity<Map<String, String>> addPerson(@RequestBody Person person) {
        Map<String, String> result = personService.addPerson(person);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable Long id) {
        boolean result = personService.deletePerson(id);
        if (result) {
            return ResponseEntity.ok(Map.of("status", "success", "message", "Person deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", "error", "message", "Person not found"));
        }
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Map<String, String>> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        Map<String, String> result = personService.updatePerson(id, person);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<?> getPersonById(@PathVariable Long id) {
        Person person = personService.getPersonById(id);
        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", "error", "message", "Person not found"));
        }
    }

    @GetMapping("/persons/search/{name}")
    public ResponseEntity<List<Person>> getPersonByName(@PathVariable String name) {
        List<Person> persons = personService.getPersonByName(name);
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personService.getAllPersons();
        return ResponseEntity.ok(persons);
    }
}