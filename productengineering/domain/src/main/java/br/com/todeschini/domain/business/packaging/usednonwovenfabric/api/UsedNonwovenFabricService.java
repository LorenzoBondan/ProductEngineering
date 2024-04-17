package br.com.todeschini.domain.business.packaging.usednonwovenfabric.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedNonwovenFabricService extends FindUsedNonwovenFabric, InsertUsedNonwovenFabric, UpdateUsedNonwovenFabric, DeleteUsedNonwovenFabric, InactivateUsedNonwovenFabric,
        FindAllActiveUsedNonwovenFabricAndCurrentOne {
}
