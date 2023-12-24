package org.zerock.jdbcex.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

//DTO -> VO 또는 VO -> DTO 변환을 ModelMapper 라이브러리를 이용해서 처리
//MapperUtil은 ModelMapper의 설정을 변경하고 쉽게 사용할 수 있게함
public enum MapperUtil {

    INSTANCE;

    private ModelMapper modelmapper;

    MapperUtil(){
        this.modelmapper = new ModelMapper();
        this.modelmapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public ModelMapper get(){
        return modelmapper;
    }
}
