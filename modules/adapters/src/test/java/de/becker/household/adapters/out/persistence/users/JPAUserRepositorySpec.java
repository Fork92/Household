package de.becker.household.adapters.out.persistence.users;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import de.becker.household.domain.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ExtendWith(MockitoExtension.class)
public class JPAUserRepositorySpec {

  private static EntityManagerFactory emf;
  private EntityManager em;
  private JPAUserRepository repository;

  @BeforeAll
  static void setupBeforeAll() {
    emf = Persistence.createEntityManagerFactory("TestPU");
  }

  @AfterAll
  static void teardownAfterAll() {
    if (emf != null) {
      emf.close();
    }
  }

  @BeforeEach
  void setup() {
    em = emf.createEntityManager();
    repository = new JPAUserRepository();
    repository.setEntityManager(em);
    em.getTransaction().begin();
  }

  @AfterEach
  void teardown() {
    if (em.getTransaction().isActive()) {
      em.getTransaction().rollback();
    }

    em.close();
  }

  @Test
  void testSaveNewUser() {
    User user = new User(0L, "TestUser", "secret");
    User saved = repository.save(user);

    assertThat(saved.getId()).isGreaterThan(0L);
  }

}
