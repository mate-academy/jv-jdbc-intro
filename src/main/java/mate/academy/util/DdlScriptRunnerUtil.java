package mate.academy.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;

public class DdlScriptRunnerUtil {
    private DdlScriptRunnerUtil() {
    }

    public static void runScript(String fileName) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            InputStream inputStream = FileReaderUtil.read(fileName);
            InputStreamReader reader = new InputStreamReader(inputStream);
            ScriptRunner runner = new ScriptRunner(connection);
            runner.runScript(reader);
        } catch (SQLException e) {
            throw new RuntimeException("Can't run script " + fileName, e);
        }

    }
}
