import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useForm } from "react-hook-form";
import FlatPicker from 'react-flatpickr';
import { toast } from 'react-toastify';
import * as MoldingService from 'services/Aluminium/moldingService';
import { DMolding } from 'models/entities';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";

type MoldingModalProps = {
    Molding?: DMolding;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
}

const MoldingModal: React.FC<MoldingModalProps> = ({ Molding, isOpen, isEditing, onClose, onDeleteOrEdit }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue } = useForm<DMolding>();
  const [dateTime, setDateTime] = useState<Date | null>(null);

  // load inputs

  useEffect(() => {
    if(isEditing && Molding){
        MoldingService.findById(Molding.code)
        .then((response) => {
            const fetchedMolding = response.data as DMolding;

            setValue('code', fetchedMolding.code);
            setValue('description', fetchedMolding.description);
            setValue('measure1', fetchedMolding.measure1);
            setValue('measure2', fetchedMolding.measure2);
            setValue('measure3', fetchedMolding.measure3);
            setValue('measurementUnit', fetchedMolding.measurementUnit);
            setValue('value', fetchedMolding.value);

            setDateTime(fetchedMolding.implementation ? new Date(fetchedMolding.implementation) : null);
        });
    }
  }, [isEditing, Molding, setValue]);

  // insert / update method

  const insertOrUpdate = (formData: DMolding) => {
    if (dateTime !== null) {
      formData.implementation = dateTime;
    }
  
    const serviceFunction = isEditing ? MoldingService.update : MoldingService.insert;
    const successMessage = isEditing ? 'Puxador editado!' : 'Puxador Inserido!';
  
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
                    <label htmlFor="">Código</label>
                    <input 
                        {...register("code", {
                        required: 'Campo obrigatório', minLength: 7
                        })}
                        type="number"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.code ? 'is-invalid' : ''}`}
                        placeholder="Código"
                        name="code"
                        disabled={isEditing}
                    />
                    <div className='invalid-feedback d-block'>{errors.code?.message}</div>
                </div> 
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
                    <div className='invalid-feedback d-block'>{errors.description?.message}</div>
                </div>  
            </div>
            <div className="col-lg-6 crud-modal-half-container">
                <div className='margin-bottom-10'>
                    <label htmlFor="">Medida 1</label>
                    <input 
                        {...register("measure1", {
                        })}
                        type="number"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.measure1 ? 'is-invalid' : ''}`}
                        placeholder="Medida 1"
                        name="measure1"
                    />
                    {errors.measure1 && <div className='invalid-feedback d-block'>{errors.measure1.message}</div>}
                </div>
                <div className='margin-bottom-10'>
                    <label htmlFor="">Medida 2</label>
                    <input 
                        {...register("measure2", {
                        })}
                        type="number"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.measure2 ? 'is-invalid' : ''}`}
                        placeholder="Medida 2"
                        name="measure2"
                    />
                    {errors.measure2 && <div className='invalid-feedback d-block'>{errors.measure2.message}</div>}
                </div>
                <div className='margin-bottom-10'>
                    <label htmlFor="">Medida 3</label>
                    <input 
                        {...register("measure3", {
                        })}
                        type="number"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.measure3 ? 'is-invalid' : ''}`}
                        placeholder="Medida 3"
                        name="measure3"
                    />
                    {errors.measure3 && <div className='invalid-feedback d-block'>{errors.measure3.message}</div>}
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
                    <label htmlFor="">Valor por Metro</label>
                    <input 
                        {...register("value", {
                            pattern: {
                                value: /^\d+(\.\d{1,2})?$/, 
                                message: 'Por favor, insira um número válido'
                            }
                        })}
                        type="text" 
                        inputMode="numeric" 
                        className={`form-control base-input ${errors.value ? 'is-invalid' : ''}`}
                        placeholder="Valor"
                        name="value"
                    />
                    {errors.value && <div className='invalid-feedback d-block'>{errors.value.message}</div>}
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

export default MoldingModal;
