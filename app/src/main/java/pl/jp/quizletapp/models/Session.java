package pl.jp.quizletapp.models;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Session {
    private Long id;

    private User user;

    private List<Question> assignedQuestions;

    private List<Answer> answerList;

    private Lecture lecture;
}
