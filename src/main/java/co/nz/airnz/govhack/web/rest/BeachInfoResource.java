package co.nz.airnz.govhack.web.rest;

import co.nz.airnz.govhack.domain.BeachInfo;
import co.nz.airnz.govhack.repository.BeachInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class BeachInfoResource {

    private final Logger log = LoggerFactory.getLogger(BeachInfoResource.class);

    @Inject
    private BeachInfoRepository beachInfoRepository;


    @RequestMapping(value = "/regions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BeachInfo> createUser() throws URISyntaxException {
       return beachInfoRepository.findRegions();

    }

    @RequestMapping(value = "/beach",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BeachInfo> createUser(@RequestParam(value="region") String region) throws URISyntaxException {
        return beachInfoRepository.findBeach(region);

    }

    @RequestMapping(value = "/info",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BeachInfo> getInfo(@RequestParam(value="region") String region,
                                   @RequestParam(value="beach") String beach) throws URISyntaxException {
        return beachInfoRepository.getInfo(region, beach);

    }

}
