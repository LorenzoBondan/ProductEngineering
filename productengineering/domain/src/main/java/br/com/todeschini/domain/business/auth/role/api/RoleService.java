package br.com.todeschini.domain.business.auth.role.api;

import org.springframework.stereotype.Component;

@Component
public interface RoleService extends FindRole, InsertRole, UpdateRole, DeleteRole {
}
