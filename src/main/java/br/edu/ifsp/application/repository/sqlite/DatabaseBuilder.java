package br.edu.ifsp.application.repository.sqlite;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBuilder {

    public void buildDatabaseIfMissing() {
        if (isDatabaseMissing()) {
            System.out.println("Database is missing. Building database: \n");
            buildTables();
        }
    }

    private boolean isDatabaseMissing() {
        return !Files.exists(Paths.get("database.db"));
    }

    private void buildTables() {
        try (Statement statement = ConnectionFactory.createStatement()) {
            statement.addBatch(createBookTable());
            statement.addBatch(createUserTable());
            statement.addBatch(createTransactionTable());
            statement.executeBatch();

            System.out.println("Database successfully created.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private String createBookTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE Book (\n");
        builder.append("id INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("name TEXT NOT NULL, \n");
        builder.append("authors TEXT NOT NULL, \n");
        builder.append("publisher TEXT NOT NULL, \n");
        builder.append("isbn TEXT NOT NULL UNIQUE, \n");
        builder.append("edition INTEGER NOT NULL, \n");
        builder.append("num_of_pages INTEGER NOT NULL, \n");
        builder.append("genre TEXT NOT NULL, \n");
        builder.append("status TEXT NOT NULL \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createUserTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE User (\n");
        builder.append("id TEXT NOT NULL PRIMARY KEY, \n");
        builder.append("name TEXT NOT NULL, \n");
        builder.append("email TEXT NOT NULL UNIQUE, \n");
        builder.append("phone TEXT NOT NULL, \n");
        builder.append("course TEXT, \n");
        builder.append("department TEXT \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createTransactionTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE BookTransaction (\n");
        builder.append("id INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("user TEXT NOT NULL, \n");
        builder.append("book INTEGER NOT NULL, \n");
        builder.append("issue_date TEXT NOT NULL, \n");
        builder.append("due_date TEXT NOT NULL, \n");
        builder.append("return_date TEXT, \n");
        builder.append("FOREIGN KEY(user) REFERENCES User(id),\n");
        builder.append("FOREIGN KEY(book) REFERENCES Book(id)\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }
}
