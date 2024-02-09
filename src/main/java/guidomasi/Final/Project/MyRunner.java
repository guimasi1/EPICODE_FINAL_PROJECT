package guidomasi.Final.Project;

import guidomasi.Final.Project.entities.ExercisesAssignment;
import guidomasi.Final.Project.services.ExercisesAssignmentsService;
import guidomasi.Final.Project.services.ExercisesDetailsService;
import guidomasi.Final.Project.services.PhysiotherapistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    PhysiotherapistsService physiotherapistsService;
    @Autowired
    ExercisesDetailsService exercisesDetailsService;
    @Autowired
    ExercisesAssignmentsService exercisesAssignmentsService;
    @Override
    public void run(String... args) throws Exception {
    }

}
