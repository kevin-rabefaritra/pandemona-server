package studio.startapps.pandemona.business;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import studio.startapps.pandemona.city.internal.CityEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents any business
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SoftDelete(strategy = SoftDeleteType.DELETED)
@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public abstract class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotBlank
    private String name;

    private String address;

    @Convert(converter = StringListConverter.class)
    private List<String> contacts;

    @NotNull
    private Float latitude;

    @NotNull
    private Float longitude;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CityEnum city;


}
