package tr.com.obss.jip.finalproject.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.finalproject.common.Constants;
import tr.com.obss.jip.finalproject.utils.SecurityUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Getter
@MappedSuperclass
public class BaseEntity implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String createdBy;

    private String updatedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onPrePersist() {
        final String currentUser= SecurityUtils.getCurrentUser();
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
        if(Objects.equals(currentUser, "")){
            setCreatedBy(Constants.SYSTEM_USER);
            setUpdatedBy(Constants.SYSTEM_USER);
        }else{
            setCreatedBy(currentUser);
            setUpdatedBy(currentUser);
        }
    }

    @PreUpdate
    public void onPreUpdate() {
        setUpdatedAt(LocalDateTime.now());
        if(Objects.equals(SecurityUtils.getCurrentUser(), "")){
            setCreatedBy(Constants.SYSTEM_USER);
            setUpdatedBy(Constants.SYSTEM_USER);
        }else{
            setCreatedBy(SecurityUtils.getCurrentUser());
            setUpdatedBy(SecurityUtils.getCurrentUser());
        }
    }
}
