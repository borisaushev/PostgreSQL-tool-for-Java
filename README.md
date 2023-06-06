# PostgreSQL-tool-for-Java
PostgreSQL tool for Java

Basicly you need a connection variable to a PostgreSQL database.
you just create a new PSQL object and use it's method for your needs
Here is description of every method:


public void insertValues(String tableName, Object ... values) throws SQLException //inserting all values in table

public void printValues(String tableName) throws SQLException { //printing all values from table

public List<String> executeWithAnswer(String command) throws SQLException { //executing command and getting answer is List of answer-lines
  
public void executeAndPrintAnswer(String command) throws SQLException { //executing command and printing answer
  
public void close() throws SQLException { connection.close(); statement.close();} //closing all connections
 
//need to mention what PSQL class implements AutoCloseable, so you can use it in
  try(PSQL db = new PSQL(connection)) {}
 form and it will be closed automaticly
