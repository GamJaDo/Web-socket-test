package websocket_test.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "TRAINER_TB", indexes = @Index(name = "IDX_TRAINER_EMAIL", columnList = "EMAIL"))
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;
/*
    @Column(nullable = true)
    private String profileImageUrl;

    @ManyToOne
    private Gym gym;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TrainerGender gender;

    @Transient
    private static DefaultImageProvider defaultImageProvider;

    public static void setDefaultImageProvider(DefaultImageProvider provider) {
        defaultImageProvider = provider;
    }
*/
    protected Trainer() {
    }
/*
    public Trainer(String email, String password, String name, TrainerGender gender) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
    }

    @PrePersist
    private void setDefaultProfileImageUrl() {
        if (this.profileImageUrl == null || this.profileImageUrl.isEmpty()) {
            this.profileImageUrl = defaultImageProvider.getDefaultImageUrl();
        }
    }
*/
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
/*
    public TrainerGender getGender() {
        return gender;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Gym getGym() {
        return gym;
    }
*/
    public String getPassword() {
        return password;
    }
}
