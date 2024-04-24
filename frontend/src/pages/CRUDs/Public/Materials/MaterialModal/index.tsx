import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useForm } from "react-hook-form";
import { toast } from 'react-toastify';
import * as MaterialService from 'services/public/materialService';
import { DMaterial } from 'models/entities';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";

type MaterialModalProps = {
    Material?: DMaterial;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
}

const MaterialModal: React.FC<MaterialModalProps> = ({ Material, isOpen, isEditing, onClose, onDeleteOrEdit }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue } = useForm<DMaterial>();

  // load inputs

  useEffect(() => {
    if(isEditing && Material){
        MaterialService.findById(Material.id)
        .then((response) => {
            const fetchedMaterial = response.data as DMaterial;

            setValue('id', fetchedMaterial.id);
            setValue('name', fetchedMaterial.name);
        });
    }
  }, [isEditing, Material, setValue]);

  // insert / update method

  const insertOrUpdate = (formData: DMaterial) => {
  
    const serviceFunction = isEditing ? MaterialService.update : MaterialService.insert;
    const successMessage = isEditing ? 'Material editado!' : 'Material inserido!';
  
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

export default MaterialModal;
