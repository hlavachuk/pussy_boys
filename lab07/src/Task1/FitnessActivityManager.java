package Task1;

/** Оркестратор: сам нічого не рахує, лише викликає сервіси по черзі. */
public class FitnessActivityManager {

    private final WorkoutValidator validator;
    private final WorkoutMetricsCalculator calculator;
    private final WorkoutReportFormatter formatter;
    private final WorkoutRepository repository;
    private final PushNotificationService pushService;

    public FitnessActivityManager(WorkoutValidator validator,
                                  WorkoutMetricsCalculator calculator,
                                  WorkoutReportFormatter formatter,
                                  WorkoutRepository repository,
                                  PushNotificationService pushService) {
        this.validator = validator;
        this.calculator = calculator;
        this.formatter = formatter;
        this.repository = repository;
        this.pushService = pushService;
    }

    public void processWorkoutSession(WorkoutSessionData session) {
        validator.validate(session);
        WorkoutMetrics metrics = calculator.calculate(session);
        String json = formatter.toJson(session, metrics);
        System.out.println(json);
        repository.save(session.getUserId(), json);
        pushService.send(session, metrics);
    }
}
