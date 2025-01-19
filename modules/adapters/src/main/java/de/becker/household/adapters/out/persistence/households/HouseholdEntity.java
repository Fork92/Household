package de.becker.household.adapters.out.persistence.households;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
