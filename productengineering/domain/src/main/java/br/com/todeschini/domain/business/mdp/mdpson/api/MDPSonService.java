package br.com.todeschini.domain.business.mdp.mdpson.api;

import org.springframework.stereotype.Component;

@Component
public interface MDPSonService extends FindMDPSon, InsertMDPSon, UpdateMDPSon, DeleteMDPSon, InactivateMDPSon,
        FindAllActiveMDPSonAndCurrentOne {
}
