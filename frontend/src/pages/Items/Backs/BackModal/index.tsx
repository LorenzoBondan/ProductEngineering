import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useForm } from "react-hook-form";
import FlatPicker from 'react-flatpickr';
import { toast } from 'react-toastify';
import * as BackService from 'services/MDF/backService';
import { DBack } from 'models/entities';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";

type BackModalProps = {
    Back?: DBack;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
}

const BackModal: React.FC<BackModalProps> = ({ Back, isOpen, isEditing, onClose, onDeleteOrEdit }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue } = useForm<DBack>();
  const [dateTime, setDateTime] = useState<Date | null>(null);

  // load inputs

  useEffect(() => {
    if(isEditing && Back){
        BackService.findById(Back.code)
        .then((response) => {
            const fetchedBack = response.data as DBack;

            setValue('code', fetchedBack.code);
            setValue('description', fetchedBack.description);
            setValue('measure1', fetchedBack.measure1);
            setValue('measure2', fetchedBack.measure2);
            setValue('thickness', fetchedBack.thickness);
            setValue('suffix', fetchedBack.suffix);
            setValue('value', fetchedBack.value);
            setValue('color', fetchedBack.color);
            setValue('family', fetchedBack.family);

            setDateTime(fetchedBack.implementation ? new Date(fetchedBack.implementation) : null);
        });
    }
  }, [isEditing, Back, setValue]);

  // insert / update method

  const insertOrUpdate = (formData: DBack) => {
    if (dateTime !== null) {
      formData.implementation = dateTime;
    }
  
    const serviceFunction = isEditing ? BackService.update : BackService.insert;
    const successMessage = isEditing ? 'Fundo editado!' : 'Fundo Inserido!';
  
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
                        required: 'Campo obrigatório', minLength: 8
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
                    <label htmlFor="">Espessura</label>
                    <input 
                        {...register("thickness", {
                        })}
                        type="number"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.thickness ? 'is-invalid' : ''}`}
                        placeholder="Espessura"
                        name="thickness"
                    />
                    {errors.thickness && <div className='invalid-feedback d-block'>{errors.thickness.message}</div>}
                </div>
            </div>
            <div className="col-lg-6 crud-modal-half-container">
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
                    <label htmlFor="">Família</label>
                    <input 
                        {...register("family", {
                        required: false
                        })}
                        type="text"
                        className={`form-control base-input ${errors.family ? 'is-invalid' : ''}`}
                        placeholder="Família"
                        name="family"
                    />
                    <div className='invalid-feedback d-block'>{errors.family?.message}</div>
                </div>  
                <div className='margin-bottom-10'>
                    <label htmlFor="">Sufixo</label>
                    <input 
                        {...register("suffix", {
                        required: 'Campo obrigatório', minLength: 2
                        })}
                        type="number"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.suffix ? 'is-invalid' : ''}`}
                        placeholder="Sufixo"
                        name="suffix"
                        disabled={isEditing}
                    />
                    <div className='invalid-feedback d-block'>{errors.suffix?.message}</div>
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

export default BackModal;
