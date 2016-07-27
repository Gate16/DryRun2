package co.nz.airnz.govhack.repository;

import co.nz.airnz.govhack.domain.BeachInfo;
import co.nz.airnz.govhack.domain.BeachLocation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BeachLocation entity.
 */
public interface BeachLocationRepository extends JpaRepository<BeachLocation,Long> {
	@Query("Select i from BeachLocation i where i.beachname=?1")
    List<BeachLocation> findBeachLocation(String beach);
}
