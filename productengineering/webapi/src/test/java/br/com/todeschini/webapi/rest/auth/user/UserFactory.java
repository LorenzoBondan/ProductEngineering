package br.com.todeschini.webapi.rest.auth.user;

import br.com.todeschini.domain.business.auth.role.DRole;
import br.com.todeschini.domain.business.auth.user.DUser;

import java.util.List;

public class UserFactory {

    public static DUser createDUserAdmin() {
        DUser user = new DUser();
        user.setId(1L);
        user.setName("Admin");
        user.setEmail("admin@admin.com");
        user.setPassword("admin");
        user.setImgUrl("https://admin.com");
        user.setRoles(List.of(new DRole(1L, "ROLE_ADMIN")));
        return user;
    }

    public static DUser createDUserClient() {
        DUser user = new DUser();
        user.setId(2L);
        user.setName("Client");
        user.setEmail("client@client.com");
        user.setPassword("client");
        user.setImgUrl("https://client.com");
        user.setRoles(List.of(new DRole(2L, "ROLE_CLIENT")));
        return user;
    }

    public static DUser createDuplicatedDUser() {
        DUser user = new DUser();
        user.setId(3L);
        user.setName("Client");
        user.setEmail("client@client.com");
        user.setPassword("client");
        user.setImgUrl("https://client.com");
        user.setRoles(List.of(new DRole(2L, "ROLE_CLIENT")));
        return user;
    }

    public static DUser createNonExistingDUser(Long nonExistingId) {
        DUser user = new DUser();
        user.setId(nonExistingId);
        user.setName("Client");
        user.setEmail("client@client.com");
        user.setPassword("client");
        user.setImgUrl("https://client.com");
        user.setRoles(List.of(new DRole(2L, "ROLE_CLIENT")));
        return user;
    }
}
