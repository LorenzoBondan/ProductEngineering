package br.com.todeschini.webapi.rest.auth.role;

import br.com.todeschini.domain.business.auth.role.DRole;

public class RoleFactory {

    public static DRole createDRole() {
        DRole role = new DRole();
        role.setId(1L);
        role.setAuthority("ROLE_TEST");
        return role;
    }

    public static DRole createDuplicatedDRole() {
        DRole role = new DRole();
        role.setId(2L);
        role.setAuthority("ROLE_TEST");
        return role;
    }

    public static DRole createNonExistingDRole(Long nonExistingId) {
        DRole role = new DRole();
        role.setId(nonExistingId);
        role.setAuthority("ROLE_TEST");
        return role;
    }
}
