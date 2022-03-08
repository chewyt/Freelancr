package chewyt.Freelancr.io.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chewyt.Freelancr.io.models.Project;
import chewyt.Freelancr.io.repositories.ProjectRepository;

@Service
public class ProjectService {
    
        
    @Autowired
    private ProjectRepository projectRepo;

    
    public Optional<Integer> addProject(Project p){
   
        if (projectRepo.addProject(p))
            return Optional.empty();
        
        return Optional.of(400);
    }

    
}
