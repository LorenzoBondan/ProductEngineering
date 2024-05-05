import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { Controller, useForm } from "react-hook-form";
import FlatPicker from 'react-flatpickr';
import { toast } from 'react-toastify';
import * as fatherService from 'services/public/fatherService';
import * as colorService from 'services/public/colorService';
import * as ghostService from 'services/Packaging/ghostService';
import { DColor, DFather, DGhost } from 'models/entities';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";
import Select from 'react-select';

type FatherModalProps = {
    father?: DFather;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
}

const FatherModal: React.FC<FatherModalProps> = ({ father, isOpen, isEditing, onClose, onDeleteOrEdit }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue, control } = useForm<DFather>();
  const [selectColors, setSelectColors] = useState<DColor[]>();
  const [selectGhost, setSelectGhost] = useState<DGhost[]>();
  const [dateTime, setDateTime] = useState<Date | null>(null);

  // load inputs

  useEffect(() => {
    if(isEditing && father){
        fatherService.findById(father.code)
        .then((response) => {
            const fetchedFather = response.data as DFather;

            setValue('code', fetchedFather.code);
            setValue('description', fetchedFather.description);
            setValue('measure1', fetchedFather.measure1);
            setValue('measure2', fetchedFather.measure2);
            setValue('measure3', fetchedFather.measure3);
            setValue('measurementUnit', fetchedFather.measurementUnit);
            setValue('value', fetchedFather.value);
            setValue('color', fetchedFather.color);
            setValue('ghost', fetchedFather.ghost);

            setDateTime(fetchedFather.implementation ? new Date(fetchedFather.implementation) : null);
        });
    }
  }, [isEditing, father, setValue]);

    // populate comboboxes

    useEffect(() => {

        if(father?.color){
            colorService.findAllActiveAndCurrentOne(father?.color.code)
                .then(response => setSelectColors(response.data));
        } else{
            colorService.findAll('')
                .then(response => setSelectColors(response.data.content));
        }
    
        if(father?.ghost){
            ghostService.findAllActiveAndCurrentOne(father.ghost.code)
                .then(response => setSelectGhost(response.data));
        } else {
            ghostService.findAll('')
                .then(response => setSelectGhost(response.data.content));
        }
    
    }, [father?.color, father?.ghost]);

  // insert / update method

  const insertOrUpdate = (formData: DFather) => {
    if (dateTime !== null) {
      formData.implementation = dateTime;
    }
  
    const serviceFunction = isEditing ? fatherService.update : fatherService.insert;
    const successMessage = isEditing ? 'Pai editado!' : 'Pai Inserido!';
  
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
                    <label htmlFor="">Cor</label> 
                    <Controller 
                        name = 'color'
                        rules = {{required: true}}
                        control = {control}
                        render = {( {field} ) => (
                            <Select 
                                {...field}
                                options={selectColors}
                                classNamePrefix="margin-bottom-20"
                                className="margin-bottom-20"
                                placeholder="Cor"
                                getOptionLabel={(color: DColor) => color.name}
                                getOptionValue={(color: DColor) => color.code.toString()}
                            />    
                        )}
                    />
                    {errors.color && (
                        <div className='invalid-feedback d-block'>Campo obrigatório</div>
                    )}
                </div>
                <div className='margin-bottom-10'>
                    <label htmlFor="">Fantasma</label> 
                    <Controller 
                        name = 'ghost'
                        rules = {{required: false}}
                        control = {control}
                        render = {( {field} ) => (
                            <Select 
                                {...field}
                                options={selectGhost}
                                classNamePrefix="margin-bottom-20"
                                className="margin-bottom-20"
                                placeholder="Fantasma"
                                getOptionLabel={(ghost: DGhost) => ghost.code.toString()}
                                getOptionValue={(ghost: DGhost) => ghost.code.toString()}
                            />    
                        )}
                    />
                    {errors.ghost && (
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

export default FatherModal;
