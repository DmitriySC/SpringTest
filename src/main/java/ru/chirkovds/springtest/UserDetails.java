package ru.chirkovds.springtest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetails extends User {
    public UserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }
}
/*
package ru.chirkovds.springtest;

        import org.apache.commons.logging.Log;
        import org.apache.commons.logging.LogFactory;
        import org.springframework.security.core.GrantedAuthority;
        import org.springframework.security.core.authority.SimpleGrantedAuthority;
        import org.springframework.security.core.userdetails.User;

        import java.util.ArrayList;
        import java.util.Collection;

public class UserDetailsImpl implements User{
    private User userDetails;
    private Log log = LogFactory.getLog(UserDetailsImpl.class);

    public UserDetailsImpl(User user){
        this.userDetails = user;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> principalAuthorities = new ArrayList<GrantedAuthority>();
        principalAuthorities.add(new SimpleGrantedAuthority (user.getRole().toString()));
        for (GrantedAuthority authority : principalAuthorities){
            log.info("Role: " + authority);
        }
        return principalAuthorities;
    }

    public String getPassword() {
        log.info("Password: "+ user.getPassword());
        return user.getPassword();
    }

    public String getUsername() {
        log.info("Username: " + user.getUsername());
        return user.getUsername();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return false;
    }
}
*/