import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useForm } from "react-hook-form";
import FlatPicker from 'react-flatpickr';
import { toast } from 'react-toastify';
import * as GuideService from 'services/Guides/guideService';
import { DGuide } from 'models/entities';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";

type GuideModalProps = {
    Guide?: DGuide;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
}

const GuideModal: React.FC<GuideModalProps> = ({ Guide, isOpen, isEditing, onClose, onDeleteOrEdit }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue } = useForm<DGuide>();
  const [dateTime, setDateTime] = useState<Date | null>(null);
  const [finalDateTime, setFinalDateTime] = useState<Date | null>(null);

  // load inputs

  useEffect(() => {
    if(isEditing && Guide){
        GuideService.findById(Guide.id)
        .then((response) => {
            const fetchedGuide = response.data as DGuide;

            setValue('id', fetchedGuide.id);
            setValue('description', fetchedGuide.description);
            setValue('value', fetchedGuide.value);
            setValue('guideMachines', fetchedGuide.guideMachines);

            setDateTime(fetchedGuide.implementation ? new Date(fetchedGuide.implementation) : null);
            setFinalDateTime(fetchedGuide.finalDate ? new Date(fetchedGuide.finalDate) : null);
        });
    }
  }, [isEditing, Guide, setValue]);

  // insert / update method

  const insertOrUpdate = (formData: DGuide) => {
    if (dateTime !== null) {
      formData.implementation = dateTime;
    }
    if( finalDateTime !== null){
        formData.finalDate = finalDateTime;
    }
  
    const serviceFunction = isEditing ? GuideService.update : GuideService.insert;
    const successMessage = isEditing ? 'Roteiro editado!' : 'Roteiro Inserido!';
  
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
                    <label htmlFor="">Descrição</label>
                    <input 
                        {...register("description", {
                        required: 'Campo obrigatório', minLength: 3
                        })}
                        type="text"
                        className={`form-control base-input ${errors.description ? 'is-invalid' : ''}`}
                        placeholder="Descrição"
                        name="description"
                    />
                    <div className='invalid-feedGuide d-block'>{errors.description?.message}</div>
                </div>  
                <div className='margin-bottom-10'>
                    <label>Implementação</label>
                    <FlatPicker
                        value={dateTime || undefined}
                        onChange={(date) => setDateTime(date[0])}
                        options={{
                            enableTime: false,
                            dateFormat: 'Y-m-d',
                        }}
                        className="base-input time-input"
                        name="implementation"
                    />
                </div>
                <div className='margin-bottom-10'>
                    <label>Data Final</label>
                    <FlatPicker
                        value={finalDateTime || undefined}
                        onChange={(finalDate) => setFinalDateTime(finalDate[0])}
                        options={{
                            enableTime: false,
                            dateFormat: 'Y-m-d',
                        }}
                        className="base-input time-input"
                        name="finalDate"
                    />
                </div> 
            </div>
            <div className="col-lg-6 crud-modal-half-container">
                
                
            </div>
            {errorMessage && <div className='invalid-feedGuide d-block'>{errorMessage}</div>}
        </div>
        <div className="crud-modal-buttons-container">
          <button onClick={onClose} className="btn cancel-button crud-modal-button">Cancelar</button>
          <button type="submit" className="btn btn-primary text-white crud-modal-button">Salvar</button>
        </div>
      </form>
    </Modal>
  );
};

export default GuideModal;
