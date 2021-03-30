package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.User;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.UserDetailsCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.major.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserManager userManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailsCommand command = this.constructByUsername(username);
		return command;
	}

	private UserDetailsCommand constructByUsername(String username) throws UsernameNotFoundException {
		User user = this.userManager.findUserById(null, username);
		if (user == null) {
			throw new UsernameNotFoundException("can't found user " + username);
		}

		return new UserDetailsCommand(user);
	}

}
