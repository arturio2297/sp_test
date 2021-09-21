package com.example.demo.users;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.users.Permissions.READ;
import static com.example.demo.users.Permissions.WRITE;
import static com.example.demo.users.Permissions.DELETE;

public enum Roles {
    ROLE_VIEWER(Sets.newHashSet(READ)),
    ROLE_WRITER(Sets.newHashSet(READ,WRITE)),
    ROLE_ADMIN(Sets.newHashSet(READ,WRITE,DELETE));

    private final Set<Permissions> permissions;

    Roles(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<Permissions> getPermissions() {
        return this.permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                    .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority(this.name()));
        return permissions;
    }
}