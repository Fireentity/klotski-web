package it.klotski.web.game.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
/**
 * Configurazione della sicurezza per l'applicazione web.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configurazione dell'encoder della password.
     *
     * @return L'encoder della password.
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Gestisce l'authentication manager per l'autenticazione.
     *
     * @param configuration La configurazione dell'authentication manager.
     * @return L'authentication manager.
     * @throws Exception Se si verifica un'eccezione durante la creazione dell'authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Configura il filtro di sicurezza.
     * In ordine:
     * -Abilita la protezione CSRF (Cross-Site Request Forgery) utilizzando un token CSRF basato su cookie.
     * -Imposta la politica di creazione della sessione come "IF_REQUIRED". Ciò significa che verrà creata una
     *  sessione solo se richiesta esplicitamente.
     * -Imposta la strategia di session fixation "newSession", che genera una nuova sessione dopo il login per
     *  prevenire attacchi di session fixation.
     * -Viene specificato l'URL di logout, vengono eliminati i cookie di sessione e viene impostato un gestore per
     *  scrivere gli header di risposta per cancellare i dati del sito.
     * -Definisce le regole di autorizzazione per le richieste HTTP: le richieste che corrispondono a determinati
     * percorsi (/api/auth/** e /api/csrf) sono consentite a tutti (permitAll()), mentre tutte le altre richieste
     * richiedono l'autenticazione (authenticated())
     *
     * @param http L'oggetto HttpSecurity per la configurazione della sicurezza.
     * @return Il filtro di sicurezza.
     * @throws Exception Se si verifica un'eccezione durante la configurazione del filtro di sicurezza.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession)
                .and()
                .logout(logout -> logout.logoutUrl("/api/auth/logout")
                        .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES)))
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))))
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/csrf").permitAll()
                                .anyRequest().authenticated()
                );
        return http.build();
    }
}
