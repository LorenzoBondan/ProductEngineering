package br.com.todeschini.userservicepersistence.projections;

public interface UserDetailsProjection {
    String getUsername();
    String getPassword();
    Integer getRoleId();
    String getAuthority();
}
