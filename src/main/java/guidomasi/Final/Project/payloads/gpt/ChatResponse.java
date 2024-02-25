package guidomasi.Final.Project.payloads.gpt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class ChatResponse {

    private List<Choice> choices;


    @Getter
    @Setter
    @AllArgsConstructor
    public static class Choice {

        private int index;
        private Message message;

        }
}