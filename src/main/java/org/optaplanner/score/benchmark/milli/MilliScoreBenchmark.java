package org.optaplanner.score.benchmark.milli;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.infra.Blackhole;
import org.optaplanner.constraint.streams.bavet.BavetConstraintFactory;
import org.optaplanner.constraint.streams.common.inliner.HardMediumSoftMilliScoreContext;
import org.optaplanner.constraint.streams.common.inliner.HardMediumSoftMilliScoreInliner;
import org.optaplanner.constraint.streams.common.inliner.JustificationsSupplier;
import org.optaplanner.constraint.streams.common.inliner.UndoScoreImpacter;
import org.optaplanner.constraint.streams.common.inliner.WeightedScoreImpacter;
import org.optaplanner.core.api.score.buildin.hardmediumsoftmilli.HardMediumSoftMilliScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import org.optaplanner.score.benchmark.common.AbstractBenchmark;
import org.optaplanner.score.benchmark.common.domain.TestdataEntity;
import org.optaplanner.score.benchmark.milli.domain.TestdataMilliSolution;

public class MilliScoreBenchmark extends AbstractBenchmark {

    private WeightedScoreImpacter<HardMediumSoftMilliScore, HardMediumSoftMilliScoreContext> hardScoreImpacter;
    private WeightedScoreImpacter<HardMediumSoftMilliScore, HardMediumSoftMilliScoreContext> mediumScoreImpacter;
    private WeightedScoreImpacter<HardMediumSoftMilliScore, HardMediumSoftMilliScoreContext> softScoreImpacter;
    private HardMediumSoftMilliScoreInliner scoreInliner;

    @Setup(Level.Invocation)
    public void setup() {
        ConstraintFactory constraintFactory = new BavetConstraintFactory<>(buildSolutionDescriptor());
        scoreInliner = new HardMediumSoftMilliScoreInliner(false);
        hardScoreImpacter = scoreInliner.buildWeightedScoreImpacter(alwaysPenalizingHardConstraint(constraintFactory),
                HardMediumSoftMilliScore.ONE_HARD);
        mediumScoreImpacter = scoreInliner.buildWeightedScoreImpacter(alwaysPenalizingMediumConstraint(constraintFactory),
                HardMediumSoftMilliScore.ONE_MEDIUM);
        softScoreImpacter = scoreInliner.buildWeightedScoreImpacter(alwaysPenalizingSoftConstraint(constraintFactory),
                HardMediumSoftMilliScore.ONE_SOFT);
    }

    @Benchmark
    public void benchmarkScore(Blackhole blackhole) {
        impact(hardScoreImpacter, 1, blackhole);
        impact(mediumScoreImpacter, 10, blackhole);
        impact(softScoreImpacter, 100, blackhole);
    }

    private void impact(WeightedScoreImpacter<HardMediumSoftMilliScore, HardMediumSoftMilliScoreContext> impacter,
                        int matchWeight, Blackhole blackhole) {
        UndoScoreImpacter undoHardScoreImpacter = impacter.impactScore(matchWeight, JustificationsSupplier.empty());
        undoHardScoreImpacter.run();
        blackhole.consume(undoHardScoreImpacter);
    }

    private Constraint alwaysPenalizingHardConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(TestdataEntity.class)
                .penalize(HardMediumSoftMilliScore.ONE_HARD)
                .asConstraint("Always penalize hard");
    }

    private Constraint alwaysPenalizingMediumConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(TestdataEntity.class)
                .penalize(HardMediumSoftMilliScore.ONE_MEDIUM)
                .asConstraint("Always penalize medium");
    }

    private Constraint alwaysPenalizingSoftConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(TestdataEntity.class)
                .penalize(HardMediumSoftMilliScore.ONE_SOFT)
                .asConstraint("Always penalize soft");
    }

    SolutionDescriptor<TestdataMilliSolution> buildSolutionDescriptor() {
        return SolutionDescriptor.buildSolutionDescriptor(TestdataMilliSolution.class, TestdataEntity.class);
    }
}
