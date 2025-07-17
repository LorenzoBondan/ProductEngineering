package br.com.todeschini.libauditpersistence.services;

import br.com.todeschini.libauditpersistence.entities.AuditoriaInfo;
import br.com.todeschini.libauditpersistence.entities.enums.SituacaoEnum;
import br.com.todeschini.libauditpersistence.projections.AuditoriaProjection;
import br.com.todeschini.libauditpersistence.repositories.AuditoriaRepository;
import br.com.todeschini.libauditpersistence.utils.JpaMetadataUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Serviço utilizado para definir os atributos de Auditoria: criadopor, criadoem e situacao ao atualizar um objeto genérico
 */
@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public <T extends AuditoriaInfo> void setCreationProperties(T obj) {
        // obtém a classe e ID da entidade
        Class<?> entityClass = obj.getClass();
        Integer id = JpaMetadataUtils.extractId(obj);

        // recupera os dados de auditoria e preenche o objeto
        AuditoriaProjection auditoria = auditoriaRepository.findAuditoriaById(entityClass, id);
        obj.setCriadoem(auditoria.criadoEm());
        obj.setCriadopor(auditoria.criadoPor());
        obj.setSituacao(SituacaoEnum.valueOf(auditoria.situacao()));
    }
}
