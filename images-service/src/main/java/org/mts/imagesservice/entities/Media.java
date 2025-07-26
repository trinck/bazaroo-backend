package org.mts.imagesservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class", discriminatorType = DiscriminatorType.STRING)
public abstract class Media extends GenericsFieldsEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mime type of the media (e.g. "image/png", "image/jpeg", "video/mp4")
    @Column(nullable = false)
    private String type;

    // Name of the media (e.g. "my-image.png")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column
    private String path;

    @Column(nullable = false)
    private Long size;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Media media = (Media) o;
        return getId() != null && Objects.equals(getId(), media.getId());
    }

    @Override
    public int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
