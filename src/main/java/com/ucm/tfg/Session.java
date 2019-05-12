package com.ucm.tfg;

import com.ucm.tfg.entities.User;

/**
 * This class has the purpose of setting session parameters for sharedpreferences
 * It also saves a user instance as the user is logged
 */
public class Session {

    public final static String SESSION_FILE = "SESSION_FILE";
    public final static String IS_LOGGED = "IS_LOGGED";
    public final static String USER = "SESSION_USER";

    public static User user;
}
