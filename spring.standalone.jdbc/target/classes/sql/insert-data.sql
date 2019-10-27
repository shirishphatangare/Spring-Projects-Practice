INSERT INTO employees VALUES (1, 'mkyong');
INSERT INTO employees VALUES (2, 'alex');
INSERT INTO employees VALUES (3, 'joel');


/*CREATE OR REPLACE ALIAS getEmp(
        the_id IN INT,
        the_name OUT VARCHAR(128))
    AS
    BEGIN

        SELECT NAME INTO the_name from employees WHERE ID = the_id;

    END;	
*/    

/* H2 way of writih Proceduere - H2 is not one of the databases fully supported for procedure calls -- supported are: [Apache Derby, DB2, MySQL, Microsoft SQL Server, Oracle, PostgreSQL, Sybase] */ 

DROP ALIAS IF EXISTS MYFUNCTION;
CREATE ALIAS MYFUNCTION AS '
String getTableContent(java.sql.Connection con) throws Exception {
    String resultValue=null;
    java.sql.ResultSet rs = con.createStatement().executeQuery(
    " SELECT name FROM EMPLOYEES where id = 3");
       while(rs.next())
       {
        resultValue=rs.getString(1);
       }
    return resultValue;
}
';   
    
    