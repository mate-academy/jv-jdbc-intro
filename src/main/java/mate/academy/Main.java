package mate.academy;

import mate.academy.dao.ConnectionToDB;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        Connection connection = ConnectionToDB.connectToDB();
    }
    // Connection to DB
}
