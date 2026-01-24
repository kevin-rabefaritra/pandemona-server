package studio.startapps.pandemona.util;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record MobileDataPage<T extends Object>(
    List<T> content,
    boolean last
) {
    public MobileDataPage(Page<T> page) {
        this(
            page.getContent(),
            page.isLast()
        );
    }
}
