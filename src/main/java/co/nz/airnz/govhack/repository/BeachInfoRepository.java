package co.nz.airnz.govhack.repository;

import co.nz.airnz.govhack.domain.BeachInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BeachInfo entity.
 */
public interface BeachInfoRepository extends JpaRepository<BeachInfo,Long> {

    @Query("Select distinct i.region from BeachInfo i")
    List<BeachInfo> findRegions();

    @Query("Select distinct i.beach from BeachInfo i where i.region=?1")
    List<BeachInfo> findBeach(String region);
    
    @Query("Select distinct i from BeachInfo i where i.region=?1 and i.beach=?2")
    List<BeachInfo> getInfo(String region, String beach);
}
