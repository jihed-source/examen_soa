// FILE: PersonService.java
package com.revision.service;

import java.util.List;
import java.util.Map;

import com.revision.entities.Person;

public interface PersonService {
    
    public Map<String, String> addPerson(Person person);
    
    public boolean deletePerson(Long id);
    
    public Map<String, String> updatePerson(Long id, Person person);
    
    public Person getPersonById(Long id);
    
    public List<Person> getPersonByName(String name);
    
    public List<Person> getAllPersons();
}