package org.bfh.dms.repository;

import org.bfh.dms.domain.Ping;
import org.bfh.dms.dto.PingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PingRepository extends JpaRepository<Ping, Long> {
}
