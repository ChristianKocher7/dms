package org.bfh.dms.repository;

import org.bfh.dms.domain.Ping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PingRepository extends JpaRepository<Ping, Long> {
}
