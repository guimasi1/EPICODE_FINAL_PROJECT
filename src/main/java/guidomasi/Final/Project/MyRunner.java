package guidomasi.Final.Project;

import guidomasi.Final.Project.authentication.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    AuthService authService;
    @Override
    public void run(String... args) throws Exception {
       /* Admin admin = new Admin("admin-physio@gmail.com","1234","Guido", "Masi", Role.ADMIN);
        NewAdminDTO adminDTO = new NewAdminDTO(admin.getEmail(),admin.getPassword(), admin.getFirstName(), admin.getLastName());
        authService.saveAdmin(adminDTO);*/
    }

}
