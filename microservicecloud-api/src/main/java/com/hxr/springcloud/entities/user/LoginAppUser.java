package com.hxr.springcloud.entities.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginAppUser extends AppUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Set<SysRole> sysRoles;

    private Set<String> permissions;


    @JsonIgnore //在实体类向前台返回数据时用来忽略不想传递给前台的属性或接口
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new HashSet<>();
        if(!CollectionUtils.isEmpty(sysRoles)){
            sysRoles.forEach(role -> {
                if(role.getCode().startsWith("ROLE_")){
                    collection.add(new SimpleGrantedAuthority(role.getCode()));
                }else{
                    collection.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
                }
            });
        }

        if(!CollectionUtils.isEmpty(permissions)){
            permissions.forEach(permission ->{
                collection.add(new SimpleGrantedAuthority(permission));
            });
        }

        return collection;
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
