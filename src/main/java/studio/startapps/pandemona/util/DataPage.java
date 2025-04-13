package studio.startapps.pandemona.util;

import org.springframework.data.domain.Page;

import java.util.List;

public record DataPage<T>(
    List<T> content,
    int totalPages,
    int number,
    boolean first,
    boolean last,
    long totalElements,
    int numberOfElements
) {
    public DataPage(Page<T> page) {
        this(
            page.getContent(),
            page.getTotalPages(),
            page.getNumber(),
            page.isFirst(),
            page.isLast(),
            page.getTotalElements(),
            page.getNumberOfElements()
        );
    }
}
