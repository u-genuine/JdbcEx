package org.zerock.jdbcex.service;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.zerock.jdbcex.dao.MemberDAO;
import org.zerock.jdbcex.domain.MemberVO;
import org.zerock.jdbcex.dto.MemberDTO;
import org.zerock.jdbcex.util.MapperUtil;

@Log4j2
public enum MemberService {
    INSTANCE; //여러 곳에서 동일한 객체를 사용하도록 하기 위함

    private MemberDAO dao;
    private ModelMapper modelMapper;

    MemberService(){
        dao = new MemberDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    public MemberDTO login(String mid, String mpw) throws Exception{
        MemberVO vo = dao.getWithPassword(mid, mpw);

        MemberDTO memberDTO = modelMapper.map(vo, MemberDTO.class);

        return memberDTO;
    }

    public void updateUuid(String mid, String uuid) throws Exception{
        dao.updateUuid(mid, uuid);
    }

    public MemberDTO getByUUID(String uuid) throws  Exception{
        MemberVO vo = dao.selectUUID(uuid);

        MemberDTO memberDTO = modelMapper.map(vo, MemberDTO.class);

        return memberDTO;
    }
}
