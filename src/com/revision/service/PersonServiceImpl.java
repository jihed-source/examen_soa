// FILE: PersonServiceImpl.java
package com.revision.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revision.entities.Person;

@Service
public class PersonServiceImpl implements PersonService {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Map<String, String> addPerson(Person person) {
        Map<String, String> result = new HashMap<>();
        try {
            em.persist(person);
            em.flush();
            result.put("status", "success");
            result.put("message", "Person added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Failed to add person: " + e.getMessage());
        }
        return result;
    }

    @Override
    @Transactional
    public boolean deletePerson(Long id) {
        try {
            Person person = em.find(Person.class, id);
            if (person != null) {
                em.remove(person);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public Map<String, String> updatePerson(Long id, Person updatedPerson) {
        Map<String, String> result = new HashMap<>();
        try {
            Person person = em.find(Person.class, id);
            if (person != null) {
                person.setNom(updatedPerson.getNom());
                person.setPrenom(updatedPerson.getPrenom());
                person.setAge(updatedPerson.getAge());
                person.setEmail(updatedPerson.getEmail());
                person.setTelephone(updatedPerson.getTelephone());

                em.merge(person);
                result.put("status", "success");
                result.put("message", "Person updated successfully");
            } else {
                result.put("status", "error");
                result.put("message", "Person not found with id: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Failed to update person: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Person getPersonById(Long id) {
        try {
            return em.find(Person.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Person> getPersonByName(String name) {
        try {
            TypedQuery<Person> query = em.createQuery(
                    "SELECT p FROM Person p WHERE p.nom LIKE :name OR p.prenom LIKE :name",
                    Person.class);
            query.setParameter("name", "%" + name + "%");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Person> getAllPersons() {
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}