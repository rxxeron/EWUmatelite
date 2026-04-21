package com.ewumatelite.core.utils;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class MarksCalculator {
    public static double calculateQuizValue(JSONArray qArr, String strategy, int n, double maxMark) {
        if (qArr == null || qArr.length() == 0) return 0.0;
        List<Double> marks = new ArrayList<>();
        for (int i = 0; i < qArr.length(); i++) {
            marks.add(qArr.optDouble(i, 0.0));
        }
        marks.sort(Collections.reverseOrder());
        double total = 0.0;
        if ("best_one".equals(strategy)) {
            total = marks.get(0);
        } else if ("best_n".equals(strategy)) {
            for (int i = 0; i < n && i < marks.size(); i++) total += marks.get(i);
        } else if ("average_n".equals(strategy) || "n_average".equals(strategy)) {
            double sum = 0;
            int count = 0;
            for (int i = 0; i < n && i < marks.size(); i++) {
                sum += marks.get(i);
                count++;
            }
            total = count > 0 ? sum / count : 0;
        } else if ("average_all".equals(strategy)) {
            double sum = 0;
            for (Double mark : marks) sum += mark;
            total = marks.size() > 0 ? sum / marks.size() : 0;
        } else if ("sum_all".equals(strategy)) {
            for (Double mark : marks) total += mark;
        }
        return total > maxMark ? maxMark : total;
    }
    public static String calculateGrade(double obtained, double distributed) {
        if (distributed <= 0) return "N/A";
        double percentage = (obtained / distributed) * 100;
        if (percentage >= 97) return "A+";
        if (percentage >= 90) return "A";
        if (percentage >= 87) return "A-";
        if (percentage >= 83) return "B+";
        if (percentage >= 80) return "B";
        if (percentage >= 77) return "B-";
        if (percentage >= 73) return "C+";
        if (percentage >= 70) return "C";
        if (percentage >= 67) return "C-";
        if (percentage >= 63) return "D+";
        if (percentage >= 60) return "D";
        return "F";
    }
}
