package de.becker.household.adapters.out.persistence.users;

import java.util.Optional;

import de.becker.household.application.port.out.users.UserRepository;
import de.becker.household.domain.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Stateless
public class JPAUserRepository implements UserRepository {

  @PersistenceContext(unitName = "household-pu")
  private EntityManager em;

  public void setEntityManager(final EntityManager em) {
    this.em = em;
  }

  @Override
  public User save(User user) {
    UserEntity entity = em.merge(UserMapper.mapToEntity(user));
    return UserMapper.mapToDomain(entity);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    try {
      UserEntity entity = em.createNamedQuery(UserEntity.FIND_BY_USERNAME, UserEntity.class)
          .setParameter("username", username).getSingleResult();

      User user = UserMapper.mapToDomain(entity);
      return Optional.ofNullable(user);
    } catch (NoResultException ex) {
      return Optional.empty();
    }

  }

}
