package pl.jp.quizletapp.models;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {
    private Long id;

    private String title;

    private String description;

    private List<Question> questionList;

}