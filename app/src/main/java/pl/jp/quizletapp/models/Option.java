package pl.jp.quizletapp.models;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    private Long id;

    private String content;

    private boolean correct;
}