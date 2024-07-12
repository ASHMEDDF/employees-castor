package com.nn.castor.dto;

import com.nn.castor.domain.Position;
import lombok.Value;
import java.util.Date;

@Value
public class ResponseEmployeeCreatedDto {

    Long id;
    String nationalId;
    String name;
    String photo;
    Date dateEntry;
    Position position;

}
