package guidomasi.Final.Project.controllers;

import guidomasi.Final.Project.entities.Exercise;
import guidomasi.Final.Project.enums.DifficultyLevel;
import guidomasi.Final.Project.repositories.ExercisesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class CSVController {

    @Autowired
    ExercisesDAO exercisesDAO;
    @PostMapping("/uploadExercises")
    public String uploadExercises(@RequestParam("exercisesFile") MultipartFile file) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                String[] data = line.split(";");
                if(data.length >= 6) {
                    String name = data[0];
                    String description = data[1];
                    String imageUrl= data[2];
                    String videoUrl = data[3];
                    String targetArea = data[4];
                    String difficultyLevel = data[5];

                    Exercise exercise = new Exercise();
                    exercise.setName(name);
                    exercise.setDescription(description);
                    exercise.setImageUrl(imageUrl);
                    exercise.setVideoUrl(videoUrl);
                    exercise.setTargetArea(targetArea);
                    exercise.setDifficultyLevel(DifficultyLevel.valueOf(difficultyLevel.toUpperCase()));
                    exercisesDAO.save(exercise);
                }else{
                    System.out.println("Riga non valida " + line);
                }
            }
            return "Success!";
        } catch (IOException e){
            e.printStackTrace();
            return "Something went wrong";
        }
    }
}
