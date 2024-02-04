package guidomasi.Final.Project;

import guidomasi.Final.Project.services.PhysiotherapistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    PhysiotherapistsService physiotherapistsService;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello");

    }

}
