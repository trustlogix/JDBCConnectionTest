Run Command

Mac/Linux
java -cp .:./snowflake-jdbc-3.13.29.jar SnowflakeService <username> <password> <hostname_without_snowflakecomputing.com>

Windows
java -cp .;./snowflake-jdbc-3.13.29.jar SnowflakeService <username> <password> <hostname_without_snowflakecomputing.com>


Examples : java -cp .:./snowflake-jdbc-3.13.29.jar SnowflakeService tst test jefferies-aws_shared_nprod


If Java Throws Version issue. 
1> rm SnowflakeService.class file
2> run javac SnowflakeService.java
3> run above java command again