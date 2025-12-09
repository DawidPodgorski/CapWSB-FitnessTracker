package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface (API) for modifying and retrieving operations on {@link User} entities.
 * It defines the business logic contract for managing users in the Fitness Tracker application.
 */
public interface UserService {

    /**
     * Creates a new user in the system.
     *
     * @param user The user entity to be created.
     * @return The created user with assigned ID.
     */
    User createUser(User user);

    /**
     * Deletes a user by their unique ID.
     *
     * @param userId The ID of the user to be deleted.
     */
    void deleteUser(Long userId);

    /**
     * Retrieves all users stored in the system.
     *
     * @return List of all users.
     */
    List<User> findAllUsers();

    /**
     * Retrieves a specific user by their ID.
     *
     * @param userId The ID of the user.
     * @return An Optional containing the user if found, or empty if not.
     */
    Optional<User> getUser(Long userId);

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address to search for.
     * @return An Optional containing the user if found, or empty if not.
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Finds users by their first and last name (ignoring case).
     *
     * @param firstName The first name.
     * @param lastName  The last name.
     * @return List of matching users.
     */
    List<User> getUserByName(String firstName, String lastName);

    /**
     * Searches for users based on a fragment of their email address.
     *
     * @param emailFragment The text fragment to search for within emails.
     * @return List of users whose email contains the fragment.
     */
    List<User> searchByEmail(String emailFragment);

    /**
     * Finds all users born before the specified date.
     *
     * @param time The cut-off date.
     * @return List of users older than the specified date.
     */
    List<User> findAllUsersOlderThan(LocalDate time);

    /**
     * Updates an existing user's details.
     *
     * @param userId  The ID of the user to update.
     * @param userDto The data transfer object containing new values.
     * @return The updated user entity.
     */
    User updateUser(Long userId, UserDto userDto);
}