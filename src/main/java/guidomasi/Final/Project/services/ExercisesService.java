package guidomasi.Final.Project.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import guidomasi.Final.Project.entities.Exercise;
import guidomasi.Final.Project.enums.DifficultyLevel;
import guidomasi.Final.Project.exceptions.NotFoundException;
import guidomasi.Final.Project.payloads.exercise.ExercisesPutDTO;
import guidomasi.Final.Project.payloads.exercise.NewExerciseDTO;
import guidomasi.Final.Project.payloads.exerciseDetails.ExerciseDetailsDTO;
import guidomasi.Final.Project.repositories.ExercisesDAO;
import org.apache.commons.lang3.builder.Diff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ExercisesService {
    @Autowired
    ExercisesDAO exercisesDAO;

    @Autowired
    Cloudinary cloudinary;

    public Exercise findByName(String name) {
        return exercisesDAO.findByName(name).orElseThrow(() -> new NotFoundException("Exercise with name " + name + " not found!"));

    }

    public Page<Exercise> getExercises(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return exercisesDAO.findAll(pageable);
    }
    public Page<Exercise> getExercisesByName(String name,int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return exercisesDAO.findByNameContainingIgnoreCase(name,pageable);
    }

    public Page<Exercise> getExercisesByParams(String name, String targetArea, DifficultyLevel difficultyLevel, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        return exercisesDAO.findExercisesByParams(name, targetArea, difficultyLevel, pageable);
    }

    public Exercise saveExercise(NewExerciseDTO body) {
        Exercise exercise = new Exercise();
        exercise.setName(body.name());
        exercise.setDescription(body.description());
        exercise.setTargetArea(body.targetArea());
        DifficultyLevel difficultyLevel = DifficultyLevel.valueOf(body.difficultyLevel().toUpperCase());
        exercise.setDifficultyLevel(difficultyLevel);
        return exercisesDAO.save(exercise);
    }
    public Exercise findById(UUID id) {
        return exercisesDAO.findById(id).orElseThrow(() -> new NotFoundException(id));

    }

    public Exercise findByIdAndUpdate(UUID uuid, ExercisesPutDTO body) {
        Exercise found = this.findById(uuid);
        found.setName(body.name());
        found.setDescription(body.description());
        DifficultyLevel difficultyLevel = DifficultyLevel.valueOf(body.difficultyLevel().toUpperCase());
        found.setDifficultyLevel(difficultyLevel);
        found.setImageUrl(body.imageUrl());
        found.setVideoUrl(body.videoUrl());
        found.setTargetArea(body.targetArea());
        return exercisesDAO.save(found);
    }

    public void deleteById(UUID uuid) {
        Exercise found = this.findById(uuid);
        exercisesDAO.delete(found);
    }

    public Exercise uploadPicture(UUID id, MultipartFile file) throws IOException {
        Exercise exercise = exercisesDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        exercise.setImageUrl(url);
        exercisesDAO.save(exercise);
        return exercise;
    }
    public long getTotalExercisesCount() {
        return exercisesDAO.count();
    }
    public long getTotalByTargetArea(String targetArea) {
        return exercisesDAO.countByTargetArea(targetArea);
    }
    public long getTotalByDifficultyLevel(DifficultyLevel difficulty) {
        return exercisesDAO.countByDifficultyLevel(difficulty);
    }

}
