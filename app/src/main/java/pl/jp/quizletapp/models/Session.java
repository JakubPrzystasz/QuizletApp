package pl.jp.quizletapp.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private Long id;

    private User user;

    private List<Question> assignedQuestions;

    private List<Answer> answerList;

    private Lecture lecture;

    private Integer result;

    private Integer totalPoints;
}
