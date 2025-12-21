package pl.wsb.fitnesstracker.training.internal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
public class TrainingController {
    private final TrainingProvider trainingProvider;
    private final TrainingMapper trainingMapper;

    public TrainingController(TrainingProvider trainingProvider, TrainingMapper trainingMapper) {
        this.trainingProvider = trainingProvider;
        this.trainingMapper = trainingMapper;
    }

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingProvider.getALLTrainings().stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/{userId}")
    public List<TrainingDto> getTrainingsByUser(@PathVariable Long userId) {
        return trainingProvider.getTrainingsByUserId(userId).stream()
                .map(trainingMapper::toDto)
                .toList();
    }
}
