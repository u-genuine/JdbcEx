package org.zerock.jdbcex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//계층이나 객체들 간에 데이터 교환이 이루어질 때, 한 개 이상의 데이터를 전달할 떄가 많기에
//여러 개의 데이터를 묶어 하나의 객체로 전달하는 것이 DTO(Data Transfer Object)
//대부분 Java Bean 형태로 구성
//VO와 똑같은데 VO는 주로 읽기 작업이라 @Date 대신에 @getter, @ToString 사용
@Builder
@Data //getter, setter, toString, equals, hashCode 등 생성
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
    private Long tno;

    private String title;

    private LocalDate dueDate;

    private boolean finished;
}

