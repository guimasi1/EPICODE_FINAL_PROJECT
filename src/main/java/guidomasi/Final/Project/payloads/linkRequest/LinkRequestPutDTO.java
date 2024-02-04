package guidomasi.Final.Project.payloads.linkRequest;

import java.util.UUID;

public record LinkRequestPutDTO(String requestStatus, UUID physiotherapist_id, UUID patient_id) {
}
