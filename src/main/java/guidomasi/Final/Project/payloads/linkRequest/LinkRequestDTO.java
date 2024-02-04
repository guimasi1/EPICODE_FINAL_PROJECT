package guidomasi.Final.Project.payloads.linkRequest;

import java.util.UUID;

public record LinkRequestDTO (UUID physiotherapist_id, UUID patient_id)  {
}
