package com.graphql.graphql.component.Fake;

import com.graphql.graphql.generated.client.BooksGraphQLQuery;
import com.graphql.graphql.generated.client.BooksProjectionRoot;
import com.jayway.jsonpath.TypeRef;
import com.netflix.dgs.codegen.generated.types.Author;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FakeBookDataResolverTest {
    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    Faker faker;

    @Test
    void booksWrittenBy() {
        var graphqlQuery = new BooksGraphQLQuery.Builder().build();
        var projectionRoot = new BooksProjectionRoot().title()
                .author().name()
                .originCountry()
                .getRoot().released().year();
        var graphqlQueryRequest = new GraphQLQueryRequest(graphqlQuery, projectionRoot).serialize();

        List<String> titles = dgsQueryExecutor.executeAndExtractJsonPath(
                graphqlQueryRequest, "data.books[*].title");

        assertNotNull(titles);
        assertFalse(titles.isEmpty());

        List<Author> authors = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                graphqlQueryRequest, "data.books[*].author",
                new TypeRef<List<Author>>() {
                }
        );

        assertNotNull(authors);
        assertEquals(titles.size(), authors.size());

        List<Integer> releaseYears = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                graphqlQueryRequest, "data.books[*].released.year",
                new TypeRef<List<Integer>>() {
                }
        );

        assertNotNull(releaseYears);
        assertEquals(titles.size(), releaseYears.size());
    }

    @Test
    void getBooksByReleased() {
    }
}