package org.optaplanner.score.benchmark.bigdecimal;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.infra.Blackhole;
import org.optaplanner.constraint.streams.bavet.BavetConstraintFactory;
import org.optaplanner.constraint.streams.common.inliner.HardMediumSoftBigDecimalScoreContext;
import org.optaplanner.constraint.streams.common.inliner.HardMediumSoftBigDecimalScoreInliner;
import org.optaplanner.constraint.streams.common.inliner.JustificationsSupplier;
import org.optaplanner.constraint.streams.common.inliner.UndoScoreImpacter;
import org.optaplanner.constraint.streams.common.inliner.WeightedScoreImpacter;
import org.optaplanner.core.api.score.buildin.hardmediumsoftbigdecimal.HardMediumSoftBigDecimalScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import org.optaplanner.score.benchmark.bigdecimal.domain.TestdataBigDecimalSolution;
import org.optaplanner.score.benchmark.common.AbstractBenchmark;
import org.optaplanner.score.benchmark.common.domain.TestdataEntity;

public class BigDecimalScoreBenchmark extends AbstractBenchmark {

    private WeightedScoreImpacter<HardMediumSoftBigDecimalScore, HardMediumSoftBigDecimalScoreContext> hardScoreImpacter;
    private WeightedScoreImpacter<HardMediumSoftBigDecimalScore, HardMediumSoftBigDecimalScoreContext> mediumScoreImpacter;
    private WeightedScoreImpacter<HardMediumSoftBigDecimalScore, HardMediumSoftBigDecimalScoreContext> softScoreImpacter;
    private HardMediumSoftBigDecimalScoreInliner scoreInliner;

    @Setup(Level.Invocation)
    public void setup() {
        ConstraintFactory constraintFactory = new BavetConstraintFactory<>(buildSolutionDescriptor());
        scoreInliner = new HardMediumSoftBigDecimalScoreInliner(false);
        hardScoreImpacter = scoreInliner.buildWeightedScoreImpacter(alwaysPenalizingHardConstraint(constraintFactory),
                HardMediumSoftBigDecimalScore.ONE_HARD);
        mediumScoreImpacter = scoreInliner.buildWeightedScoreImpacter(alwaysPenalizingMediumConstraint(constraintFactory),
                HardMediumSoftBigDecimalScore.ONE_MEDIUM);
        softScoreImpacter = scoreInliner.buildWeightedScoreImpacter(alwaysPenalizingSoftConstraint(constraintFactory),
                HardMediumSoftBigDecimalScore.ONE_SOFT);
    }

    @Benchmark
    public void benchmarkScore(Blackhole blackhole) {
        impact(hardScoreImpacter, 1, blackhole);
        impact(mediumScoreImpacter, 10, blackhole);
        impact(softScoreImpacter, 100, blackhole);
    }

    private void impact(WeightedScoreImpacter<HardMediumSoftBigDecimalScore, HardMediumSoftBigDecimalScoreContext> impacter,
                        int matchWeight, Blackhole blackhole) {
        UndoScoreImpacter undoHardScoreImpacter = impacter.impactScore(matchWeight, JustificationsSupplier.empty());
        undoHardScoreImpacter.run();
        blackhole.consume(undoHardScoreImpacter);
    }

    private Constraint alwaysPenalizingHardConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(TestdataEntity.class)
                .penalize(HardMediumSoftBigDecimalScore.ONE_HARD)
                .asConstraint("Always penalize hard");
    }

    private Constraint alwaysPenalizingMediumConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(TestdataEntity.class)
                .penalize(HardMediumSoftBigDecimalScore.ONE_MEDIUM)
                .asConstraint("Always penalize medium");
    }

    private Constraint alwaysPenalizingSoftConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(TestdataEntity.class)
                .penalize(HardMediumSoftBigDecimalScore.ONE_SOFT)
                .asConstraint("Always penalize soft");
    }

    SolutionDescriptor<TestdataBigDecimalSolution> buildSolutionDescriptor() {
        return SolutionDescriptor.buildSolutionDescriptor(TestdataBigDecimalSolution.class, TestdataEntity.class);
    }
}
