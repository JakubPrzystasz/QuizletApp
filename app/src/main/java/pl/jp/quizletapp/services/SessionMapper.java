package pl.jp.quizletapp.services;

import pl.jp.quizletapp.models.Lecture;
import pl.jp.quizletapp.models.Session;

public class SessionMapper {
    public static Session toDto(SessionDTO dto, Integer lectrueId) {
        Lecture lecture = new Lecture();
        return new Session(
                dto.getId(),
                dto.getUser(),
                dto.getAssignedQuestions(),
                dto.getAnswerList(),
                lecture,
                dto.getResult(),
                dto.getTotalPoints()
        );
    }
}
