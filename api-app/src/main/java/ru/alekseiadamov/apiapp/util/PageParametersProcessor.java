package ru.alekseiadamov.apiapp.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.alekseiadamov.apiapp.dto.PageParamsDTO;

import java.util.Optional;

public class PageParametersProcessor {

    private PageParametersProcessor() {
        throw new IllegalStateException("Utility class");
    }

    public static PageRequest getPageRequest(PageParamsDTO params) {
        return PageRequest.of(
                Optional.ofNullable(params.getPage()).orElse(1) - 1,
                Optional.ofNullable(params.getSize()).orElse(3),
                getSortDirection(params)
        );
    }

    private static Sort getSortDirection(PageParamsDTO params) {
        return Optional.ofNullable(params.getSortOrder())
                .orElse("asc")
                .equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(Optional.ofNullable(params.getSortBy()).orElse("id")).ascending()
                : Sort.by(Optional.ofNullable(params.getSortBy()).orElse("id")).descending();
    }
}
