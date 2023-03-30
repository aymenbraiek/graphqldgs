package com.graphql.graphql;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import graphql.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GraphqlApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	DgsQueryExecutor dgsQueryExecutor;

	void testOnHello() {
		var graphQuery = """
                {
                oneHello {
                text 
                randomNum 
                }
                }
                """;

		String text = dgsQueryExecutor.executeAndExtractJsonPath(graphQuery,"data.oneHello.text");
		Integer randomNumber = dgsQueryExecutor.executeAndExtractJsonPath(graphQuery,"data.oneHello.randomNumber");
		Assert.assertFalse(StringUtils.isBlank(text));

	}

}
