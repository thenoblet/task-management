package gtp.taskmanagement.util;

import gtp.taskmanagement.model.Task;

import java.util.Arrays;

public class Util {
    public static boolean isValidPriority(String value) {
        return Arrays.stream(Task.Priority.values())
                .anyMatch(priority -> priority.name().equalsIgnoreCase(value));
    }

    public static boolean isValidStatus(String value) {
        if (value == null || value.trim().isEmpty()) return false;
        try {
            Task.Status.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
