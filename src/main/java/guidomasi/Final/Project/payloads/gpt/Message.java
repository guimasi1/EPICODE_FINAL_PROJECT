package guidomasi.Final.Project.payloads.gpt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Message {

    private String role;
    private String content;

}