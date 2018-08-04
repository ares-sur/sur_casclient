package org.ares.app.sys.entity;

import lombok.Data;

@Data
public class CasUser {
	private String userid;
	private String username;
	private String password;
	private boolean status;
	private String email;
}
