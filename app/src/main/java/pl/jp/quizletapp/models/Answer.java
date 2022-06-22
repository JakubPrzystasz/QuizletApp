package pl.jp.quizletapp.models;

import lombok.Data;

@Data
public class Answer {
    private Long id;

    private Session session;

    private Option option;
}

