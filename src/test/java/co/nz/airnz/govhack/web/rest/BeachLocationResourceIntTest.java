package co.nz.airnz.govhack.web.rest;

import co.nz.airnz.govhack.GreatWalksApp;
import co.nz.airnz.govhack.domain.BeachLocation;
import co.nz.airnz.govhack.repository.BeachLocationRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BeachLocationResource REST controller.
 *
 * @see BeachLocationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GreatWalksApp.class)
@WebAppConfiguration
@IntegrationTest
public class BeachLocationResourceIntTest {

    private static final String DEFAULT_BEACHNAME = "AAAAA";
    private static final String UPDATED_BEACHNAME = "BBBBB";

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Integer DEFAULT_ZOOMLEVEL = 1;
    private static final Integer UPDATED_ZOOMLEVEL = 2;

    @Inject
    private BeachLocationRepository beachLocationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBeachLocationMockMvc;

    private BeachLocation beachLocation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BeachLocationResource beachLocationResource = new BeachLocationResource();
        ReflectionTestUtils.setField(beachLocationResource, "beachLocationRepository", beachLocationRepository);
        this.restBeachLocationMockMvc = MockMvcBuilders.standaloneSetup(beachLocationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        beachLocation = new BeachLocation();
        beachLocation.setBeachname(DEFAULT_BEACHNAME);
        beachLocation.setLongitude(DEFAULT_LONGITUDE);
        beachLocation.setLatitude(DEFAULT_LATITUDE);
        beachLocation.setZoomlevel(DEFAULT_ZOOMLEVEL);
    }

    @Test
    @Transactional
    public void createBeachLocation() throws Exception {
        int databaseSizeBeforeCreate = beachLocationRepository.findAll().size();

        // Create the BeachLocation

        restBeachLocationMockMvc.perform(post("/api/beach-locations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(beachLocation)))
                .andExpect(status().isCreated());

        // Validate the BeachLocation in the database
        List<BeachLocation> beachLocations = beachLocationRepository.findAll();
        assertThat(beachLocations).hasSize(databaseSizeBeforeCreate + 1);
        BeachLocation testBeachLocation = beachLocations.get(beachLocations.size() - 1);
        assertThat(testBeachLocation.getBeachname()).isEqualTo(DEFAULT_BEACHNAME);
        assertThat(testBeachLocation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testBeachLocation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testBeachLocation.getZoomlevel()).isEqualTo(DEFAULT_ZOOMLEVEL);
    }

    @Test
    @Transactional
    public void getAllBeachLocations() throws Exception {
        // Initialize the database
        beachLocationRepository.saveAndFlush(beachLocation);

        // Get all the beachLocations
        restBeachLocationMockMvc.perform(get("/api/beach-locations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(beachLocation.getId().intValue())))
                .andExpect(jsonPath("$.[*].beachname").value(hasItem(DEFAULT_BEACHNAME.toString())))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].zoomlevel").value(hasItem(DEFAULT_ZOOMLEVEL)));
    }

    @Test
    @Transactional
    public void getBeachLocation() throws Exception {
        // Initialize the database
        beachLocationRepository.saveAndFlush(beachLocation);

        // Get the beachLocation
        restBeachLocationMockMvc.perform(get("/api/beach-locations/{id}", beachLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(beachLocation.getId().intValue()))
            .andExpect(jsonPath("$.beachname").value(DEFAULT_BEACHNAME.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.zoomlevel").value(DEFAULT_ZOOMLEVEL));
    }

    @Test
    @Transactional
    public void getNonExistingBeachLocation() throws Exception {
        // Get the beachLocation
        restBeachLocationMockMvc.perform(get("/api/beach-locations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeachLocation() throws Exception {
        // Initialize the database
        beachLocationRepository.saveAndFlush(beachLocation);
        int databaseSizeBeforeUpdate = beachLocationRepository.findAll().size();

        // Update the beachLocation
        BeachLocation updatedBeachLocation = new BeachLocation();
        updatedBeachLocation.setId(beachLocation.getId());
        updatedBeachLocation.setBeachname(UPDATED_BEACHNAME);
        updatedBeachLocation.setLongitude(UPDATED_LONGITUDE);
        updatedBeachLocation.setLatitude(UPDATED_LATITUDE);
        updatedBeachLocation.setZoomlevel(UPDATED_ZOOMLEVEL);

        restBeachLocationMockMvc.perform(put("/api/beach-locations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBeachLocation)))
                .andExpect(status().isOk());

        // Validate the BeachLocation in the database
        List<BeachLocation> beachLocations = beachLocationRepository.findAll();
        assertThat(beachLocations).hasSize(databaseSizeBeforeUpdate);
        BeachLocation testBeachLocation = beachLocations.get(beachLocations.size() - 1);
        assertThat(testBeachLocation.getBeachname()).isEqualTo(UPDATED_BEACHNAME);
        assertThat(testBeachLocation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testBeachLocation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testBeachLocation.getZoomlevel()).isEqualTo(UPDATED_ZOOMLEVEL);
    }

    @Test
    @Transactional
    public void deleteBeachLocation() throws Exception {
        // Initialize the database
        beachLocationRepository.saveAndFlush(beachLocation);
        int databaseSizeBeforeDelete = beachLocationRepository.findAll().size();

        // Get the beachLocation
        restBeachLocationMockMvc.perform(delete("/api/beach-locations/{id}", beachLocation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BeachLocation> beachLocations = beachLocationRepository.findAll();
        assertThat(beachLocations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
