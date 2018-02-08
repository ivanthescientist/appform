package com.isc.appform;

import com.isc.appform.dto.SectorTreeNodeDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SectorsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testThatTreeIsBuilt() {
        SectorTreeNodeDTO tree = restTemplate.getForObject("http://localhost:" + port + "/api/sectors", SectorTreeNodeDTO.class);

        /*
         * Rough representation of the tree contained in data.sql.
         *         1
         *      /     \
         *   19       6
         *           /  \
         *         342  43
         *
         */

        Assert.assertEquals(0L, tree.getId().longValue());

        SectorTreeNodeDTO l1ChildOne = tree.getChildren().get(0);
        Assert.assertEquals(1L, l1ChildOne.getId().longValue());

        SectorTreeNodeDTO l2ChildOne = l1ChildOne.getChildren().get(0);
        SectorTreeNodeDTO l2ChildTwo = l1ChildOne.getChildren().get(1);

        Assert.assertEquals(19L, l2ChildOne.getId().longValue());
        Assert.assertEquals(6L, l2ChildTwo.getId().longValue());
    }
}
