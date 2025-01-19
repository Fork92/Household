package de.becker.household.adapters.out.persistence.households;

import de.becker.household.application.port.out.households.HouseholdRepository;
import de.becker.household.domain.model.Household;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class JPAHouseholdRepository implements HouseholdRepository {

  @PersistenceContext(unitName = "household-pu")
  private EntityManager em;

  public void setEntityManager(final EntityManager em) {
    this.em = em;
  }

  @Override
  public Household save(Household household) {
    HouseholdEntity entity = em.merge(HouseholdMapper.mapToEntity(household));
    return HouseholdMapper.mapToDomain(entity);
  }

  @Override
  public Household findById(long householdId) {
    HouseholdEntity entity = em.find(HouseholdEntity.class, householdId);

    return HouseholdMapper.mapToDomain(entity);
  }

}
