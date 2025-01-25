package de.becker.household.adapters.out.persistence.budgets;

import java.math.BigDecimal;
import java.util.List;

import de.becker.household.adapters.out.persistence.households.HouseholdEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "budgets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BudgetEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String period;

  private BigDecimal totalAmount;

  @ManyToOne
  @JoinColumn(name = "household_id")
  private HouseholdEntity household;

  @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
  private List<CategoryEntity> categories;
}
