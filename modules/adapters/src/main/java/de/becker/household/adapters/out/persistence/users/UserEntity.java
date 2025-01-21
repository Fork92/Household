package de.becker.household.adapters.out.persistence.users;

import de.becker.household.adapters.out.persistence.households.HouseholdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
    @NamedQuery(name = UserEntity.FIND_BY_USERNAME, query = "SELECT u FROM UserEntity u where u.username = :username")
})
public class UserEntity {

  public static final String FIND_BY_USERNAME = "findByUsername";
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String username;
  @Column(name = "password_hash")
  private String passwordHash;
  @OneToOne
  @JoinColumn(name = "household_id")
  private HouseholdEntity household;

}
