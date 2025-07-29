package br.com.todeschini.mdpservicedomain.colausada;

import br.com.todeschini.itemservicedomain.materialusado.DMaterialUsado;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libvalidationhandler.util.FormatadorNumeros;
import br.com.todeschini.mdpservicedomain.cola.DCola;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DColaUsada extends DMaterialUsado {

    public DColaUsada(Integer codigo){
        this.setCodigo(codigo);
    }

    @Override
    public void validar() throws ValidationException {
        super.validar();
    }

    @Override
    public Double calcularQuantidadeLiquida(){
        double quantidadeFitaBorda = ((double) this.getFilho().getMedidas().getAltura() / 1000) *
                (double) this.getFilho().getPai().getBordasComprimento() +
                ((double) this.getFilho().getMedidas().getLargura() / 1000) * (double) this.getFilho().getPai().getBordasComprimento();

        DCola cola = (DCola) this.getMaterial();
        double quantidadeLiquida = ((double) this.getFilho().getMedidas().getEspessura() / 1000 *
                quantidadeFitaBorda * cola.getGramatura());
        this.setQuantidadeLiquida(FormatadorNumeros.formatarQuantidade(quantidadeLiquida));
        return this.getQuantidadeLiquida();
    }
}
