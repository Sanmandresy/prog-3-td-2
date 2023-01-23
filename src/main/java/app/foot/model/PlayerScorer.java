package app.foot.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
@EqualsAndHashCode
public class PlayerScorer {
    private Player player;
    private Integer minute;
    private Boolean isOwnGoal;
}
