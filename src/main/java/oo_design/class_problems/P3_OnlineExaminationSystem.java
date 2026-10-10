package oo_design.class_problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Online Examination System
 *
 * UML relationships:
 *   Question <|-- MultipleChoiceQuestion, TrueFalseQuestion, ShortAnswerQuestion   generalization
 *   Examination 1 *-- 1..* Question        composition (questions belong to the exam)
 *   Student 1 --- 0..* Attempt             association
 *   Examination 1 --- 0..* Attempt         association (max one SUBMITTED attempt per student)
 *   Attempt state: IN_PROGRESS -> SUBMITTED (answers frozen after submit)
 *   Attempt only calls Question.isCorrect(), so new question types need no change here.
 */
public class P3_OnlineExaminationSystem {

    abstract static class Question {
        private final int id;
        private final String text;
        private final int points;

        Question(int id, String text, int points) {
            this.id = id;
            this.text = text;
            this.points = points;
        }

        int getId() {
            return id;
        }

        String getText() {
            return text;
        }

        int getPoints() {
            return points;
        }

        /** Each question type evaluates answers its own way. */
        abstract boolean isCorrect(String answer);
    }

    static class MultipleChoiceQuestion extends Question {
        private final String correctOption;

        MultipleChoiceQuestion(int id, String text, int points, String correctOption) {
            super(id, text, points);
            this.correctOption = correctOption;
        }

        @Override
        boolean isCorrect(String answer) {
            return correctOption.equalsIgnoreCase(answer.trim());
        }
    }

    static class TrueFalseQuestion extends Question {
        private final boolean correctValue;

        TrueFalseQuestion(int id, String text, int points, boolean correctValue) {
            super(id, text, points);
            this.correctValue = correctValue;
        }

        @Override
        boolean isCorrect(String answer) {
            return String.valueOf(correctValue).equalsIgnoreCase(answer.trim());
        }
    }

    static class ShortAnswerQuestion extends Question {
        private final String expectedKeyword;

        ShortAnswerQuestion(int id, String text, int points, String expectedKeyword) {
            super(id, text, points);
            this.expectedKeyword = expectedKeyword;
        }

        @Override
        boolean isCorrect(String answer) {
            return answer.toLowerCase().contains(expectedKeyword.toLowerCase());
        }
    }

    static class Student {
        private final String name;

        Student(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    static class Examination {
        private final String name;
        private final List<Question> questions;

        Examination(String name, List<Question> questions) {
            this.name = name;
            this.questions = Collections.unmodifiableList(new ArrayList<Question>(questions));
        }

        String getName() {
            return name;
        }

        List<Question> getQuestions() {
            return questions;
        }

        int getTotalPoints() {
            int total = 0;
            for (Question q : questions) {
                total += q.getPoints();
            }
            return total;
        }
    }

    static class Attempt {
        private final Student student;
        private final Examination exam;
        private final Map<Integer, String> answers = new LinkedHashMap<Integer, String>();
        private boolean submitted = false;

        Attempt(Student student, Examination exam) {
            this.student = student;
            this.exam = exam;
        }

        boolean isSubmitted() {
            return submitted;
        }

        void recordAnswer(Question question, String answer) {
            if (submitted) {
                throw new IllegalStateException("Cannot change answers for a submitted examination.");
            }
            if (!exam.getQuestions().contains(question)) {
                throw new IllegalArgumentException("Question " + question.getId() + " is not part of this exam.");
            }
            answers.put(question.getId(), answer);
        }

        /** Submits the attempt and returns the result summary line. */
        String submit() {
            if (submitted) {
                throw new IllegalStateException("This examination has already been submitted.");
            }
            submitted = true;
            List<String> parts = new ArrayList<String>();
            int score = 0;
            for (Question q : exam.getQuestions()) {
                String answer = answers.get(q.getId());
                if (answer == null) {
                    parts.add("Question " + q.getId() + ": Unanswered (0 points)");
                } else if (q.isCorrect(answer)) {
                    score += q.getPoints();
                    parts.add("Question " + q.getId() + ": Correct (" + q.getPoints() + " points)");
                } else {
                    parts.add("Question " + q.getId() + ": Incorrect (0 points)");
                }
            }
            return "Result: " + String.join(", ", parts) + ". Total score: " + score + "/" + exam.getTotalPoints() + ".";
        }
    }

    static class ExamService {
        private final Map<String, Attempt> attempts = new HashMap<String, Attempt>();

        private String key(Student s, Examination e) {
            return s.getName() + "|" + e.getName();
        }

        Attempt start(Student student, Examination exam) {
            Attempt existing = attempts.get(key(student, exam));
            if (existing != null && existing.isSubmitted()) {
                System.out.println(student.getName() + " has already submitted " + exam.getName() + ".");
                return null;
            }
            if (existing != null) {
                return existing;
            }
            Attempt attempt = new Attempt(student, exam);
            attempts.put(key(student, exam), attempt);
            System.out.println(exam.getName() + " started by " + student.getName() + ".");
            return attempt;
        }
    }

    public static void main(String[] args) {
        Question q1 = new MultipleChoiceQuestion(1, "Which option is correct?", 5, "C");
        Question q2 = new TrueFalseQuestion(2, "Java supports multiple class inheritance.", 5, false);
        Examination examA = new Examination("Exam A", java.util.Arrays.asList(q1, q2));
        Student s1 = new Student("Student 1");
        ExamService service = new ExamService();

        Attempt attempt = service.start(s1, examA);
        attempt.recordAnswer(q1, "C");
        System.out.println("Answer recorded for Question 1.");
        attempt.recordAnswer(q2, "True");
        System.out.println("Answer recorded for Question 2.");

        String result = attempt.submit();
        System.out.println(examA.getName() + " submitted by " + s1.getName() + ".");
        System.out.println(result);

        try {
            attempt.recordAnswer(q1, "A");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        service.start(s1, examA); // only one submitted attempt allowed
    }
}
