public class PSQL implements AutoCloseable {

    private Connection connection;
    private Statement statement;

    public PSQL(Connection con) throws SQLException {
        connection = con;
        statement = connection.createStatement();
    }

    public void insertValues(String tableName, Object ... values) throws SQLException {

        if(!tableName.matches("\\w+")) //injection protection
            throw new SQLException("Illegal table name");

        StringBuilder build = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
        for(var value : values) {
            if(!value.toString().contains(";")) //injection protection
                throw new SQLException("Illegal value");

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
        StringBuilder build = new StringBuilder();

        for(int i = 1; i <= count; i++)     //appending columns names in StringBuilder
            build.append(result.getMetaData().getColumnName(i) + " ");
        build.append("\n");


        while(result.next()) {  //printng values in StringBuilder
            for (int i = 1; i <= count; i++)
                build.append(result.getObject(i) + " ");
            build.append("\n");
        }

        System.out.println(build); //printing StringBuilder

    }

    public void execute(String command) throws SQLException {
        statement.execute(command);
    }

    public ResultSet executeWithAnswer(String command) throws SQLException {
        return statement.executeQuery(command);
    }

    public void close() throws SQLException { connection.close(); statement.close();}

}
