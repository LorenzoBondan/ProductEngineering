import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useForm } from "react-hook-form";
import { toast } from 'react-toastify';
import * as GuideMachineService from 'services/Guides/guideMachineService';
import * as machineService from 'services/Guides/machineService';
import { DGuideMachine, DMachine } from 'models/entities';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";

type UsedGuideMachineModalProps = {
    guideMachine?: DGuideMachine;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
    guideId : number;
}

const GuideMachineModal: React.FC<UsedGuideMachineModalProps> = ({ guideMachine, isOpen, isEditing, onClose, onDeleteOrEdit, guideId }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue } = useForm<DGuideMachine>();
  const [selectMachines, setSelectMachines] = useState<DMachine[]>();

  // load inputs

  useEffect(() => {
    if(isEditing && guideMachine){
        GuideMachineService.findById(guideMachine.id)
        .then((response) => {
            const fetcheGuideMachine = response.data as DGuideMachine;

            setValue('id', fetcheGuideMachine.id);
            setValue('guideId', guideId);
            setValue('machineId', fetcheGuideMachine.machineId);
            setValue('manTime', fetcheGuideMachine.manTime);
            setValue('machineTime', fetcheGuideMachine.machineTime);
            setValue('measurementUnit', fetcheGuideMachine.measurementUnit);
        });
    } else if (!isEditing && guideMachine){
        setValue('guideId', guideId);
    }
  }, [isEditing, guideMachine, setValue, guideId]);

    // populate comboboxes

    useEffect(() => {

        if(guideMachine?.machineId){
            machineService.findAllActiveAndCurrentOne(guideMachine?.machineId)
                .then(response => setSelectMachines(response.data));
        } else{
            machineService.findAll('')
                .then(response => setSelectMachines(response.data.content));
        }
    
    }, [guideMachine?.machineId]);

  // insert / update method

  const insertOrUpdate = (formData: DGuideMachine) => {

    const serviceFunction = isEditing ? GuideMachineService.update : GuideMachineService.insert;
    const successMessage = isEditing ? 'Maquinário editado!' : 'Maquinário inserido!';
  
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
                    <label htmlFor="">Código do Roteiro</label>
                    <input 
                        {...register("guideId", {
                        required: 'Campo obrigatório'
                        })}
                        type="number"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.guideId ? 'is-invalid' : ''}`}
                        placeholder="Código do Roteiro"
                        name="guideId"
                        disabled
                    />
                    <div className='invalid-feedback d-block'>{errors.guideId?.message}</div>
                </div> 
                <div className='margin-bottom-10'>
                    <label htmlFor="">Máquina</label> 
                    <select
                        {...register("machineId", {
                            required: 'Campo obrigatório',
                        })}
                        className={`form-control base-input ${errors.machineId ? 'is-invalid' : ''}`}
                        placeholder='Máquina' 
                        name="machineId"
                    >
                        {selectMachines && selectMachines.length > 0 ? (
                            selectMachines.map(machine => (
                                <option key={machine.id} value={machine.id}>
                                    {machine.name}
                                </option>
                            ))
                        ) : (
                            <option value="" disabled>
                                Carregando opções...
                            </option>
                        )}
                    </select>
                    {errors.machineId && (
                        <div className='invalid-feedback d-block'>Campo obrigatório</div>
                    )}
                </div>
            </div>
            <div className="col-lg-6 crud-modal-half-container">
                <div className='margin-bottom-10'>
                    <label htmlFor="">Tempo Máquina</label>
                    <input 
                        {...register("machineTime", {
                        })}
                        type="text"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.machineTime ? 'is-invalid' : ''}`}
                        placeholder="Quantidade Líquida"
                        name="machineTime"
                    />
                    {errors.machineTime && <div className='invalid-feedback d-block'>{errors.machineTime.message}</div>}
                </div>
                <div className='margin-bottom-10'>
                    <label htmlFor="">Tempo Homem</label>
                    <input 
                        {...register("manTime", {
                        })}
                        type="text"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.manTime ? 'is-invalid' : ''}`}
                        placeholder="Tempo Homem"
                        name="manTime"
                    />
                    {errors.manTime && <div className='invalid-feedback d-block'>{errors.manTime.message}</div>}
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

export default GuideMachineModal;
