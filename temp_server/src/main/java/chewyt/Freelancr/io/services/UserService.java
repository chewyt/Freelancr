package chewyt.Freelancr.io.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chewyt.Freelancr.io.models.User;
import chewyt.Freelancr.io.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;

    public Optional<Integer> addUser(User u){

        if(userRepo.hasUser(u.getEmail())){
            // System.out.println("Email for checks>>>>>>>"+u.getEmail());
            return Optional.of(401);

        }
        
        if (userRepo.addUser(u))
            return Optional.empty();
        
        return Optional.of(400);
    }

    public Optional<User> authUser(String email, String password){
        if(!userRepo.authUser(email,password)){
            return Optional.empty();
        }
        else{
            return Optional.ofNullable(userRepo.getUser(email,password));
        }
    }
}
