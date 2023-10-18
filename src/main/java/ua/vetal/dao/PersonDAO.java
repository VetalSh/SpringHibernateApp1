package ua.vetal.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.vetal.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
  private final SessionFactory sessionFactory;

  @Autowired
  public PersonDAO(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional(readOnly = true)
  public List<Person> index() {
//    return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    Session session = sessionFactory.getCurrentSession();

//    List<Person> people = session.createQuery("SELECT p FROM Person p", Person.class)
    List<Person> people = session.createQuery("FROM Person", Person.class)
        .getResultList();

    return people;
  }

  @Transactional(readOnly = true)
  public Person show(String email) {
//    return jdbcTemplate.query("SELECT * FROM Person WHERE email=?", new Object[] {email},
//        new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    Session session = sessionFactory.getCurrentSession();
//    Optional<Person> person = session.createQuery("FROM Person WHERE email",
//        Person.class).stream().findAny();
    return session.get(Person.class, email);
  }

  @Transactional(readOnly = true)
  public Person show(int id) {
//    return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id},
//        new BeanPropertyRowMapper<>(Person.class))
//        .stream().findAny().orElse(null);
    Session session = sessionFactory.getCurrentSession();
    Person person = session.get(Person.class, id);
    return person;
  }

  @Transactional
  public void save(Person person) {
//    jdbcTemplate.update("INSERT INTO Person(name, age, email, address) VALUES(?, ?, ?, ?)", person.getName(),
//        person.getAge(), person.getEmail(), person.getAddress());
    Session session = sessionFactory.getCurrentSession();
    session.save(person);
  }

  @Transactional
  public void update(int id, Person updatedPerson) {
//    jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=?, address=? WHERE id=?",
//        updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getAddress(), id);
    Session session = sessionFactory.getCurrentSession();
    Person personToBeUpdated = session.get(Person.class, id);

    personToBeUpdated.setName(updatedPerson.getName());
    personToBeUpdated.setAge(updatedPerson.getAge());
    personToBeUpdated.setEmail(updatedPerson.getEmail());
    personToBeUpdated.setAddress(updatedPerson.getAddress());
  }

  @Transactional
  public void delete(int id) {
//    jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    Session session = sessionFactory.getCurrentSession();
    session.remove(session.get(Person.class,id));
  }

}