package guidomasi.Final.Project.controllers;

import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.services.PhysiotherapistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/physiotherapists")
public class PhysiotherapistsController {
    @Autowired
    PhysiotherapistsService physiotherapistsService;

    @GetMapping
    public Page<Physiotherapist> getPhysiotherapists(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String id) {
        return physiotherapistsService.getPhysiotherapists(page, size, id);
    }
}
