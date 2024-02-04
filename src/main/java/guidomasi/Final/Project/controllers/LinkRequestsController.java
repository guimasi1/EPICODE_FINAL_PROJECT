package guidomasi.Final.Project.controllers;

import guidomasi.Final.Project.entities.Exercise;
import guidomasi.Final.Project.entities.LinkRequest;
import guidomasi.Final.Project.exceptions.BadRequestException;
import guidomasi.Final.Project.payloads.exercise.ExerciseResponseDTO;
import guidomasi.Final.Project.payloads.exercise.NewExerciseDTO;
import guidomasi.Final.Project.payloads.linkRequest.LinkRequestDTO;
import guidomasi.Final.Project.payloads.linkRequest.LinkRequestResponseDTO;
import guidomasi.Final.Project.services.ExercisesService;
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
            @RequestParam(defaultValue = "id") String id) {
        return linkRequestsService.getLinkRequests(page,size,id);
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