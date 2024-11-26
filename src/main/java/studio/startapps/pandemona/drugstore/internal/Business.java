package studio.startapps.pandemona.drugstore.internal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents any business
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SoftDelete(strategy = SoftDeleteType.DELETED)
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotEmpty
    @Convert(converter = StringListConverter.class)
    private List<String> contacts;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    @Convert(converter = CityConverter.class)
    private CityEnum city;


}
