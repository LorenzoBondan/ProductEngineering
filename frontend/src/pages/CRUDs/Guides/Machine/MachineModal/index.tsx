import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useForm } from "react-hook-form";
import { toast } from 'react-toastify';
import * as machineService from 'services/Guides/machineService';
import * as machineGroupService from 'services/Guides/machineGroupService';
import { DMachine, DMachineGroup } from 'models/entities';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";

type MachineModalProps = {
    machine?: DMachine;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
}

const MachineModal: React.FC<MachineModalProps> = ({ machine, isOpen, isEditing, onClose, onDeleteOrEdit }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue } = useForm<DMachine>();
  const [selectMachineGroups, setSelectMachineGroups] = useState<DMachineGroup[]>();
  const machineGroupsIds = selectMachineGroups?.map(machineGroup => machineGroup.id);
  const machineGroupsNames = selectMachineGroups?.map(machineGroup => machineGroup.name);
  const [textFormula, setTextFormula] = useState<string>();

  // load inputs

  useEffect(() => {
    if(isEditing && machine){
        machineService.findById(machine.id)
        .then((response) => {
            const fetchedMachine = response.data as DMachine;

            setValue('id', fetchedMachine.id);
            setValue('name', fetchedMachine.name);
            setValue('formula', fetchedMachine.formula);
            setValue('machineGroupId', fetchedMachine.machineGroupId);
        });
    }
  }, [isEditing, machine, setValue]);

  // populate comboboxes

  useEffect(() => {

    if(machine?.machineGroupId){
      machineGroupService.findAllActiveAndCurrentOne(machine?.machineGroupId)
        .then(response => setSelectMachineGroups(response.data));
    } else{
      machineGroupService.findAll('')
        .then(response => setSelectMachineGroups(response.data.content));
    }

  }, [machine?.machineGroupId]);

  // insert / update method

  const validateFormula = (formula: string | undefined) => {
    if(formula){
      const validCharacters = /^[a-zA-Z0-9+\-*/() ]*$/;
      return validCharacters.test(formula);
    }
  };

  const handleFormulaChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTextFormula(event.target.value);
  };  

  const insertOrUpdate = (formData: DMachine) => {
    
    if (!validateFormula(formData.formula.at(0))) {
      setErrorMessage('Fórmula inválida. Por favor, use apenas números, letras e os operadores +, -, *, /, (, ).');
      return;
    }

    if(textFormula){
      formData.formula = Array.from(textFormula);
    }
  
    const serviceFunction = isEditing ? machineService.update : machineService.insert;
    const successMessage = isEditing ? 'Máquina editada!' : 'Máquina inserida!';
  
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
                <div className='margin-bottom-10'>
                    <label htmlFor="">Fórmula</label>
                    <input 
                        {...register("formula", {
                        })}
                        type="text"
                        className={`form-control base-input ${errors.formula ? 'is-invalid' : ''}`}
                        placeholder="Fórmula"
                        name="formula"
                        onChange={handleFormulaChange}
                    />
                    <label htmlFor="">Para representar a medida 1 use x, para a medida 2 use y e para a medida 3 use z. Utilize parênteses e sinais aritméticos como +, -, * e /</label>
                    {errors.formula && <div className='invalid-feedback d-block'>{errors.formula.message}</div>}
                </div>
            </div>
            <div className="col-lg-6 crud-modal-half-container">
                <div className='margin-bottom-10'>
                    <label htmlFor="">Grupo de Máquina</label> 
                    <select
                        {...register("machineGroupId", {
                            required: 'Campo obrigatório',
                        })}
                        className={`form-control base-input ${errors.machineGroupId ? 'is-invalid' : ''}`}
                        placeholder='Grupo de Máquina' 
                        name="machineGroupId"
                    >
                        {machineGroupsIds?.map(id => <option key={id} value={id} label={machineGroupsNames && machineGroupsNames[id-1]}></option>)}
                    </select>
                    {errors.machineGroupId && (
                        <div className='invalid-feedback d-block'>Campo obrigatório</div>
                    )}
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

export default MachineModal;
