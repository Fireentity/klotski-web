package it.klotski.web.game.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Questa classe rappresenta un utente del sistema.
 *
 * Un oggetto User contiene informazioni sull'utente, come l'identificatore, l'email, la password,
 * lo stato dell'account e le autorizzazioni.
 * Implementa l'interfaccia UserDetails di Spring Security per supportare l'autenticazione e l'autorizzazione.
 */
@Setter
@Entity
@Getter
@Table(name = "users")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class User implements UserDetails {
    /**
     * Identificatore univoco dell'utente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * L'email dell'utente.
     */
    @Column(unique = true)
    private String email;

    /**
     * La password dell'utente.
     */
    private String password;

    /**
     * Lo stato dell'account dell'utente.
     */
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    /**
     * Restituisce una collezione di autorizzazioni dell'utente.
     *
     * @return La collezione di autorizzazioni dell'utente.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * Restituisce l'username dell'utente, che corrisponde all'email.
     *
     * @return L'username dell'utente.
     */
    @Override
    public String getUsername() {
        return email;
    }
}
