package co.nz.airnz.govhack.web.rest;

import com.codahale.metrics.annotation.Timed;

import co.nz.airnz.govhack.domain.BeachInfo;
import co.nz.airnz.govhack.domain.BeachLocation;
import co.nz.airnz.govhack.repository.BeachLocationRepository;
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
 * REST controller for managing BeachLocation.
 */
@RestController
@RequestMapping("/api")
public class BeachLocationResource {

    private final Logger log = LoggerFactory.getLogger(BeachLocationResource.class);
        
    @Inject
    private BeachLocationRepository beachLocationRepository;
    
    /**
     * POST  /beach-locations : Create a new beachLocation.
     *
     * @param beachLocation the beachLocation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new beachLocation, or with status 400 (Bad Request) if the beachLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/beach-locations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BeachLocation> createBeachLocation(@RequestBody BeachLocation beachLocation) throws URISyntaxException {
        log.debug("REST request to save BeachLocation : {}", beachLocation);
        if (beachLocation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("beachLocation", "idexists", "A new beachLocation cannot already have an ID")).body(null);
        }
        BeachLocation result = beachLocationRepository.save(beachLocation);
        return ResponseEntity.created(new URI("/api/beach-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("beachLocation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /beach-locations : Updates an existing beachLocation.
     *
     * @param beachLocation the beachLocation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated beachLocation,
     * or with status 400 (Bad Request) if the beachLocation is not valid,
     * or with status 500 (Internal Server Error) if the beachLocation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/beach-locations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BeachLocation> updateBeachLocation(@RequestBody BeachLocation beachLocation) throws URISyntaxException {
        log.debug("REST request to update BeachLocation : {}", beachLocation);
        if (beachLocation.getId() == null) {
            return createBeachLocation(beachLocation);
        }
        BeachLocation result = beachLocationRepository.save(beachLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("beachLocation", beachLocation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /beach-locations : get all the beachLocations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of beachLocations in body
     */
    @RequestMapping(value = "/beach-locations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BeachLocation> getAllBeachLocations() {
        log.debug("REST request to get all BeachLocations");
        List<BeachLocation> beachLocations = beachLocationRepository.findAll();
        return beachLocations;
    }

    /**
     * GET  /beach-locations/:id : get the "id" beachLocation.
     *
     * @param id the id of the beachLocation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the beachLocation, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/beach-locations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BeachLocation> getBeachLocation(@PathVariable Long id) {
        log.debug("REST request to get BeachLocation : {}", id);
        BeachLocation beachLocation = beachLocationRepository.findOne(id);
        return Optional.ofNullable(beachLocation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /beach-locations/:id : delete the "id" beachLocation.
     *
     * @param id the id of the beachLocation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/beach-locations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBeachLocation(@PathVariable Long id) {
        log.debug("REST request to delete BeachLocation : {}", id);
        beachLocationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("beachLocation", id.toString())).build();
    }
    
    @RequestMapping(value = "/location",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        public List<BeachLocation> getInfo(
                                       @RequestParam(value="beach") String beach) throws URISyntaxException {
            return beachLocationRepository.findBeachLocation(beach);

        }

}
