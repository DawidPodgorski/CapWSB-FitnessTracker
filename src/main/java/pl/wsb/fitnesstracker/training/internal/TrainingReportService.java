package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.mail.api.EmailSender;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingRepository;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class TrainingReportService {

    private final TrainingRepository trainingRepository;
    private final UserProvider userProvider;
    private final EmailSender emailSender;

    // Cron: "0 0 0 * * MON" -> w każdy poniedziałek o północy.
    @Scheduled(fixedRate = 30000)
    public void generateWeeklyReport() {
        log.info(">>> Starting weekly report generation...");

        List<User> users = userProvider.findAllUsers();
        Date oneWeekAgo = getOneWeekAgoDate();

        for (User user : users) {
            generateConsoleReport(user, oneWeekAgo);

            sendEmailReport(user);
        }

        log.info(">>> Report generation finished.");
    }

    private void generateConsoleReport(User user, Date dateLimit) {
        List<Training> recentTrainings = trainingRepository.findByUserIdAndEndTimeAfter(user.getId(), dateLimit);
        log.info("User: {} (ID: {}). Recent trainings count: {}", user.getEmail(), user.getId(), recentTrainings.size());
    }

    private void sendEmailReport(User user) {
        log.info("Preparing email for user: {}", user.getEmail());

        // Pobieramy statystykę do treści maila
        long totalTrainings = trainingRepository.countByUser(user);

        String subject = "Weekly Training Summary";
        String content = String.format(
                "Hello %s,\n\nYou have registered a total of %d trainings in our system.\nKeep training!",
                user.getFirstName(), totalTrainings
        );

        // Tworzymy DTO z danymi
        EmailDto emailDto = new EmailDto(
                user.getEmail(),
                "no-reply@fitnesstracker.pl",
                subject,
                content
        );

        try {
            emailSender.send(emailDto);
        } catch (Exception e) {
            log.error("Failed to send email to user ID: {}", user.getId(), e);
        }
    }

    private Date getOneWeekAgoDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        return calendar.getTime();
    }
}