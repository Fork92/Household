package de.becker.household.adapters.out.persistence.households;

import java.util.List;

import de.becker.household.adapters.out.persistence.budgets.BudgetEntity;
import de.becker.household.adapters.out.persistence.users.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "household")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "household")
  private List<UserEntity> users;

  @OneToMany(mappedBy = "household")
  private List<BudgetEntity> budgets;
}
