package com.architect.api.user;

import com.architect.api.user.dto.request.DtoCreateUserRequest;
import com.architect.api.user.dto.request.DtoUpdateUserRequest;
import com.architect.api.user.dto.response.DtoGetUserResponse;
import com.architect.persistence.entities.DbUser;
import com.architect.persistence.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Spy
    private UserMapper userMapper;

    private UserService userService;

    private final Faker faker = new Faker();
    private final AtomicLong counter = new AtomicLong();

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, userMapper);
    }

    @Test
    void fetchUsers() {
        List<DbUser> dbUsers = Arrays.asList(buildUser(), buildUser(), buildUser());
        Mockito.when(userRepository.findAll()).thenReturn(dbUsers);
        List<DtoGetUserResponse> result = userService.fetchUsers();
        assertThat(dbUsers.size()).isEqualTo(result.size());
        IntStream.range(0, dbUsers.size()).forEach(idx -> {
            assertUser(dbUsers.get(idx), result.get(idx));
        });
    }

    @Test
    void testFetchUserById() {
        DbUser dbUser = buildUser();
        Mockito.when(userRepository.findById(dbUser.getId())).thenReturn(Optional.of(dbUser));
        DtoGetUserResponse result = userService.fetchUserById(dbUser.getId());
        assertUser(dbUser, result);
    }

    @Test
    void testFetchUserByInvalidIdThrowsException() {
        DbUser dbUser = buildUser();
        Mockito.when(userRepository.findById(dbUser.getId())).thenReturn(Optional.of(dbUser));
        assertThatThrownBy(() -> userService.fetchUserById(45L));
    }

    @Test
    void testCreateUser() {
        DtoCreateUserRequest request = buildCreateUserRequest();
        ArgumentCaptor<DbUser> dbUserArgumentCaptor = ArgumentCaptor.forClass(DbUser.class);
        Mockito.when(userRepository.save(dbUserArgumentCaptor.capture())).thenReturn(null);

        userService.createUser(request);

        Mockito.verify(userRepository).save(Mockito.any(DbUser.class));

        DbUser createdUser = dbUserArgumentCaptor.getValue();
        assertThat(request.getEmail()).isEqualTo(createdUser.getEmail());
        assertThat(request.getFirstName()).isEqualTo(createdUser.getFirstName());
        assertThat(request.getLastName()).isEqualTo(createdUser.getLastName());
        assertThat(request.getPhone()).isEqualTo(createdUser.getPhone());
        assertThat(request.getUsername()).isEqualTo(createdUser.getUsername());
    }

    @Test
    void testUpdateUser() {
        DbUser user = buildUser();
        DtoUpdateUserRequest request = buildUpdateUserRequest();
        ArgumentCaptor<DbUser> dbUserArgumentCaptor = ArgumentCaptor.forClass(DbUser.class);
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(dbUserArgumentCaptor.capture())).thenReturn(user);

        userService.updateUserById(user.getId(), request);

        Mockito.verify(userRepository).findById(Mockito.any(Long.class));
        Mockito.verify(userRepository).save(Mockito.any(DbUser.class));

        DbUser updatedUser = dbUserArgumentCaptor.getValue();
        assertThat(request.getEmail()).isEqualTo(updatedUser.getEmail());
        assertThat(request.getFirstName()).isEqualTo(updatedUser.getFirstName());
        assertThat(request.getLastName()).isEqualTo(updatedUser.getLastName());
        assertThat(request.getPhone()).isEqualTo(updatedUser.getPhone());
    }

    @Test
    void testUpdateUserWithInvalidId() {
        DbUser user = buildUser();
        DtoUpdateUserRequest request = buildUpdateUserRequest();
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> userService.updateUserById(33L, request));
    }

    @Test
    void testDeleteUser() {
        DbUser user = buildUser();
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUserById(1L);

        Mockito.verify(userRepository).delete(Mockito.any(DbUser.class));
    }

    @Test
    void testDeleteUserWithInvalidId() {
        DbUser user = buildUser();
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.deleteUserById(44L));
    }

    private void assertUser(DbUser actual, DtoGetUserResponse expected) {
        assertThat(expected).isNotNull();
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        assertThat(actual.getUsername()).isEqualTo(expected.getUsername());
        assertThat(actual.getPhone()).isEqualTo(expected.getPhone());
    }

    private DtoCreateUserRequest buildCreateUserRequest() {
        DtoCreateUserRequest user = new DtoCreateUserRequest();
        user.setEmail(faker.internet().emailAddress());
        user.setUsername(faker.name().username());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setPhone(faker.phoneNumber().phoneNumber());
        return user;
    }

    private DtoUpdateUserRequest buildUpdateUserRequest() {
        DtoUpdateUserRequest user = new DtoUpdateUserRequest();
        user.setEmail(faker.internet().emailAddress());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setPhone(faker.phoneNumber().phoneNumber());
        return user;
    }

    private DbUser buildUser() {
        DbUser user = new DbUser();
        user.setId(counter.incrementAndGet());
        user.setEmail(faker.internet().emailAddress());
        user.setUsername(faker.name().username());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setPhone(faker.phoneNumber().phoneNumber());
        return user;
    }
}
