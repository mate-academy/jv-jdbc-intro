package mate.academy;

import mate.academy.connectionUtil.ConnectionUtil;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            ConnectionUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
