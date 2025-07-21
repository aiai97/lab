package algorithms.dp;

import java.util.*;

public class MeetingScheduler {
    public static void main(String[] args) {
        int[][] meetings = {
                {9, 10}, {9, 10}, {10, 11},
                {11, 12}, {11, 12}, {12, 13}
        };

        int rooms = minMeetingRooms(meetings);
        System.out.println("the least count of meeting room will be：" + rooms);
    }

    public static int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int[] interval : intervals) {
            if (!heap.isEmpty() && interval[0] >= heap.peek()) {
                heap.poll();
            }
            heap.offer(interval[1]);
        }
        return heap.size();
    }
}
