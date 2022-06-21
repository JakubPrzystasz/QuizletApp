package pl.jp.quizletapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionMedia {

    private Long id;

    private byte[] data;

    private MediaType type;
}
