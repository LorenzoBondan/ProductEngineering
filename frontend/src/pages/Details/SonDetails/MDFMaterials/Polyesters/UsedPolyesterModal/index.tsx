import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useForm } from "react-hook-form";
import { toast } from 'react-toastify';
import * as usedPolyesterService from 'services/MDF/usedPolyesterService';
import * as PolyesterService from 'services/MDF/polyesterService';
import { DUsedPolyester, DPolyester } from 'models/entities';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";

type UsedPolyesterModalProps = {
    usedPolyester?: DUsedPolyester;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
    paintingSonCode : number;
}

const UsedPolyesterModal: React.FC<UsedPolyesterModalProps> = ({ usedPolyester, isOpen, isEditing, onClose, onDeleteOrEdit, paintingSonCode }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue } = useForm<DUsedPolyester>();
  const [selectPolyesters, setSelectPolyesters] = useState<DPolyester[]>();

  // load inputs

  useEffect(() => {
    if(isEditing && usedPolyester){
        usedPolyesterService.findById(usedPolyester.id)
        .then((response) => {
            const fetchedUsedPolyester = response.data as DUsedPolyester;

            setValue('id', fetchedUsedPolyester.id);
            setValue('polyesterCode', fetchedUsedPolyester.polyesterCode);
            setValue('paintingSonCode', paintingSonCode);
            setValue('grossQuantity', fetchedUsedPolyester.grossQuantity);
            setValue('netQuantity', fetchedUsedPolyester.netQuantity);
            setValue('measurementUnit', fetchedUsedPolyester.measurementUnit);
        });
    } else if (!isEditing && usedPolyester){
        setValue('paintingSonCode', paintingSonCode);
    }
  }, [isEditing, usedPolyester, setValue, paintingSonCode]);

    // populate comboboxes

    useEffect(() => {

        if(usedPolyester?.polyesterCode){
            PolyesterService.findAllActiveAndCurrentOne(usedPolyester?.polyesterCode)
                .then(response => setSelectPolyesters(response.data));
        } else{
            PolyesterService.findAll('')
                .then(response => setSelectPolyesters(response.data.content));
        }
    
    }, [usedPolyester?.polyesterCode]);

  // insert / update method

  const insertOrUpdate = (formData: DUsedPolyester) => {

    const serviceFunction = isEditing ? usedPolyesterService.update : usedPolyesterService.insert;
    const successMessage = isEditing ? 'Poliéster utilizado editado!' : 'Poliéster utilizado inserido!';
  
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
                        name="PolyesterSonCode"
                        disabled
                    />
                    <div className='invalid-feedback d-block'>{errors.paintingSonCode?.message}</div>
                </div> 
                <div className='margin-bottom-10'>
                    <label htmlFor="">Poliéster</label> 
                    <select
                        {...register("polyesterCode", {
                            required: 'Campo obrigatório',
                        })}
                        className={`form-control base-input ${errors.polyesterCode ? 'is-invalid' : ''}`}
                        placeholder='Chapa' 
                        name="polyesterCode"
                    >
                        {selectPolyesters && selectPolyesters.length > 0 ? (
                            selectPolyesters.map(Polyester => (
                                <option key={Polyester.code} value={Polyester.code}>
                                    {Polyester.description}
                                </option>
                            ))
                        ) : (
                            <option value="" disabled>
                                Carregando opções...
                            </option>
                        )}
                    </select>
                    {errors.polyesterCode && (
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

export default UsedPolyesterModal;
