package org.optaplanner.score.benchmark.common.domain;

import org.optaplanner.core.api.domain.lookup.PlanningId;

public class TestdataObject implements CodeAssertable {

    @PlanningId
    protected String code;

    public TestdataObject() {
    }

    public TestdataObject(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

}
