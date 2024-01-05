package org.zerock.jdbcex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data //getter, setter, toString, equals, hashCode 등 생성
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private String mid;
    private String mpw;
    private String mname;
    private String uuid;
}
