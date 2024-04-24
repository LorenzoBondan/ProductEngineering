import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useForm } from "react-hook-form";
import { toast } from 'react-toastify';
import * as MachineGroupService from 'services/Guides/machineGroupService';
import { DMachineGroup } from 'models/entities';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";

type MachineGroupModalProps = {
    MachineGroup?: DMachineGroup;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
}

const MachineGroupModal: React.FC<MachineGroupModalProps> = ({ MachineGroup, isOpen, isEditing, onClose, onDeleteOrEdit }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue } = useForm<DMachineGroup>();

  // load inputs

  useEffect(() => {
    if(isEditing && MachineGroup){
        MachineGroupService.findById(MachineGroup.id)
        .then((response) => {
            const fetchedMachineGroup = response.data as DMachineGroup;

            setValue('id', fetchedMachineGroup.id);
            setValue('name', fetchedMachineGroup.name);
        });
    }
  }, [isEditing, MachineGroup, setValue]);

  // insert / update method

  const insertOrUpdate = (formData: DMachineGroup) => {
  
    const serviceFunction = isEditing ? MachineGroupService.update : MachineGroupService.insert;
    const successMessage = isEditing ? 'Grupo de máquina editado!' : 'Grupo de máquina inserido!';
  
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
                    <label htmlFor="">Nome</label>
                    <input 
                        {...register("name", {
                        required: 'Campo obrigatório', minLength: 3
                        })}
                        type="text"
                        className={`form-control base-input ${errors.name ? 'is-invalid' : ''}`}
                        placeholder="Nome"
                        name="name"
                    />
                    <div className='invalid-feedback d-block'>{errors.name?.message}</div>
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

export default MachineGroupModal;
