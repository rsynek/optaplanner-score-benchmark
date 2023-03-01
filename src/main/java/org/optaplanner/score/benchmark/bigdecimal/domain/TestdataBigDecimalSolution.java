package org.optaplanner.score.benchmark.bigdecimal.domain;

import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.score.buildin.hardmediumsoftbigdecimal.HardMediumSoftBigDecimalScore;
import org.optaplanner.score.benchmark.common.domain.AbstractTestdataSolution;

@PlanningSolution
public class TestdataBigDecimalSolution extends AbstractTestdataSolution {

    private HardMediumSoftBigDecimalScore score;

    @PlanningScore
    public HardMediumSoftBigDecimalScore getScore() {
        return score;
    }

    public void setScore(HardMediumSoftBigDecimalScore score) {
        this.score = score;
    }
}
