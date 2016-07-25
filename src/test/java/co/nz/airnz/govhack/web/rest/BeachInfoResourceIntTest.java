package co.nz.airnz.govhack.web.rest;

import co.nz.airnz.govhack.GreatWalksApp;
import co.nz.airnz.govhack.domain.BeachInfo;
import co.nz.airnz.govhack.repository.BeachInfoRepository;

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
 * Test class for the BeachInfoResource REST controller.
 *
 * @see BeachInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GreatWalksApp.class)
@WebAppConfiguration
@IntegrationTest
public class BeachInfoResourceIntTest {

    private static final String DEFAULT_REGION = "AAAAA";
    private static final String UPDATED_REGION = "BBBBB";
    private static final String DEFAULT_BEACH = "AAAAA";
    private static final String UPDATED_BEACH = "BBBBB";

    private static final Double DEFAULT_EXCELLENT = 1D;
    private static final Double UPDATED_EXCELLENT = 2D;

    private static final Double DEFAULT_SATISFACTORY = 1D;
    private static final Double UPDATED_SATISFACTORY = 2D;

    private static final Double DEFAULT_UNSATISFACTORY = 1D;
    private static final Double UPDATED_UNSATISFACTORY = 2D;

    @Inject
    private BeachInfoRepository beachInfoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBeachInfoMockMvc;

    private BeachInfo beachInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BeachInfoResource beachInfoResource = new BeachInfoResource();
        ReflectionTestUtils.setField(beachInfoResource, "beachInfoRepository", beachInfoRepository);
        this.restBeachInfoMockMvc = MockMvcBuilders.standaloneSetup(beachInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        beachInfo = new BeachInfo();
        beachInfo.setRegion(DEFAULT_REGION);
        beachInfo.setBeach(DEFAULT_BEACH);
        beachInfo.setExcellent(DEFAULT_EXCELLENT);
        beachInfo.setSatisfactory(DEFAULT_SATISFACTORY);
        beachInfo.setUnsatisfactory(DEFAULT_UNSATISFACTORY);
    }

    @Test
    @Transactional
    public void createBeachInfo() throws Exception {
        int databaseSizeBeforeCreate = beachInfoRepository.findAll().size();

        // Create the BeachInfo

        restBeachInfoMockMvc.perform(post("/api/beach-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(beachInfo)))
                .andExpect(status().isCreated());

        // Validate the BeachInfo in the database
        List<BeachInfo> beachInfos = beachInfoRepository.findAll();
        assertThat(beachInfos).hasSize(databaseSizeBeforeCreate + 1);
        BeachInfo testBeachInfo = beachInfos.get(beachInfos.size() - 1);
        assertThat(testBeachInfo.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testBeachInfo.getBeach()).isEqualTo(DEFAULT_BEACH);
        assertThat(testBeachInfo.getExcellent()).isEqualTo(DEFAULT_EXCELLENT);
        assertThat(testBeachInfo.getSatisfactory()).isEqualTo(DEFAULT_SATISFACTORY);
        assertThat(testBeachInfo.getUnsatisfactory()).isEqualTo(DEFAULT_UNSATISFACTORY);
    }

    @Test
    @Transactional
    public void getAllBeachInfos() throws Exception {
        // Initialize the database
        beachInfoRepository.saveAndFlush(beachInfo);

        // Get all the beachInfos
        restBeachInfoMockMvc.perform(get("/api/beach-infos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(beachInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
                .andExpect(jsonPath("$.[*].beach").value(hasItem(DEFAULT_BEACH.toString())))
                .andExpect(jsonPath("$.[*].excellent").value(hasItem(DEFAULT_EXCELLENT.doubleValue())))
                .andExpect(jsonPath("$.[*].satisfactory").value(hasItem(DEFAULT_SATISFACTORY.doubleValue())))
                .andExpect(jsonPath("$.[*].unsatisfactory").value(hasItem(DEFAULT_UNSATISFACTORY.doubleValue())));
    }

    @Test
    @Transactional
    public void getBeachInfo() throws Exception {
        // Initialize the database
        beachInfoRepository.saveAndFlush(beachInfo);

        // Get the beachInfo
        restBeachInfoMockMvc.perform(get("/api/beach-infos/{id}", beachInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(beachInfo.getId().intValue()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.beach").value(DEFAULT_BEACH.toString()))
            .andExpect(jsonPath("$.excellent").value(DEFAULT_EXCELLENT.doubleValue()))
            .andExpect(jsonPath("$.satisfactory").value(DEFAULT_SATISFACTORY.doubleValue()))
            .andExpect(jsonPath("$.unsatisfactory").value(DEFAULT_UNSATISFACTORY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBeachInfo() throws Exception {
        // Get the beachInfo
        restBeachInfoMockMvc.perform(get("/api/beach-infos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeachInfo() throws Exception {
        // Initialize the database
        beachInfoRepository.saveAndFlush(beachInfo);
        int databaseSizeBeforeUpdate = beachInfoRepository.findAll().size();

        // Update the beachInfo
        BeachInfo updatedBeachInfo = new BeachInfo();
        updatedBeachInfo.setId(beachInfo.getId());
        updatedBeachInfo.setRegion(UPDATED_REGION);
        updatedBeachInfo.setBeach(UPDATED_BEACH);
        updatedBeachInfo.setExcellent(UPDATED_EXCELLENT);
        updatedBeachInfo.setSatisfactory(UPDATED_SATISFACTORY);
        updatedBeachInfo.setUnsatisfactory(UPDATED_UNSATISFACTORY);

        restBeachInfoMockMvc.perform(put("/api/beach-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBeachInfo)))
                .andExpect(status().isOk());

        // Validate the BeachInfo in the database
        List<BeachInfo> beachInfos = beachInfoRepository.findAll();
        assertThat(beachInfos).hasSize(databaseSizeBeforeUpdate);
        BeachInfo testBeachInfo = beachInfos.get(beachInfos.size() - 1);
        assertThat(testBeachInfo.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testBeachInfo.getBeach()).isEqualTo(UPDATED_BEACH);
        assertThat(testBeachInfo.getExcellent()).isEqualTo(UPDATED_EXCELLENT);
        assertThat(testBeachInfo.getSatisfactory()).isEqualTo(UPDATED_SATISFACTORY);
        assertThat(testBeachInfo.getUnsatisfactory()).isEqualTo(UPDATED_UNSATISFACTORY);
    }

    @Test
    @Transactional
    public void deleteBeachInfo() throws Exception {
        // Initialize the database
        beachInfoRepository.saveAndFlush(beachInfo);
        int databaseSizeBeforeDelete = beachInfoRepository.findAll().size();

        // Get the beachInfo
        restBeachInfoMockMvc.perform(delete("/api/beach-infos/{id}", beachInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BeachInfo> beachInfos = beachInfoRepository.findAll();
        assertThat(beachInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
