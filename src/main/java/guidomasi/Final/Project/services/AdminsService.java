package guidomasi.Final.Project.services;

import guidomasi.Final.Project.entities.Admin;
import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.exceptions.NotFoundException;
import guidomasi.Final.Project.repositories.AdminsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdminsService {
    @Autowired
    AdminsDAO adminsDAO;

    public Admin findByEmail(String email) throws NotFoundException
    {
        return adminsDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Admin with email " + email + " not found!"));
    }
    public Admin findById(UUID id) {
        return adminsDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

}
