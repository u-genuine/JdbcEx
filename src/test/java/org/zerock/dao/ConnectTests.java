package org.zerock.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectTests {
    @Test
    public void testConnection() throws Exception{
        //JDBC 드라이버 클래스를 메모리상으로 로딩
        Class.forName("org.mariadb.jdbc.Driver");

        // Connection 인터페이스는 데이터베이스와 네트워크 연결을 의미
        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3308/webdb", //jdbc 프로토콜을 이용, localhost:3308 네트워크 연결정보, webdb 데이터베이스 정보
                "webuser",
                "webuser");

        Assertions.assertNotNull(connection); //데이터베이스와 정상적으로 연결이 도니다면, Connection 타입의 객체는 null이 아님을 확신한다는 의미

        connection.close(); //데이터베이스와 연결 종료
    }

    @Test
    public void testHikariCP() throws Exception{
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://localhost:3308/webdb");
        config.setUsername("webuser");
        config.setPassword("webuser");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource ds = new HikariDataSource(config);
        Connection connection = ds.getConnection();

        System.out.println(connection);

        connection.close();
    }
}
