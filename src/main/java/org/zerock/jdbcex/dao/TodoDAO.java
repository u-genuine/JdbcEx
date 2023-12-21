package org.zerock.jdbcex.dao;

import lombok.Cleanup;
import org.zerock.jdbcex.domain.TodoVO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


// DAO는 데이터베이스의 접근과 처리를 전담하는 객체
// 주로 VO 단위로 처리함
public class TodoDAO {

    public String getTime(){
        String now = null;

        try(Connection connection = ConnectionUtil.INSTANCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select now()");
            ResultSet resultSet = preparedStatement.executeQuery();
            ){

            resultSet.next();

            now = resultSet.getString(1);
        } catch(Exception e){
            e.printStackTrace();
        }
        return now;
    }

    public String getTime2() throws Exception {

        //@Cleanup이 추가된 변수는 해당 메소드가 끝날 때 close()가 호출되는 것을 보장
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement("select now()");
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        String now = resultSet.getString(1);

        return now;
    }

    //파라미터로 입력된 TodoVO 객체의 정보를 이용해 DML 실행
    public void insert(TodoVO vo) throws Exception{

        String sql = "insert into tbl_todo (title, dueDate, finished) values (?, ?, ?)";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // PreparedStatement는 '?'를 이용해서 나중에 전달할 데이터들을 지정하는데 setXXX()를 이용해서 실제 값 지정
        // 인덱스 번호 1부터 시작. '?'가 3개이므로 setXXX()도 3개 지정
        preparedStatement.setString(1, vo.getTitle());
        preparedStatement.setDate(2, Date.valueOf(vo.getDueDate()));
        preparedStatement.setBoolean(3, vo.isFinished());

        preparedStatement.executeUpdate();
    }

    public List<TodoVO> selectAll() throws Exception{
        String sql = "select * from tbl_todo";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        List<TodoVO> list = new ArrayList<>();

        // ResultSet으로 각 행을 이동하면서 각 행의 데이터를 TodoVO로 변환
        while(resultSet.next()){
            TodoVO vo = TodoVO.builder()
                    .tno(resultSet.getLong("tno"))
                    .title(resultSet.getString("title"))
                    .dueDate(resultSet.getDate("dueDate").toLocalDate())
                    .finished(resultSet.getBoolean("finished"))
                    .build();

            list.add(vo);
        }
        return list;
    }

    public TodoVO selectOne(Long tno) throws Exception{
        String sql = "select * from tbl_todo where tno = ?";


        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // '?'에 들어갈 데이터 지정. '?'가 한개니까 인덱스 1만
        preparedStatement.setLong(1, tno);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        // tno로 조회한 한 행을 TodoVO 객체로 변환
        resultSet.next();
        TodoVO vo = TodoVO.builder()
                .tno(resultSet.getLong("tno"))
                .title(resultSet.getString("title"))
                .dueDate(resultSet.getDate("dueDate").toLocalDate())
                .finished(resultSet.getBoolean("finished"))
                .build();

        return vo;
    }

    public void deleteOne(Long tno) throws Exception{
        String sql = "delete from tbl_todo where tno = ?";


        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // '?'에 들어갈 데이터 지정. '?'가 한개니까 인덱스 1만
        preparedStatement.setLong(1, tno);

        preparedStatement.executeUpdate();
    }

    public void updateOne(TodoVO todoVO) throws Exception{
        String sql = "update tbl_todo set title = ?, dueDate = ?, finished = ? where tno = ?";


        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, todoVO.getTitle());
        preparedStatement.setDate(2, Date.valueOf(todoVO.getDueDate()));
        preparedStatement.setBoolean(3, todoVO.isFinished());
        preparedStatement.setLong(4, todoVO.getTno());

        preparedStatement.executeUpdate();

    }
}
