package guidomasi.Final.Project.config;

import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.payloads.EmailDTO;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    private String mailgunApiKey;
    private String mailgunDomainname;

    @Value("${mailgun.maildefault}")
    private String mailFrom;

    public EmailSender(@Value("${mailgun.apikey}") String mailgunApiKey,
                       @Value("${mailgun.domainname}") String mailgunDomainName) {
        this.mailgunApiKey = mailgunApiKey;
        this.mailgunDomainname = mailgunDomainName;
    }


    public void sendEmail(Physiotherapist recipient, EmailDTO emailDTO) {

        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.mailgunDomainname + "/messages")
                .basicAuth("api", this.mailgunApiKey)
                .queryString("from", this.mailFrom)
                .queryString("to", recipient.getEmail())
                .queryString("subject", emailDTO.subject())
                .queryString("text", emailDTO.content())
                .asJson();
        System.out.println(response);
    }
    public void sendRegistrationEmailToPhysio(String recipient) {

        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.mailgunDomainname + "/messages")
                .basicAuth("api", this.mailgunApiKey)
                .queryString("from", this.mailFrom)
                .queryString("to", recipient)
                .queryString("subject", "Congratulation! Registration completed.")
                .queryString("text", "Welcome to our platform. Please feel free to ask any question.")
                .asJson();
        System.out.println(response);
    }
}
