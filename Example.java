//Simple example on the code

import java.sql.*;

public class Example {

    public static void main(String[] args) throws SQLException {

        //Creating connection do database
        Connection connection  = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres", "123");

        //creating variable of PSQL class
        PSQL db = new PSQL(connection);


        //the first variable is table's name, others are values in order of created table
        //"mikle" - name(text)  15 - age(Integer) 3 - code(Integer)
        db.insertValues("people", "mikle", 15,3 );
        db.printValues("people");
    }
}

/*
    Output:

    name age code
    mikle 15 3

*/