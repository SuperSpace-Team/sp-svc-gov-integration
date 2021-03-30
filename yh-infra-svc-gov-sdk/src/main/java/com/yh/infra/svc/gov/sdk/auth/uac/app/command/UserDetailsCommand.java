package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 当前用户的所有信息，基于SpringSecurity
 * @author 李光辉
 * @date 2015年3月12日 上午10:47:38
 * @since
 */
public class UserDetailsCommand implements UserDetails {

    private static final long serialVersionUID = -967256670810173952L;

    private UserPrivilegeCommand command;
    
    /** 当前选择的组织的ID */
    private Long currentOuId;
    
    public UserDetailsCommand() { }

    public UserDetailsCommand(User user) {
        this.command = new UserPrivilegeCommand(user);
    }

    public UserDetailsCommand(UserPrivilegeCommand command) {
        this.command = command;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>();
    }

    @Override
    public String getPassword() {
        return this.command.getUser().getPassword();
    }
    
    @Override
    public String getUsername() {
        return this.command.getUser().getLoginName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.command.getUser().getIsAccNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.command.getUser().getIsAccNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return BaseModel.LIFECYCLE_NORMAL.equals(this.command.getUser().getLifecycle());
    }

    public UserPrivilegeCommand getCommand() {
        return command;
    }

    public void setCommand(UserPrivilegeCommand command) {
        this.command = command;
    }

    public User getUser() {
        return this.command.getUser();
    }
    
    public String getDisplayName() {
        return this.command.getUser().getUserName();
    }
    
    public List<String> getPrivilegeUrls() {
        return this.command.getUrlList();
    }

    public Long getCurrentOuId() {
        return currentOuId;
    }

    public void setCurrentOuId(Long currentOuId) {
        this.currentOuId = currentOuId;
    }
    
}
