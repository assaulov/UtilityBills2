package ru.assaulov.utilitybills2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import ru.assaulov.utilitybills2.model.enums.Gender;
import ru.assaulov.utilitybills2.model.enums.Role;

import javax.persistence.*;
import javax.swing.text.Utilities;
import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "t_users")
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(of = {"userId", "login", "firstName", "lastName", "gender", "email"})
@ToString(of = {"userId", "login", "firstName", "lastName", "gender", "email"})
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false, unique = true)
	private Long userId;
	@Column(name = "login", nullable = false, unique = true)
	private String login;
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	private String password;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private String email;

	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "t_user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	@JsonIgnore
	@Builder.Default
	private Set<Role> roles = new HashSet<>();

	@Builder.Default
	@OneToMany()
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private List<Meters> metersList = new ArrayList<>();

	@JsonIgnore
	public String getFullName() {
		return lastName + " " + firstName;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getUsername() {
		return getFullName();
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}
}
