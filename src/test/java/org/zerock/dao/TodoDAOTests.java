package org.zerock.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zerock.jdbcex.dao.TodoDAO;
import org.zerock.jdbcex.domain.TodoVO;

import java.time.LocalDate;
import java.util.List;

public class TodoDAOTests {

    private TodoDAO todoDAO;

    //모든 테스트 전에 TodoDAO 타입의 객체를 생성
    @BeforeEach
    public void ready(){
        todoDAO = new TodoDAO();
    }

    @Test
    public void testTime() throws Exception{
        System.out.println(todoDAO.getTime());
    }

    @Test
    public void testTime2() throws Exception{
        System.out.println(todoDAO.getTime2());
    }

    //todoVO 객체 생성 & todoDAO의 insert()메소드를 통해 DB에 insert
    @Test
    public void testInsert() throws Exception{
        TodoVO todoVO = TodoVO.builder()
                .title("Sample Title...")
                .dueDate(LocalDate.of(2023,12,31))
                .build();

        todoDAO.insert(todoVO);
    }

    @Test
    public void testList() throws Exception{
        List<TodoVO> list = todoDAO.selectAll();

        list.forEach(vo -> System.out.println(vo));
    }

    @Test
    public void testSelectOne() throws Exception{
        Long tno = 1L; //반드시 존재하는 번호를 이용
        TodoVO vo = todoDAO.selectOne(tno);
        System.out.println(vo);
    }

    @Test
    public void testUpdateOne() throws Exception{
        TodoVO todoVO = TodoVO.builder()
                .tno(1L)
                .title("Sample Title...")
                .dueDate(LocalDate.of(2023, 12, 21))
                .finished(true)
                .build();

        todoDAO.updateOne(todoVO);
    }
}
