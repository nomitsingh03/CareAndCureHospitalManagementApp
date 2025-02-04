package com.cac.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cac.exception.UserNotFoundException;
import com.cac.dto.LoginDetails;
import com.cac.model.ContactForm;
import com.cac.model.Doctor;
import com.cac.model.Patient;
import com.cac.model.UserInfo;
import com.cac.repository.ContactFormRepository;
import com.cac.repository.DoctorRepository;
import com.cac.repository.PatientRepository;
import com.cac.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ContactFormRepository contactFormRepository;

    public UserInfo createUser(UserInfo userInfo) throws UserNotFoundException {
        userInfo.setRole(userInfo.getRole().toUpperCase());
        UserInfo savedUserInfo = null;
        try{
        savedUserInfo = userRepository.save(userInfo);
        } catch(Exception e){
            throw new UserNotFoundException("Failed to register");
        }
        return  savedUserInfo;
    }


    public UserInfo getUserByUsername(String username) throws UserNotFoundException {
        UserInfo userData = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username:" + username));
        return userData;
    }

    public UserInfo verifyLoginDetails(LoginDetails loginDetails) throws UserNotFoundException {

        // UserInfo userInfo = userRepository.findByUsernameAndPassword(loginDetails.getUsername(), loginDetails.getPassword());
        UserInfo userInfo = userRepository.findByUsernameAndPasswordAndRoleIgnoreCase(loginDetails.getUsername(), loginDetails.getPassword(), loginDetails.getRole());

        if(userInfo!=null) {
            if(loginDetails.getRole().equalsIgnoreCase("patient")){
                Patient patient = patientRepository.findById(Integer.parseInt(loginDetails.getUsername())).orElseThrow(()->new UserNotFoundException("Patient Not Found with Id : "+ loginDetails.getUsername()));
                if(!patient.isActive()) throw new UserNotFoundException("Your account is deactivated .Please contact to Care and Cure.");
            } 
            if(loginDetails.getRole().equalsIgnoreCase("doctor")){
                Doctor doctor = doctorRepository.findByUsername(loginDetails.getUsername()).orElseThrow(()->new UserNotFoundException("Doctor Not Found with username : "+ loginDetails.getUsername()));
                if(!doctor.getStatus()) throw new UserNotFoundException("Your account is deactivated .Please contact to Care and Cure.");
            } 

            return userInfo;
        }
        else {
            throw new UserNotFoundException("Invalid username or password. Try again!");
        }
    }

    public void deleteUser(UserInfo userInfo) {
        userRepository.delete(userInfo);
    }
    
    public UserInfo addDoctor(String username, String password) {
        UserInfo user = new UserInfo(username, password, "DOCTOR");
        return userRepository.save(user);
    }
    
    public boolean updatePasswordByUsername(String username, String newPassword) {
        Optional<UserInfo> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent()) {
            UserInfo user = userOptional.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return true; // Password updated successfully
        }
        
        return false; // User not found
    }

    public ContactForm addContactForm(ContactForm contactForm) {
        return contactFormRepository.save(contactForm);
    }

}
