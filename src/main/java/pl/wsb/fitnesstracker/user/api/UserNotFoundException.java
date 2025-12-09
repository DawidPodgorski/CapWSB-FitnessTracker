package pl.wsb.fitnesstracker.user.api;

import pl.wsb.fitnesstracker.exception.api.NotFoundException;

/**
 * Exception indicating that the {@link User} was not found.
 */
@SuppressWarnings("squid:S110")
public class UserNotFoundException extends NotFoundException {

//    private UserNotFoundException(String message) {
//        super(message);
//    }

    public UserNotFoundException(Long id) {
        super("User with ID=%s was not found".formatted(id));
    }

    public UserNotFoundException(String missingField) {
        super("User with identifier/email=%s was not found".formatted(missingField));
    }

}
