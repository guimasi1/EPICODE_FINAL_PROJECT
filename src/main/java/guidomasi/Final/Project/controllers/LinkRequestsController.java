package guidomasi.Final.Project.controllers;

import guidomasi.Final.Project.entities.LinkRequest;
import guidomasi.Final.Project.exceptions.BadRequestException;
import guidomasi.Final.Project.payloads.linkRequest.LinkRequestDTO;
import guidomasi.Final.Project.payloads.linkRequest.LinkRequestPutDTO;
import guidomasi.Final.Project.payloads.linkRequest.LinkRequestResponseDTO;
import guidomasi.Final.Project.services.LinkRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/linkRequests")
public class LinkRequestsController {

    @Autowired
    LinkRequestsService linkRequestsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LinkRequestResponseDTO create(@RequestBody @Validated LinkRequestDTO request, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            LinkRequest newRequest = linkRequestsService.save(request);
            return new LinkRequestResponseDTO(newRequest.getId());
        }
    }

    @GetMapping
    public Page<LinkRequest> getLinkRequests(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String orderBy) {
        return linkRequestsService.getLinkRequests(page,size,orderBy);
    }

    @GetMapping("/byPatient/{id}")
    public Page<LinkRequest> getLinkRequestsByPatient(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String orderBy) {
        return linkRequestsService.getLinkRequestsByPatient(id,page,size,orderBy);
    }

    @GetMapping("/byPatientAndStatus/{id}")
    public Page<LinkRequest> getLinkRequestsByPatientAndStatus(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String orderBy) {
        return linkRequestsService.getPendingLinkRequestsByPatient("PENDING",id,page,size,orderBy);
    }
    @PutMapping("/{id}")
    public LinkRequest updateById(@PathVariable UUID id, @RequestBody LinkRequestPutDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
            return linkRequestsService.findByIdAndUpdate(id, body);
        }    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        linkRequestsService.deleteById(id);
    }

    @GetMapping("/{id}")
    public LinkRequest getLinkRequestById(@PathVariable UUID id) {
        return linkRequestsService.findById(id);
    }


    @PatchMapping("/accept/{id}")
    public LinkRequest accept(@PathVariable UUID id) {
        return linkRequestsService.findByIdAndAccept(id);
    }
    @PatchMapping("/reject/{id}")
    public LinkRequest reject(@PathVariable UUID id) {
        return linkRequestsService.findByIdAndReject(id);
    }

}