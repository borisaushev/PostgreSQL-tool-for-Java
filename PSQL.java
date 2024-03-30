import java.sql.*;
import java.util.LinkedList;
import java.util.List;

//Я надеюсь никто нмкогда не узнает о существовании этого кошмара
public class PSQL implements AutoCloseable {

    private final Connection connection;
    private final Statement statement;

    public PSQL(Connection con) throws SQLException {
        connection = con;
        statement = connection.createStatement();
    }

    public void insertValues(String tableName, Object ... values) throws SQLException { //inserting all values in table 

        if(!tableName.matches("\\w+")) //checking table name on injection
            throw new SQLException("Illegal table name");

        StringBuilder build = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
        for(var value : values) {
            if(!value.toString().contains(";")) // checking values on injection
                throw new SQLException("Illegal value");

            if(value instanceof String)
                build.append("'").append(value).append("', ");
            else
                build.append(value).append(", ");

        }
        build.deleteCharAt(build.length() - 2);
        build.append(")");

        execute(build.toString()); //executing final command with our own method

    }

    public void printValues(String tableName) throws SQLException { //printing all values from table

        ResultSet result = statement.executeQuery("SELECT * FROM " + tableName);

        int count = result.getMetaData().getColumnCount();
        StringBuilder build = new StringBuilder();

        for(int i = 1; i <= count; i++)
            build.append(result.getMetaData().getColumnName(i)).append(" ");
        build.append("\n");


        while(result.next()) {
            for (int i = 1; i <= count; i++)
                build.append(result.getObject(i)).append(" ");
            build.append("\n");
        }

        System.out.println(build);

    }

    public List<String> executeWithAnswer(String command) throws SQLException { //executing command and getting answer is List of anser-lines

        LinkedList<String> list = new LinkedList<>();
        StringBuilder build = new StringBuilder();

        ResultSet result = statement.executeQuery(command);
        int count = result.getMetaData().getColumnCount();

        while(result.next()) {
            for (int i = 1; i <= count; i++)
                build.append(result.getObject(i)).append(" ");
            list.add(build.toString());
            build.delete(0, build.length());
        }

        return list;

    }

    public void executeAndPrintAnswer(String command) throws SQLException { //executing command and printing answer
        List<String> list = executeWithAnswer(command);
        for(String str : list)
            System.out.println(str);
    }

    public void execute(String command) throws SQLException { //executing command
        statement.execute(command);
    }

    public void close() throws SQLException { connection.close(); statement.close();} //closing all connections

}





























