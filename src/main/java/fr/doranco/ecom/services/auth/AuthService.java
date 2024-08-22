package fr.doranco.ecom.services.auth;

import fr.doranco.ecom.dto.SignupRequest;
import fr.doranco.ecom.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);

    void createAdminAccount();
}
