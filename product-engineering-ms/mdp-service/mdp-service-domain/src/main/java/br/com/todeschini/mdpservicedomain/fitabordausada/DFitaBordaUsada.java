package br.com.todeschini.mdpservicedomain.fitabordausada;

import br.com.todeschini.itemservicedomain.materialusado.DMaterialUsado;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libvalidationhandler.util.FormatadorNumeros;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DFitaBordaUsada extends DMaterialUsado {

    public DFitaBordaUsada(Integer codigo){
        this.setCodigo(codigo);
    }

    @Override
    public void validar() throws ValidationException {
        super.validar();
    }

    @Override
    public Double calcularQuantidadeLiquida() {
        double quantidadeLiquida = ((double) this.getFilho().getMedidas().getAltura() / 1000) * (double) this.getFilho().getPai().getBordasComprimento() +
                ((double) this.getFilho().getMedidas().getLargura() / 1000) * (double) this.getFilho().getPai().getBordasLargura();
        this.setQuantidadeLiquida(FormatadorNumeros.formatarQuantidade(quantidadeLiquida));
        return this.getQuantidadeLiquida();
    }
}
