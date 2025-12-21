package pl.wsb.fitnesstracker.training.api;

import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<Training> getTraining(Long trainingId);

    /**
     * Retrieves all available trainings.
     *
     * @return A list of all trainings.
     * */
    List<Training> getALLTrainings();

    /**
     * Retrieves all trainings belonging to a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of trainings for the given user.
     * */
    List<Training> getTrainingsByUserId(Long userId);
}
