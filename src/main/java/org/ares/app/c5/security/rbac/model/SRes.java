package org.ares.app.c5.security.rbac.model;

import java.util.List;

import lombok.Data;

@Data
public class SRes {
	private String resid;
	private String resname;
	private String resurl;
	private List<SRole> roles;
}
