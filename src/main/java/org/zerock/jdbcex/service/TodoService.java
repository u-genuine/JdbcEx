package org.zerock.jdbcex.service;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.zerock.jdbcex.dao.TodoDAO;
import org.zerock.jdbcex.domain.TodoVO;
import org.zerock.jdbcex.dto.TodoDTO;
import org.zerock.jdbcex.util.MapperUtil;

import java.util.List;
import java.util.stream.Collectors;

//DTO 와 VO 둘 다 이용
@Log4j2
public enum TodoService {
    INSTANCE;

    private TodoDAO dao;
    private ModelMapper modelMapper;

    TodoService(){
        dao = new TodoDAO();
        modelMapper = MapperUtil.INSTANCE.get();

    }

    //TodoDTO를 파라미터로 받아서 TodoVO로 변환하고 저장
    public void register(TodoDTO todoDTO) throws Exception{
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);

        log.info(todoVO);

        dao.insert(todoVO); //int를 반환하므로 이를 이용해 예외처리 가능
    }


    // TodoDAO에서 가져온 TodoVO의 목록을 모두 TodoDTO로 변환해서 반환
    public List<TodoDTO> listAll() throws Exception{
        List<TodoVO> voList = dao.selectAll();

        log.info("voList....................");
        log.info(voList);

        List<TodoDTO> dtoList = voList.stream()
                .map(vo -> modelMapper.map(vo, TodoDTO.class))
                .collect(Collectors.toList());

        return dtoList;
    }

    //TodoDAO의 selectOne()을 통해 TodoVO 객체를 가져오고
    //ModelMapper를 이용해서 TodoDTO로 변환
    public TodoDTO get(Long tno) throws Exception{

        log.info("tno: "+ tno);
        TodoVO todoVO = dao.selectOne(tno);

        TodoDTO todoDTO = modelMapper.map(todoVO, TodoDTO.class);
        return todoDTO;
    }

    public void remove(Long tno) throws Exception{
        log.info("tno: " + tno);
        dao.deleteOne(tno);
    }

    public void modify(TodoDTO todoDTO) throws Exception{
        log.info("todoDTO: " + todoDTO);

        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);

        dao.updateOne(todoVO);
    }
}
