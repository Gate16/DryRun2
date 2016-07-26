package co.nz.airnz.govhack.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.nz.airnz.govhack.domain.BeachInfo;
import co.nz.airnz.govhack.repository.BeachInfoRepository;
import co.nz.airnz.govhack.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BeachInfo.
 */
@RestController
@RequestMapping("/api")
public class BeachInfoResource {

    private final Logger log = LoggerFactory.getLogger(BeachInfoResource.class);
        
    @Inject
    private BeachInfoRepository beachInfoRepository;
    
    /**
     * POST  /beach-infos : Create a new beachInfo.
     *
     * @param beachInfo the beachInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new beachInfo, or with status 400 (Bad Request) if the beachInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/beach-infos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BeachInfo> createBeachInfo(@RequestBody BeachInfo beachInfo) throws URISyntaxException {
        log.debug("REST request to save BeachInfo : {}", beachInfo);
        if (beachInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("beachInfo", "idexists", "A new beachInfo cannot already have an ID")).body(null);
        }
        BeachInfo result = beachInfoRepository.save(beachInfo);
        return ResponseEntity.created(new URI("/api/beach-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("beachInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /beach-infos : Updates an existing beachInfo.
     *
     * @param beachInfo the beachInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated beachInfo,
     * or with status 400 (Bad Request) if the beachInfo is not valid,
     * or with status 500 (Internal Server Error) if the beachInfo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/beach-infos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BeachInfo> updateBeachInfo(@RequestBody BeachInfo beachInfo) throws URISyntaxException {
        log.debug("REST request to update BeachInfo : {}", beachInfo);
        if (beachInfo.getId() == null) {
            return createBeachInfo(beachInfo);
        }
        BeachInfo result = beachInfoRepository.save(beachInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("beachInfo", beachInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /beach-infos : get all the beachInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of beachInfos in body
     */
    @RequestMapping(value = "/beach-infos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BeachInfo> getAllBeachInfos() {
        log.debug("REST request to get all BeachInfos");
        List<BeachInfo> beachInfos = beachInfoRepository.findAll();
        return beachInfos;
    }

    /**
     * GET  /beach-infos/:id : get the "id" beachInfo.
     *
     * @param id the id of the beachInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the beachInfo, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/beach-infos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BeachInfo> getBeachInfo(@PathVariable Long id) {
        log.debug("REST request to get BeachInfo : {}", id);
        BeachInfo beachInfo = beachInfoRepository.findOne(id);
        return Optional.ofNullable(beachInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /beach-infos/:id : delete the "id" beachInfo.
     *
     * @param id the id of the beachInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/beach-infos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBeachInfo(@PathVariable Long id) {
        log.debug("REST request to delete BeachInfo : {}", id);
        beachInfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("beachInfo", id.toString())).build();
    }
    
    @RequestMapping(value = "/regions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BeachInfo> createUser() throws URISyntaxException {
       return beachInfoRepository.findRegions();

    }

    @RequestMapping(value = "/beach",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BeachInfo> createUser(@RequestParam(value="region") String region) throws URISyntaxException {
        return beachInfoRepository.findBeach(region);

    }

    @RequestMapping(value = "/info",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BeachInfo> getInfo(@RequestParam(value="region") String region,
                                   @RequestParam(value="beach") String beach) throws URISyntaxException {
        return beachInfoRepository.getInfo(region, beach);

    }

}
