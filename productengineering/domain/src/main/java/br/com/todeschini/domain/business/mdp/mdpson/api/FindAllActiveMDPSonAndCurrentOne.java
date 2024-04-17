package br.com.todeschini.domain.business.mdp.mdpson.api;

import br.com.todeschini.domain.business.mdp.mdpson.DMDPSon;

import java.util.List;

public interface FindAllActiveMDPSonAndCurrentOne {

    List<DMDPSon> findAllActiveAndCurrentOne (Long id);
}
