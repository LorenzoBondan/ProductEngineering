package br.com.todeschini.persistence.entities.mdf;

import br.com.todeschini.persistence.entities.publico.Son;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("PaintingSon")
public class PaintingSon extends Son implements Serializable {

    private Boolean satin;
    private Boolean highShine;
    private Boolean satinGlass;
    private Integer faces;
    private Boolean special;
    private Integer suffix;

    @ManyToOne
    @JoinColumn(name = "back_id")
    private Back back;

    @OneToMany(mappedBy = "paintingSon", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<UsedPainting> paintings = new ArrayList<>();

    @OneToMany(mappedBy = "paintingSon", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<UsedPaintingBorderBackground> paintingBorderBackgrounds = new ArrayList<>();

    @OneToMany(mappedBy = "paintingSon", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<UsedPolyester> polyesters = new ArrayList<>();

    public PaintingSon(Son son){
        this.setCode(son.getCode());
        this.setDescription(son.getDescription());
        this.setMeasure1(son.getMeasure1());
        this.setMeasure2(son.getMeasure2());
        this.setMeasure3(son.getMeasure3());
        this.setColor(son.getColor());
        this.setFather(son.getFather());
        this.setSatin(false);
        this.setHighShine(false);
        this.setSatinGlass(false);
        this.setSpecial(false);
        this.setSuffix(99);
        this.setFaces(1);
        //this.setGuide
    }

    public void generateSuffix(){
        if(faces == 1){
            suffix = 99;
            if(special){
                suffix = 77;
            }
        }
        else if(faces == 2){
            suffix = 55;
            if(special){
                suffix = 44;
            }
        }
    }

    public Double calculateValue() {
        double value = 0;
        for(UsedPainting painting : paintings){
            value += painting.getPainting().getValue() * painting.getNetQuantity();
        }
        for(UsedPaintingBorderBackground paintingBorderBackground : paintingBorderBackgrounds){
            value += paintingBorderBackground.getPaintingBorderBackground().getValue() * paintingBorderBackground.getNetQuantity();
        }
        for(UsedPolyester polyester : polyesters){
            value += polyester.getPolyester().getValue() * polyester.getNetQuantity();
        }
        for(UsedBackSheet usedBackSheet : back.getSheets()){
            value += usedBackSheet.getSheet().getValue() * usedBackSheet.getNetQuantity();
        }
        if(this.getGuide() != null){
            value += this.getGuide().calculateValue();
        }
        value = Math.round(value * 1e2) / 1e2;
        this.setValue(value);
        return value;
    }
}
