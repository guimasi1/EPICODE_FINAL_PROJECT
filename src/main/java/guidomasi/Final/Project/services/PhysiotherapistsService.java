package guidomasi.Final.Project.services;

import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.exceptions.NotFoundException;
import guidomasi.Final.Project.repositories.PhysiotherapistsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PhysiotherapistsService {
    @Autowired
    PhysiotherapistsDAO physiotherapistsDAO;

    public Page<Physiotherapist> getPhysiotherapists(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return physiotherapistsDAO.findAll(pageable);
    }

    public Physiotherapist findByEmail(String email) throws NotFoundException {
        return physiotherapistsDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Physiotherapist with email " + email + " not found!"));
    }
    public Physiotherapist findById(UUID id) throws NotFoundException {
        return physiotherapistsDAO.findById(id).orElseThrow(() -> new NotFoundException("Physiotherapist with id " + id + " not found!"));
    }


}
