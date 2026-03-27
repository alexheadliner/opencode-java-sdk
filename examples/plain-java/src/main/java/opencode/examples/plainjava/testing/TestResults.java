package opencode.examples.plainjava.testing;

import java.util.ArrayList;
import java.util.List;

public class TestResults {

    private final List<TestResult> results;
    private long totalExecutionTimeMs;

    public TestResults() {
        this.results = new ArrayList<>();
    }

    public void addResult(TestResult result) {
        results.add(result);
    }

    public List<TestResult> getResults() {
        return new ArrayList<>(results);
    }

    public long getTotalExecutionTimeMs() {
        return totalExecutionTimeMs;
    }

    public void setTotalExecutionTimeMs(long totalExecutionTimeMs) {
        this.totalExecutionTimeMs = totalExecutionTimeMs;
    }

    public int getTotalCount() {
        return results.size();
    }

    public int getPassedCount() {
        return (int) results.stream().filter(TestResult::isSuccess).count();
    }

    public int getFailedCount() {
        return (int) results.stream().filter(r -> !r.isSuccess()).count();
    }

    public double getSuccessRate() {
        if (results.isEmpty()) {
            return 0.0;
        }
        return (double) getPassedCount() / getTotalCount() * 100.0;
    }

    @Override
    public String toString() {
        return "TestResults{" +
                "total=" + getTotalCount() +
                ", passed=" + getPassedCount() +
                ", failed=" + getFailedCount() +
                ", successRate=" + String.format("%.2f%%", getSuccessRate()) +
                ", totalExecutionTimeMs=" + totalExecutionTimeMs +
                '}';
    }
}
