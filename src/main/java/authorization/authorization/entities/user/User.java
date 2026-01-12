package authorization.authorization.entities.user;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true)
  @Setter
  private String username;

  @Column(nullable = false)
  @Setter
  private String password;

  @Column(nullable = false)
  @Setter
  private boolean accountNonExpired;

  @Column(nullable = false)
  @Setter
  private boolean accountNonLocked;

  @Column(nullable = false)
  @Setter
  private boolean credentialsNonExpired;

  @Column(nullable = false)
  @Setter
  private boolean enabled;

  @Version
  private Long version;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
    name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id")
  )
  @Enumerated(EnumType.STRING)
  private Set<UserRole> roles = new HashSet<>();

  public Set<UserRole> getRoles() {
    return Collections.unmodifiableSet(this.roles);
  }

  public void addRole(UserRole role) {
    this.roles.add(role);
  }

  public void removeRole(UserRole role) {
    this.roles.remove(role);
  }
}
