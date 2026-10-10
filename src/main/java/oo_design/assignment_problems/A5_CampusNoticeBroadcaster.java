package oo_design.assignment_problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The Campus Notice Broadcaster
 *
 * UML relationships:
 *   NotificationChannel <|.. EmailChannel, SmsChannel, AppChannel   realization
 *   Student 1..* --- 1..* NotificationChannel   each student prefers one or more channels
 *   NoticeBoard 1 o-- 0..* Student              aggregation (students exist independently)
 *   NoticeBoard ..> Notice, NotificationChannel depends only on the abstraction, so a new
 *                                               channel (e.g. WhatsApp) needs no change here
 *   Notice validates itself: a title and at least one target department are required.
 */
public class A5_CampusNoticeBroadcaster {

    interface NotificationChannel {
        void send(Student student, String message);
    }

    static class EmailChannel implements NotificationChannel {
        @Override
        public void send(Student student, String message) {
            System.out.println("[Email → " + student.getName() + "] " + message);
        }
    }

    static class SmsChannel implements NotificationChannel {
        @Override
        public void send(Student student, String message) {
            System.out.println("[SMS → " + student.getName() + "] " + message);
        }
    }

    static class AppChannel implements NotificationChannel {
        @Override
        public void send(Student student, String message) {
            System.out.println("[App → " + student.getName() + "] " + message);
        }
    }

    static class Student {
        private final String name;
        private final String department;
        private final List<NotificationChannel> channels;

        Student(String name, String department, List<NotificationChannel> channels) {
            if (channels == null || channels.isEmpty()) {
                throw new IllegalArgumentException("A student must choose at least one channel.");
            }
            this.name = name;
            this.department = department;
            this.channels = Collections.unmodifiableList(new ArrayList<NotificationChannel>(channels));
        }

        String getName() {
            return name;
        }

        String getDepartment() {
            return department;
        }

        List<NotificationChannel> getChannels() {
            return channels;
        }
    }

    static class Notice {
        private final String title;
        private final List<String> targetDepartments;

        Notice(String title, List<String> targetDepartments) {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Cannot post notice: A title is required.");
            }
            if (targetDepartments == null || targetDepartments.isEmpty()) {
                throw new IllegalArgumentException("Cannot post notice: At least one target department is required.");
            }
            this.title = title;
            this.targetDepartments = Collections.unmodifiableList(new ArrayList<String>(targetDepartments));
        }

        String getTitle() {
            return title;
        }

        List<String> getTargetDepartments() {
            return targetDepartments;
        }
    }

    static class NoticeBoard {
        private final List<Student> students = new ArrayList<Student>();

        void register(Student student) {
            students.add(student);
        }

        void post(Notice notice) {
            System.out.println("Notice '" + notice.getTitle() + "' posted to "
                    + String.join(", ", notice.getTargetDepartments()) + ".");
            for (Student s : students) {
                if (notice.getTargetDepartments().contains(s.getDepartment())) {
                    for (NotificationChannel channel : s.getChannels()) {
                        channel.send(s, notice.getTitle());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        NoticeBoard board = new NoticeBoard();
        board.register(new Student("Asha", "CSE",
                Arrays.<NotificationChannel>asList(new EmailChannel(), new AppChannel())));
        board.register(new Student("Ravi", "ECE",
                Arrays.<NotificationChannel>asList(new SmsChannel())));

        board.post(new Notice("Lab Closed Tomorrow", Arrays.asList("CSE")));
        board.post(new Notice("Fee Deadline Extended", Arrays.asList("CSE", "ECE")));

        try {
            board.post(new Notice("Sports Day", new ArrayList<String>()));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
