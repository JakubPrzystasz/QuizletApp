package pl.jp.quizletapp.services;

import java.util.List;

import lombok.Data;
import pl.jp.quizletapp.models.Answer;
import pl.jp.quizletapp.models.Question;
import pl.jp.quizletapp.models.User;

@Data
public class SessionDTO {
    private Long id;

    private User user;

    private List<Question> assignedQuestions;

    private List<Answer> answerList;

    private Integer lecture;

    private Integer result;

    private Integer totalPoints;
}
