package ru.assaulov.utilitybills2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.assaulov.utilitybills2.model.enums.Gender;
import ru.assaulov.utilitybills2.model.enums.Role;

import javax.persistence.*;
import javax.swing.text.Utilities;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_users")
@NoArgsConstructor
@Data
@ToString(of = {"userId", "login", "firstName", "lastName", "gender", "email"})
@EqualsAndHashCode(of = {"userId"})
@SuperBuilder(toBuilder = true)
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Column(name = "login", nullable = false)
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
	private Set<Role> roles = new HashSet<>();

	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL,  mappedBy = "userId")
	private List<Meters> metersList;

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
