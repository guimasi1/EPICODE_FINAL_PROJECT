package guidomasi.Final.Project.controllers;

import guidomasi.Final.Project.entities.Admin;
import guidomasi.Final.Project.entities.Patient;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admins")
public class AdminsController {
    @GetMapping("/me")
    public Admin getMyProfile(@AuthenticationPrincipal Admin myUser) {
        return myUser;
    }

}
