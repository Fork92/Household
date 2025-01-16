package de.becker.household.adapters.out.persistence.users;

import java.util.Optional;

import de.becker.household.application.port.out.UserRepository;
import de.becker.household.domain.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnit;

@Stateless
public class JPAUserRepository implements UserRepository {

  @PersistenceUnit(unitName = "hosuehold-pu")
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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByUsername'");
  }

}
