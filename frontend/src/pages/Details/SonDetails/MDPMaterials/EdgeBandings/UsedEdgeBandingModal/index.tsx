import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useForm } from "react-hook-form";
import { toast } from 'react-toastify';
import * as usedEdgeBandingService from 'services/MDP/usedEdgeBandingService';
import * as EdgeBandingService from 'services/MDP/edgeBandingService';
import { DUsedEdgeBanding, DEdgeBanding } from 'models/entities';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";

type UsedEdgeBandingModalProps = {
    usedEdgeBanding?: DUsedEdgeBanding;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
    mdpSonCode : number;
}

const UsedEdgeBandingModal: React.FC<UsedEdgeBandingModalProps> = ({ usedEdgeBanding, isOpen, isEditing, onClose, onDeleteOrEdit, mdpSonCode }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue } = useForm<DUsedEdgeBanding>();
  const [selectEdgeBandings, setSelectEdgeBandings] = useState<DEdgeBanding[]>();

  // load inputs

  useEffect(() => {
    if(isEditing && usedEdgeBanding){
        usedEdgeBandingService.findById(usedEdgeBanding.id)
        .then((response) => {
            const fetchedUsedEdgeBanding = response.data as DUsedEdgeBanding;

            setValue('id', fetchedUsedEdgeBanding.id);
            setValue('edgeBandingCode', fetchedUsedEdgeBanding.edgeBandingCode);
            setValue('mdpSonCode', mdpSonCode);
            setValue('grossQuantity', fetchedUsedEdgeBanding.grossQuantity);
            setValue('netQuantity', fetchedUsedEdgeBanding.netQuantity);
            setValue('measurementUnit', fetchedUsedEdgeBanding.measurementUnit);
        });
    } else if (!isEditing && usedEdgeBanding){
        setValue('mdpSonCode', mdpSonCode);
    }
  }, [isEditing, usedEdgeBanding, setValue, mdpSonCode]);

    // populate comboboxes

    useEffect(() => {

        if(usedEdgeBanding?.edgeBandingCode){
            EdgeBandingService.findAllActiveAndCurrentOne(usedEdgeBanding?.edgeBandingCode)
                .then(response => setSelectEdgeBandings(response.data));
        } else{
            EdgeBandingService.findAll('')
                .then(response => setSelectEdgeBandings(response.data.content));
        }
    
    }, [usedEdgeBanding?.edgeBandingCode]);

  // insert / update method

  const insertOrUpdate = (formData: DUsedEdgeBanding) => {

    const serviceFunction = isEditing ? usedEdgeBandingService.update : usedEdgeBandingService.insert;
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
                        {...register("mdpSonCode", {
                        required: 'Campo obrigatório', minLength: 7
                        })}
                        type="number"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.mdpSonCode ? 'is-invalid' : ''}`}
                        placeholder="Código do Filho"
                        name="mdpSonCode"
                        disabled
                    />
                    <div className='invalid-feedback d-block'>{errors.mdpSonCode?.message}</div>
                </div> 
                <div className='margin-bottom-10'>
                    <label htmlFor="">Chapa</label> 
                    <select
                        {...register("edgeBandingCode", {
                            required: 'Campo obrigatório',
                        })}
                        className={`form-control base-input ${errors.edgeBandingCode ? 'is-invalid' : ''}`}
                        placeholder='Chapa' 
                        name="edgeBandingCode"
                    >
                        {selectEdgeBandings && selectEdgeBandings.length > 0 ? (
                            selectEdgeBandings.map(EdgeBanding => (
                                <option key={EdgeBanding.code} value={EdgeBanding.code}>
                                    {EdgeBanding.description}
                                </option>
                            ))
                        ) : (
                            <option value="" disabled>
                                Carregando opções...
                            </option>
                        )}
                    </select>
                    {errors.edgeBandingCode && (
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

export default UsedEdgeBandingModal;
