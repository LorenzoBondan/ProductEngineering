import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useForm } from "react-hook-form";
import { toast } from 'react-toastify';
import * as usedPaintingService from 'services/MDF/usedPaintingService';
import * as PaintingService from 'services/MDF/paintingService';
import { DUsedPainting, DPainting } from 'models/entities';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";

type UsedPaintingModalProps = {
    usedPainting?: DUsedPainting;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
    paintingSonCode : number;
}

const UsedPaintingModal: React.FC<UsedPaintingModalProps> = ({ usedPainting, isOpen, isEditing, onClose, onDeleteOrEdit, paintingSonCode }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue } = useForm<DUsedPainting>();
  const [selectPaintings, setSelectPaintings] = useState<DPainting[]>();

  // load inputs

  useEffect(() => {
    if(isEditing && usedPainting){
        usedPaintingService.findById(usedPainting.id)
        .then((response) => {
            const fetchedUsedPainting = response.data as DUsedPainting;

            setValue('id', fetchedUsedPainting.id);
            setValue('paintingCode', fetchedUsedPainting.paintingCode);
            setValue('paintingSonCode', paintingSonCode);
            setValue('grossQuantity', fetchedUsedPainting.grossQuantity);
            setValue('netQuantity', fetchedUsedPainting.netQuantity);
            setValue('measurementUnit', fetchedUsedPainting.measurementUnit);
        });
    } else if (!isEditing && usedPainting){
        setValue('paintingSonCode', paintingSonCode);
    }
  }, [isEditing, usedPainting, setValue, paintingSonCode]);

    // populate comboboxes

    useEffect(() => {

        if(usedPainting?.paintingCode){
            PaintingService.findAllActiveAndCurrentOne(usedPainting?.paintingCode)
                .then(response => setSelectPaintings(response.data));
        } else{
            PaintingService.findAll('')
                .then(response => setSelectPaintings(response.data.content));
        }
    
    }, [usedPainting?.paintingCode]);

  // insert / update method

  const insertOrUpdate = (formData: DUsedPainting) => {

    const serviceFunction = isEditing ? usedPaintingService.update : usedPaintingService.insert;
    const successMessage = isEditing ? 'Pintura utilizada editada!' : 'Pintura utilizada inserida!';
  
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
                    <label htmlFor="">Pintura</label> 
                    <select
                        {...register("paintingCode", {
                            required: 'Campo obrigatório',
                        })}
                        className={`form-control base-input ${errors.paintingCode ? 'is-invalid' : ''}`}
                        placeholder='Chapa' 
                        name="paintingCode"
                    >
                        {selectPaintings && selectPaintings.length > 0 ? (
                            selectPaintings.map(Painting => (
                                <option key={Painting.code} value={Painting.code}>
                                    {Painting.description}
                                </option>
                            ))
                        ) : (
                            <option value="" disabled>
                                Carregando opções...
                            </option>
                        )}
                    </select>
                    {errors.paintingCode && (
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

export default UsedPaintingModal;
