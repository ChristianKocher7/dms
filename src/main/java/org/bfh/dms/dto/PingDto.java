package org.bfh.dms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PingDto {

    private String value;

    public static PingDto of(String value) {
        PingDto instance = new PingDto();
        instance.value = value;
        return instance;
    }
}
