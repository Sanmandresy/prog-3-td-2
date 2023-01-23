import app.foot.controller.rest.Player;
import app.foot.controller.rest.PlayerScorer;
import app.foot.controller.validator.GoalValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

//TODO-1: complete these tests
public class GoalValidatorTest {
    GoalValidator subject = new GoalValidator();

    Player guardianOne() {
        return Player.builder()
            .id(7)
            .name("G1")
            .isGuardian(true)
            .build();
    }

    Player playerOne() {
        return Player.builder()
            .id(1)
            .isGuardian(false)
            .build();
    }

    PlayerScorer scorer() {
        return PlayerScorer.builder()
            .player(playerOne())
            .scoreTime(1)
            .isOG(true)
            .build();
    }

    @Test
    void accept_ok() {
        PlayerScorer validScorer = PlayerScorer.builder()
            .player(playerOne())
            .scoreTime(42)
            .build();
        assertDoesNotThrow(() -> subject.accept(validScorer));
    }

    //Mandatory attributes not provided : scoreTime
    @Test
    void accept_ko() {
        PlayerScorer invalidScoreTimePlayer = PlayerScorer.builder()
            .player(scorer().getPlayer())
            .scoreTime(null)
            .build();
        assertThrows(RuntimeException.class, () -> subject.accept(invalidScoreTimePlayer),
            "Score time is mandatory.");
    }

    @Test
    void when_guardian_throws_exception() {
        PlayerScorer isGuardian = PlayerScorer.builder()
            .player(guardianOne())
            .build();
        assertThrows(RuntimeException.class,() -> subject.accept(isGuardian),
            "The player is a guardian so he cannot score");
    }

    @Test
    void when_score_time_greater_than_90_throws_exception() {
        PlayerScorer invalidScoreTimePlayer = PlayerScorer.builder()
            .scoreTime(95)
            .build();
        assertThrows(RuntimeException.class, () -> subject.accept(invalidScoreTimePlayer),
            "The goal is after 90 minutes so it's not permitted !");
    }

    @Test
    void when_score_time_less_than_0_throws_exception() {
        PlayerScorer invalidScoreTimePlayer = PlayerScorer.builder()
            .scoreTime(-2)
            .build();
        assertThrows(RuntimeException.class, () -> subject.accept(invalidScoreTimePlayer),
            "The match hasn't even started yet so it's not permitted !");
    }
}
