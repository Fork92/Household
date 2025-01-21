package de.becker.household.adapters.out.persistence.users;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import de.becker.household.adapters.out.persistence.households.HouseholdEntity;
import de.becker.household.domain.model.Household;
import de.becker.household.domain.model.User;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Test
    void testMapToEntity() {
        final User user = new User(123L, "TestUser", "secret", new Household(321L));
        final UserEntity entity = UserMapper.mapToEntity(user);

        assertThat(entity.getId()).isEqualTo(user.getId());
        assertThat(entity.getUsername()).isEqualTo(user.getUsername());
        assertThat(entity.getPasswordHash()).isEqualTo(user.getPasswordHash());
        assertThat(entity.getHousehold()).isNotNull();
    }
    

    @Test
    void testReturnNullInCaseOfNullUser() {
        User user = null;
        final UserEntity entity = UserMapper.mapToEntity(user);

        assertThat(entity).isNull();
    }

    @Test
    void testMapToDomain() {
        final UserEntity entity = new UserEntity(123L, "TestUser", "secret", new HouseholdEntity(321L));
        final User user = UserMapper.mapToDomain(entity);

        assertThat(user.getId()).isEqualTo(entity.getId());
        assertThat(user.getUsername()).isEqualTo(entity.getUsername());
        assertThat(user.getPasswordHash()).isEqualTo(entity.getPasswordHash());
        assertThat(user.getHousehold()).isNotNull();
    }

    @Test
    void testReturnNullInCaseOfNullEntity() {
        UserEntity entity = null;
        final User user = UserMapper.mapToDomain(entity);

        assertThat(user).isNull();
    }

}
