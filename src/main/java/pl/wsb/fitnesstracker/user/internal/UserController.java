package pl.wsb.fitnesstracker.user.internal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.fitnesstracker.user.api.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.*;

import java.time.LocalDate;
import java.util.List;

/**
 * UserController is responsible for handling HTTP requests related to user operations.
 * It provides endpoints for retrieving and creating users.
 */
@RestController
@RequestMapping("/v1/users")
class UserController {

    private final UserService userService;
//    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    public UserController(UserServiceImpl userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    // Simple info
    @GetMapping("/simple")
    public List<UserSimpleDto> getAllUserSimple() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    // Fetch by ID
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    // Fetch by email
    @GetMapping("/email")
    public List<UserDto> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .map(user -> List.of(userMapper.toDto(user))) // Pakujemy wynik w Listę
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    // Fetch by first and last name
    @GetMapping("/search-by-name")
    public List<UserDto> getUserByFirstAndLastName(
            @RequestParam String first,
            @RequestParam String last)
    {
        return userService.getUserByName(first, last)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    // Add new user
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        // DTO to entity
        User user = userMapper.toEntity(userDto);
        // Add to service
        User createdUser = userService.createUser(user);
        // Entity to DTO (with id)
        return userMapper.toDto(createdUser);
    }

    // Delete user
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    // Search by email fragment
    @GetMapping("/search/email")
    public List<UserEmailDto> searchUserByEmail(@RequestParam String email) {
        return userService.searchByEmail(email)
                .stream()
                .map(userMapper::toEmailDto)
                .toList();
    }

    // Search users older than
    @GetMapping("/older/{time}")
    public List<UserDto> getUsersOlderThan(@PathVariable LocalDate time) {
        return userService.findAllUsersOlderThan(time)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    // Update user by ID
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(id, userDto);
        return userMapper.toDto(updatedUser);
    }
}

