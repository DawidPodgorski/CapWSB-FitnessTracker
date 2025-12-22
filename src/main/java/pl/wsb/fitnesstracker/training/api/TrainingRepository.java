package pl.wsb.fitnesstracker.training.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    /**
     * Finds all training sessions belonging to a specific user.
     *
     * @param userId the ID of the user whose trainings are to be searched
     * @return a list of {@link Training} entities associated with the given user ID
     */
    List<Training> findByUserId(long userId);
}
