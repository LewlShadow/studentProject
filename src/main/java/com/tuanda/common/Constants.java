package com.tuanda.common;

/**
 * @prOjEct studentProject-main
 * @DAtE 4/2/2024
 * @tImE 10:08 AM
 * @AUthOr tuanda52
 */
public class Constants {

    public interface Role {
        public static final String
                USER = "USER";

    }


    public interface Message {
        public static final String
                INVALID_USERNAME = "INVALID_USERNAME: must not be empty, can only contain letters and numbers, length between 5-20 characters",
                INVALID_PASSWORD = "INVALID_PASSWORD: must not be empty, length between 8-20 characters, and must include at least one uppercase letter, one lowercase letter, one number, and one special character",
                INVALID_EMAIL = "INVALID_EMAIL: must not be empty, must be in a valid email format",
                EXIST_USERNAME = "Username: %s is already taken",
                EXIST_EMAIL = "Email: %s is already taken",
                USER_ID_IS_NOT_EXIST = "The ID is not exist";
    }
}
