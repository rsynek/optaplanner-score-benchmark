package org.optaplanner.score.benchmark.milli.domain;

import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.score.buildin.hardmediumsoftmilli.HardMediumSoftMilliScore;
import org.optaplanner.score.benchmark.common.domain.AbstractTestdataSolution;


@PlanningSolution
public class TestdataMilliSolution extends AbstractTestdataSolution {

    private HardMediumSoftMilliScore score;

    @PlanningScore
    public HardMediumSoftMilliScore getScore() {
        return score;
    }

    public void setScore(HardMediumSoftMilliScore score) {
        this.score = score;
    }
}
