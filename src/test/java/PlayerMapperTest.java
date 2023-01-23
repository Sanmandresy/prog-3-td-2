
import app.foot.model.Player;
import app.foot.model.PlayerScorer;
import app.foot.repository.MatchRepository;
import app.foot.repository.PlayerRepository;
import app.foot.repository.entity.MatchEntity;
import app.foot.repository.entity.PlayerEntity;
import app.foot.repository.entity.PlayerScoreEntity;
import app.foot.repository.entity.TeamEntity;
import app.foot.repository.mapper.PlayerMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

//TODO-2: complete these tests
public class PlayerMapperTest {
    MatchRepository matchRepository = mock(MatchRepository.class);
    PlayerRepository playerRepository = mock(PlayerRepository.class);
    PlayerMapper playerMapper = new PlayerMapper(matchRepository,playerRepository);

    PlayerEntity playerEntity() {
        return PlayerEntity.builder()
            .id(1)
            .name("J1")
            .guardian(false)
            .team(TeamEntity.builder()
                .id(1)
                .name("E1")
                .build())
            .build();
    }

    Player playerOne() {
        return Player.builder()
            .id(1)
            .isGuardian(false)
            .name("J1")
            .teamName("E1")
            .build();
    }

    PlayerScorer playerScorer() {
        return PlayerScorer.builder()
            .player(playerOne())
            .isOwnGoal(false)
            .minute(12)
            .build();
    }

    PlayerScoreEntity playerScoreEntity() {
        return PlayerScoreEntity.builder()
            .player(playerEntity())
            .match(MatchEntity.builder()
                .id(1)
                .build())
            .minute(12)
            .ownGoal(false)
            .build();
    }

    @Test
    void player_to_domain_ok() {
        Player actual = playerMapper.toDomain(playerEntity());
        assertEquals(playerOne(),actual);
    }

    @Test
    void player_scorer_to_domain_ok() {
        PlayerScorer expected = playerScorer();
        PlayerScorer actual = playerMapper.toDomain(playerScoreEntity());

        assertEquals(expected,actual);
    }

    @Test
    void player_scorer_to_entity_ok() {
        PlayerScoreEntity actual = playerMapper.toEntity(1,playerScorer());
        assertEquals(playerScoreEntity(),actual);
    }
}
