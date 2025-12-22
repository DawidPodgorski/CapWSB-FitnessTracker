package pl.wsb.fitnesstracker.training.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.UserDto;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * Data Transfer Object (DTO) for Training representation.
 * <p>
 * Used to transfer training data between the client and the server
 * without exposing the internal {@link Training} entity structure.
 * This class includes the associated user information as {@link UserDto}.
 * </p>
 */
public class TrainingDto {
    private Long id;
    private UserDto user;
    private Date startTime;
    private Date endTime;
    private ActivityType activityType;
    private double distance;
    private double averageSpeed;
}
