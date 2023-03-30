package com.graphql.graphql.component.fake;

import DataSource.fake.FakeBookDataSource;
import com.netflix.dgs.codegen.generated.DgsConstants;
import com.netflix.dgs.codegen.generated.types.Book;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@DgsComponent
public class FakeBookDataResolver {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = "books")
    public List<Book> booksWrittenBy(@InputArgument(name = "author") Optional<String> authorName) {
        if (authorName.isEmpty() || StringUtils.isBlank(authorName.get())) {
            return FakeBookDataSource.BOOK_LIST;
        }

        return FakeBookDataSource.BOOK_LIST.stream()
                .filter(b -> StringUtils.containsAnyIgnoreCase(
                        b.getAuthor().getName(), authorName.get()
                )).collect(Collectors.toList());
    }


}


