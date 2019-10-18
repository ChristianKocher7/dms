package org.bfh.dms.service;

import org.bfh.dms.domain.Ping;
import org.bfh.dms.dto.PingDto;
import org.bfh.dms.repository.PingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PingService {

    @Autowired
    private PingRepository pingRepository;

    public PingDto getPing() {
        return PingDto.of("Ping!");
    }

    public PingDto savePing(String value){
        PingDto pingDto = PingDto.of(value);
        pingRepository.save(Ping.of(pingDto.getValue()));
        return pingDto;
    }

}
