package guidomasi.Final.Project.services;

import guidomasi.Final.Project.entities.ExerciseDetails;
import guidomasi.Final.Project.entities.ExercisesAssignment;
import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.enums.AssignmentStatus;
import guidomasi.Final.Project.payloads.exerciseDetails.ExerciseDetailsDTO;
import guidomasi.Final.Project.payloads.exercisesAssignment.ExercisesAssignmentDTO;
import guidomasi.Final.Project.repositories.ExerciseDetailsDAO;
import guidomasi.Final.Project.repositories.ExercisesAssignmentsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ExercisesAssignmentsService {
    @Autowired
    ExercisesAssignmentsDAO exercisesAssignmentsDAO;
    @Autowired
    PhysiotherapistsService physiotherapistsService;
    @Autowired
    PatientsService patientsService;
    @Autowired
    ExercisesDetailsService exercisesDetailsService;
    public Page<ExercisesAssignment> getExercisesAssignments(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return exercisesAssignmentsDAO.findAll(pageable);
    }

    public ExercisesAssignment save(ExercisesAssignmentDTO body) {
        ExercisesAssignment exercisesAssignment = new ExercisesAssignment();
        exercisesAssignment.setNotes(body.notes());
        exercisesAssignment.setAssignmentDate(LocalDate.now());
        exercisesAssignment.setAssignmentStatus(AssignmentStatus.ASSIGNED);
        Physiotherapist physiotherapist = physiotherapistsService.findById(body.physiotherapist_id());
        Patient patient = patientsService.findById(body.patient_id());
        exercisesAssignment.setAssignedBy(physiotherapist);
        exercisesAssignment.setPatient(patient);

        return exercisesAssignmentsDAO.save(exercisesAssignment);
    }
}
