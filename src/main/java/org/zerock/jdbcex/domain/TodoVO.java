package org.zerock.jdbcex.domain;

import lombok.*;

import java.time.LocalDate;

// tbl_todo 테이블의 데이터를 자바 객체로 처리하기 위한 TodoVO 클래스
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TodoVO {

    private Long tno;

    private String title;

    private LocalDate dueDate;

    private boolean finished;
}
