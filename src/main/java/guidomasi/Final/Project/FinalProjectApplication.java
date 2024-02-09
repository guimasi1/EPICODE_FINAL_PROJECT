package guidomasi.Final.Project;

import guidomasi.Final.Project.entities.ExercisesAssignment;
import guidomasi.Final.Project.security.JWTAuthFilter;
import guidomasi.Final.Project.services.ExercisesAssignmentsService;
import guidomasi.Final.Project.services.ExercisesDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinalProjectApplication {


	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
		System.out.println("Hello");
	}

}
