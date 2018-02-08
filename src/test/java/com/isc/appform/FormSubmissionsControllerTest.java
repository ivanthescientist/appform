package com.isc.appform;

import com.isc.appform.dto.FormSubmissionDTO;
import com.isc.appform.repository.FormSubmissionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FormSubmissionsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FormSubmissionRepository repository;

    @Test
    public void testFullFlow() {
        // Check that nothing is in the database
        Assert.assertEquals(0, repository.findAllWithPrefetch().size());
        FormSubmissionDTO result = restTemplate.getForObject("http://localhost:" + port + "/api/form-submissions/test",
                FormSubmissionDTO.class);

        // Add one submission entity
        result.setName("test");
        result.setSelectedSectorIds(Arrays.asList(342L, 43L));
        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + "/api/form-submissions",
                result,
                Void.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Check that its added correctly with links
        result = restTemplate.getForObject("http://localhost:" + port + "/api/form-submissions/test",
                FormSubmissionDTO.class);
        Assert.assertEquals(2, result.getSelectedSectorIds().size());
        Assert.assertTrue(result.getSelectedSectorIds().contains(342L));
        Assert.assertTrue(result.getSelectedSectorIds().contains(43L));

        // Clear the entity and add non-existing sector
        result.getSelectedSectorIds().clear();
        result.getSelectedSectorIds().add(13394L);
        response = restTemplate.postForEntity("http://localhost:" + port + "/api/form-submissions",
                result,
                Void.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Clear list of linked sectors for entity and add new link
        result.getSelectedSectorIds().clear();
        result.getSelectedSectorIds().add(19L);
        response = restTemplate.postForEntity("http://localhost:" + port + "/api/form-submissions",
                result,
                Void.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Check that entity has new link
        result = restTemplate.getForObject("http://localhost:" + port + "/api/form-submissions/test",
                FormSubmissionDTO.class);
        Assert.assertTrue(result.getSelectedSectorIds().contains(19L));
    }

}
