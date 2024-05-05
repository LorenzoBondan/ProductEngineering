import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useForm } from "react-hook-form";
import { toast } from 'react-toastify';
import * as usedPaintingBorderBackgroundService from 'services/MDF/usedPaintingBorderBackgroundService';
import * as PaintingBorderBackgroundService from 'services/MDF/paintingBorderBackgroundService';
import { DUsedPaintingBorderBackground, DPaintingBorderBackground } from 'models/entities';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";

type UsedPaintingBorderBackgroundModalProps = {
    usedPaintingBorderBackground?: DUsedPaintingBorderBackground;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
    paintingSonCode : number;
}

const UsedPaintingBorderBackgroundModal: React.FC<UsedPaintingBorderBackgroundModalProps> = ({ usedPaintingBorderBackground, isOpen, isEditing, onClose, onDeleteOrEdit, paintingSonCode }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue } = useForm<DUsedPaintingBorderBackground>();
  const [selectPaintingBorderBackgrounds, setSelectPaintingBorderBackgrounds] = useState<DPaintingBorderBackground[]>();

  // load inputs

  useEffect(() => {
    if(isEditing && usedPaintingBorderBackground){
        usedPaintingBorderBackgroundService.findById(usedPaintingBorderBackground.id)
        .then((response) => {
            const fetchedUsedPaintingBorderBackground = response.data as DUsedPaintingBorderBackground;

            setValue('id', fetchedUsedPaintingBorderBackground.id);
            setValue('paintingBorderBackgroundCode', fetchedUsedPaintingBorderBackground.paintingBorderBackgroundCode);
            setValue('paintingSonCode', paintingSonCode);
            setValue('grossQuantity', fetchedUsedPaintingBorderBackground.grossQuantity);
            setValue('netQuantity', fetchedUsedPaintingBorderBackground.netQuantity);
            setValue('measurementUnit', fetchedUsedPaintingBorderBackground.measurementUnit);
        });
    } else if (!isEditing && usedPaintingBorderBackground){
        setValue('paintingSonCode', paintingSonCode);
    }
  }, [isEditing, usedPaintingBorderBackground, setValue, paintingSonCode]);

    // populate comboboxes

    useEffect(() => {

        if(usedPaintingBorderBackground?.paintingBorderBackgroundCode){
            PaintingBorderBackgroundService.findAllActiveAndCurrentOne(usedPaintingBorderBackground?.paintingBorderBackgroundCode)
                .then(response => setSelectPaintingBorderBackgrounds(response.data));
        } else{
            PaintingBorderBackgroundService.findAll('')
                .then(response => setSelectPaintingBorderBackgrounds(response.data.content));
        }
    
    }, [usedPaintingBorderBackground?.paintingBorderBackgroundCode]);

  // insert / update method

  const insertOrUpdate = (formData: DUsedPaintingBorderBackground) => {

    const serviceFunction = isEditing ? usedPaintingBorderBackgroundService.update : usedPaintingBorderBackgroundService.insert;
    const successMessage = isEditing ? 'Chapa utilizada editada!' : 'Chapa utilizada inserida!';
  
    serviceFunction(formData)
      .then(response => {
        console.log(response.data);
        setErrorMessage('');
        toast.success(successMessage);
        onClose();
        onDeleteOrEdit();
      })
      .catch(error => {
        toast.error(error.response.data.error);
        setErrorMessage(error.response.data.error);
      });
  };

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={onClose}
      contentLabel="Example Modal"
      overlayClassName="modal-overlay"
      className="modal-content"
    >
      <form onSubmit={handleSubmit(insertOrUpdate)} className="crud-modal-form">
        {isEditing ? <div className='crud-modal-title'><EditSvg/><h2>Editar</h2></div> : <div className='crud-modal-title'><AddSvg/><h2>Adicionar</h2></div>}
        <div className="row crud-modal-inputs-container">
            <div className="col-lg-6 crud-modal-half-container">
                <div className='margin-bottom-10'>
                    <label htmlFor="">Código do Filho</label>
                    <input 
                        {...register("paintingSonCode", {
                        required: 'Campo obrigatório', minLength: 7
                        })}
                        type="number"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.paintingSonCode ? 'is-invalid' : ''}`}
                        placeholder="Código do Filho"
                        name="paintingSonCode"
                        disabled
                    />
                    <div className='invalid-feedback d-block'>{errors.paintingSonCode?.message}</div>
                </div> 
                <div className='margin-bottom-10'>
                    <label htmlFor="">Pintura de Borda de Fundo</label> 
                    <select
                        {...register("paintingBorderBackgroundCode", {
                            required: 'Campo obrigatório',
                        })}
                        className={`form-control base-input ${errors.paintingBorderBackgroundCode ? 'is-invalid' : ''}`}
                        placeholder='Chapa' 
                        name="paintingBorderBackgroundCode"
                    >
                        {selectPaintingBorderBackgrounds && selectPaintingBorderBackgrounds.length > 0 ? (
                            selectPaintingBorderBackgrounds.map(PaintingBorderBackground => (
                                <option key={PaintingBorderBackground.code} value={PaintingBorderBackground.code}>
                                    {PaintingBorderBackground.description}
                                </option>
                            ))
                        ) : (
                            <option value="" disabled>
                                Carregando opções...
                            </option>
                        )}
                    </select>
                    {errors.paintingBorderBackgroundCode && (
                        <div className='invalid-feedback d-block'>Campo obrigatório</div>
                    )}
                </div>
            </div>
            <div className="col-lg-6 crud-modal-half-container">
                <div className='margin-bottom-10'>
                    <label htmlFor="">Quantidade Líquida</label>
                    <input 
                        {...register("netQuantity", {
                        })}
                        type="text"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.netQuantity ? 'is-invalid' : ''}`}
                        placeholder="Quantidade Líquida"
                        name="netQuantity"
                    />
                    {errors.netQuantity && <div className='invalid-feedback d-block'>{errors.netQuantity.message}</div>}
                </div>
                <div className='margin-bottom-10'>
                    <label htmlFor="">Quantidade Bruta</label>
                    <input 
                        {...register("grossQuantity", {
                        })}
                        type="text"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.grossQuantity ? 'is-invalid' : ''}`}
                        placeholder="Quantidade Bruta"
                        name="grossQuantity"
                    />
                    {errors.grossQuantity && <div className='invalid-feedback d-block'>{errors.grossQuantity.message}</div>}
                </div>
            </div>
            {errorMessage && <div className='invalid-feedback d-block'>{errorMessage}</div>}
        </div>
        <div className="crud-modal-buttons-container">
          <button onClick={onClose} className="btn cancel-button crud-modal-button">Cancelar</button>
          <button type="submit" className="btn btn-primary text-white crud-modal-button">Salvar</button>
        </div>
      </form>
    </Modal>
  );
};

export default UsedPaintingBorderBackgroundModal;
