package algorithms.stack;

import java.util.*;

public class IntervalContinuityChecker {

    // Interval class representing a numeric range [start, end]
    static class Interval {
        int start;
        int end;

        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * Checks if intervals are continuous and non-overlapping using a stack.
     * Returns true if for every consecutive pair: current.start == previous.end + 1.
     */
    public static boolean areIntervalsContinuousWithStack(List<Interval> intervals) {
        // Sort intervals by start value to ensure correct order
        intervals.sort(Comparator.comparingInt(i -> i.start));

        Deque<Interval> stack = new ArrayDeque<>();
        for (Interval interval : intervals) {
            if (stack.isEmpty()) {
                stack.push(interval);
            } else {
                Interval top = stack.peek();

                // Check if current interval starts exactly after the previous one ends (no gaps, no overlaps)
                if (interval.start == top.end + 1) {
                    stack.push(interval);
                } else {
                    // Gap or overlap detected
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        List<Interval> intervals1 = Arrays.asList(
                new Interval(1000, 2000),
                new Interval(2001, 3000),
                new Interval(3001, 4000),
                new Interval(4001, 5000)
        );

        List<Interval> intervals2 = Arrays.asList(
                new Interval(1000, 2000),
                new Interval(2002, 3000), // gap here (should be 2001)
                new Interval(3001, 4000),
                new Interval(4001, 5000)
        );

        System.out.println("Intervals1 continuous? " + areIntervalsContinuousWithStack(intervals1)); // true
        System.out.println("Intervals2 continuous? " + areIntervalsContinuousWithStack(intervals2)); // false
    }
}
