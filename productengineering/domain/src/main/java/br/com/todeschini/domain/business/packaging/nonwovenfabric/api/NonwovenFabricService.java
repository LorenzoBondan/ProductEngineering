package br.com.todeschini.domain.business.packaging.nonwovenfabric.api;

import org.springframework.stereotype.Component;

@Component
public interface NonwovenFabricService extends FindNonwovenFabric, InsertNonwovenFabric, UpdateNonwovenFabric, DeleteNonwovenFabric, InactivateNonwovenFabric,
        FindAllActiveNonwovenFabricAndCurrentOne {
}
