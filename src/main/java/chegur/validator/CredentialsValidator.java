package chegur.validator;

import chegur.exception.CredentialsException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CredentialsValidator {
    public static void validateData(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new CredentialsException("Username can`t be empty");
        }

        if (password == null || password.isBlank()) {
            throw new CredentialsException("Password can`t be empty");
        }
    }

    public static void validateData(String username, String password, String confirmedPassword) {
        if (username == null || username.isBlank()) {
            throw new CredentialsException("Username can`t be empty");
        }

        if (password == null || password.isBlank() || confirmedPassword == null || confirmedPassword.isBlank()) {
            throw new CredentialsException("Password can`t be empty");
        }

        if (!password.equals(confirmedPassword)) {
            throw new CredentialsException("Passwords are not equal");
        }

        if (!username.matches("[a-zA-Z0-9]+")) {
            throw new CredentialsException("Username should contain only English letters and numbers");
        }
    }
}
