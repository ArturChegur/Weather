package chegur.validator;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CredentialsValidator {
    public static String isDataValid(String username, String password) {
        if (username == null || username.isBlank()) {
            return "Username can`t be empty";
        }

        if (password == null || password.isBlank()) {
            return "Password can`t be empty";
        }

        return null;
    }

    public static String isDataValid(String username, String password, String confirmedPassword) {
        if (username == null || username.isBlank()) {
            return "Username can`t be empty";
        }

        if (password == null || password.isBlank() || confirmedPassword == null || confirmedPassword.isBlank()) {
            return "Password can`t be empty";
        }

        if (!password.equals(confirmedPassword)) {
            return "Passwords are not equal";
        }

        if (!username.matches("[a-zA-Z0-9]+")) {
            return "Username should contain only English letters and numbers";
        }

        return null;
    }
}
