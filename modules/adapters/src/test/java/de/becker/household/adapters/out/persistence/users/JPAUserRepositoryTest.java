package de.becker.household.adapters.out.persistence.users;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import de.becker.household.adapters.out.persistence.households.JPAHouseholdRepository;
import de.becker.household.domain.model.Household;
import de.becker.household.domain.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ExtendWith(MockitoExtension.class)
public class JPAUserRepositoryTest {

  private static EntityManagerFactory emf;
  private EntityManager em;
  private JPAUserRepository repository;
  private JPAHouseholdRepository householdRepository;

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

    householdRepository = new JPAHouseholdRepository();
    householdRepository.setEntityManager(em);
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
    User user = new User(0L, "TestUser", "secret", null);
    User saved = repository.save(user);

    assertThat(saved.getId()).isGreaterThan(0L);
    assertThat(saved.getUsername()).isSameAs(user.getUsername());
    assertThat(saved.getPasswordHash()).isSameAs(user.getPasswordHash());
  }

  @Test
  void testUpdateUser() {
    User user = new User(0L, "TestUser", "secret", null);
    User saved = repository.save(user);
    User updated = repository
        .save(new User(saved.getId(), "TestUser123", saved.getPasswordHash(), saved.getHousehold()));

    assertThat(updated.getId()).isSameAs(saved.getId());
    assertThat(updated.getUsername()).isNotSameAs(user.getUsername());
    assertThat(updated.getUsername()).isSameAs("TestUser123");
  }

  @Test
  void testFindByUsername() {
    Household household = householdRepository
        .save(new Household(0, "TestHousehold", Collections.emptyList(), Collections.emptyList()));
    repository.save(new User(0, "TestUser", "secret", household));

    Optional<User> found = repository.findByUsername("TestUser");

    assertThat(found.isPresent()).isTrue();
    assertThat(found.get().getId()).isGreaterThan(0);
    assertThat(found.get().getHousehold()).isNotNull();
  }

  @Test
  void testFindByUsernameReturnEmptyOptional() {
    Optional<User> found = repository.findByUsername("TestUser");

    assertThat(found.isPresent()).isFalse();
  }
}
