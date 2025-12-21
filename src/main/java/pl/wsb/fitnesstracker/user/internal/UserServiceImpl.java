package pl.wsb.fitnesstracker.user.internal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(final User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUserByName(String firstName, String lastName) {
        return userRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
    }

    @Override
    public void deleteUser(final Long userId) {
        log.info("Deleting User with ID:{}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        userRepository.delete(user);
    }

    @Override
    public List<User> searchByEmail(String emailFragment){
        return userRepository.findAllByEmailContainingIgnoreCase(emailFragment);
    }

    @Override
    public List<User> findAllUsersOlderThan(LocalDate time) {
        return userRepository.findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(time))
                .toList();
    }

    @Override
    public User updateUser(Long userId, UserDto userDto) {
        log.info("Updating User with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (userDto.firstName() != null) {
            user.setFirstName(userDto.firstName());
        }
        if (userDto.lastName() != null) {
            user.setLastName(userDto.lastName());
        }
        if (userDto.email() != null) {
            user.setEmail(userDto.email());
        }
        if (userDto.birthdate() != null) {
            user.setBirthdate(userDto.birthdate());
        }

        return userRepository.save(user);
    }
}