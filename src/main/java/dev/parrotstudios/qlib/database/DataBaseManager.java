package dev.parrotstudios.qlib.database;

import com.google.gson.Gson;

import java.sql.Connection;

public class DataBaseManager {
    private static final DataBaseManager instance = new DataBaseManager();

    private final Gson gson = new Gson();
    private Connection connection;

    private DataBaseManager() {

    }

    public void load(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {}
    }
}
