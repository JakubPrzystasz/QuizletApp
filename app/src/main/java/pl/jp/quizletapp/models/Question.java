package pl.jp.quizletapp.models;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private Long id;

    private String content;

    private QuestionType type;

    private List<Option> optionList;

    private List<QuestionMedia> mediaList;

}