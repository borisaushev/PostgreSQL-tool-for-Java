import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        Connection connection  = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres", "123");

        PSQL db = new PSQL(connection);

        //db.insertValues("people", "mikle", 15,3 );
        db.printValues("people");
    }
}

class PSQL {

    private Connection connection;
    private Statement statement;

    public PSQL(Connection con) throws SQLException {
        connection = con;
        statement = connection.createStatement();
    }

    public void insertValues(String tableName, Object ... values) throws SQLException {

        tableName.matches("\\w+");

        StringBuilder build = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
        for(var value : values) {

            if(value instanceof String)
                build.append("'" + value + "', ");
            else
                build.append( value + ", ");

        }
        build.deleteCharAt(build.length() - 2);
        build.append(")");

        execute(build.toString());

    }

    public void printValues(String tableName) throws SQLException {

        ResultSet result = statement.executeQuery("SELECT * FROM " + tableName);

        int count = result.getMetaData().getColumnCount();

        for(int i = 1; i <= count; i++)
            System.out.print(result.getMetaData().getColumnName(i) + " ");
        System.out.println();


        while(result.next()) {
            for (int i = 1; i <= count; i++)
                System.out.print(result.getObject(i) + " ");
            System.out.println();
        }
    }

    public void execute(String command) throws SQLException {
        statement.execute(command);
    }

}