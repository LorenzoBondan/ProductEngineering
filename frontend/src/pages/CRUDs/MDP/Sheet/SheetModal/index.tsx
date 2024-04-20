import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { Controller, useForm } from "react-hook-form";
import FlatPicker from 'react-flatpickr';
import Select from 'react-select';
import { toast } from 'react-toastify';
import * as sheetService from 'services/MDP/sheetService';
import { DColor, DMaterial, DSheet } from 'models/entities';
import { requestBackend } from 'util/requests';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";

type SheetModalProps = {
    sheet?: DSheet;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
}

const SheetModal: React.FC<SheetModalProps> = ({ sheet, isOpen, isEditing, onClose, onDeleteOrEdit }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue, control } = useForm<DSheet>();
  const [selectColors, setSelectColors] = useState<DColor[]>();
  const [selectMaterials, setSelectMaterials] = useState<DMaterial[]>();
  const materialsIds = selectMaterials?.map(material => material.id);
  const materialsNames = selectMaterials?.map(material => material.name);
  const [dateTime, setDateTime] = useState<Date | null>(null);

  useEffect(() => {
    if(isEditing && sheet){
        sheetService.findById(sheet.code)
        .then((response) => {
            const fetchedSheet = response.data as DSheet;

            setValue('code', fetchedSheet.code);
            setValue('description', fetchedSheet.description);
            setValue('family', fetchedSheet.family);
            setValue('implementation', fetchedSheet.implementation);
            setValue('lostPercentage', fetchedSheet.lostPercentage);
            setValue('color', fetchedSheet.color);
            setValue('thickness', fetchedSheet.thickness);
            setValue('faces', fetchedSheet.faces);
            setValue('materialId', fetchedSheet.materialId);

            setDateTime(fetchedSheet.implementation ? new Date(fetchedSheet.implementation) : null);
        });
    }
  }, [isEditing, sheet, setValue]);

  useEffect(() => {
    requestBackend({ url: '/colors', params: { name: '' }, withCredentials: true })
      .then(response => setSelectColors(response.data.content));

    requestBackend({ url: '/materials', params: { name: '' }, withCredentials: true })
      .then(response => {
        const materials = response.data.content;
        setSelectMaterials(materials);
      });
  }, []);

  const insertOrUpdate = (formData: DSheet) => {
    if (dateTime !== null) {
      formData.implementation = dateTime;
    }
  
    const serviceFunction = isEditing ? sheetService.update : sheetService.insert;
    const successMessage = isEditing ? 'Chapa editada!' : 'Chapa Inserida!';
  
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
                    <label htmlFor="">Família</label>
                    <input 
                        {...register("family", {
                        })}
                        type="text"
                        className={`form-control base-input ${errors.family ? 'is-invalid' : ''}`}
                        placeholder="Família"
                        name="family"
                    />
                    {errors.family && <div className='invalid-feedback d-block'>{errors.family.message}</div>}
                </div>
                <div className='margin-bottom-10'>
                    <label htmlFor="">Faces</label>
                    <input 
                        {...register("faces", {
                        })}
                        type="number"
                        className={`form-control base-input ${errors.faces ? 'is-invalid' : ''}`}
                        placeholder="Faces"
                        name="faces"
                    />
                    {errors.faces && <div className='invalid-feedback d-block'>{errors.faces.message}</div>}
                </div>
                <div className='margin-bottom-10'>
                    <label htmlFor="">Espessura</label>
                    <input 
                        {...register("thickness", {
                        })}
                        type="number"
                        className={`form-control base-input ${errors.thickness ? 'is-invalid' : ''}`}
                        placeholder="Espessura"
                        name="thickness"
                    />
                    {errors.thickness && <div className='invalid-feedback d-block'>{errors.thickness.message}</div>}
                </div>
            </div>
            <div className="col-lg-6 crud-modal-half-container">
                <div className='margin-bottom-10'>
                    <label htmlFor="">% Perda</label>
                    <input 
                        {...register("lostPercentage", {
                        })}
                        type="number"
                        className={`form-control base-input ${errors.lostPercentage ? 'is-invalid' : ''}`}
                        placeholder="% Perda"
                        name="lostPercentage"
                    />
                    {errors.lostPercentage && <div className='invalid-feedback d-block'>{errors.lostPercentage.message}</div>}
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
                    <label htmlFor="">Material</label> 
                    <select
                        {...register("materialId", {
                            required: 'Campo obrigatório',
                        })}
                        className={`form-control base-input ${errors.materialId ? 'is-invalid' : ''}`}
                        placeholder='Material' 
                        name="materialId"
                    >
                        {materialsIds?.map(id => <option key={id} value={id} label={materialsNames && materialsNames[id-1]}></option>)}
                    </select>
                    {errors.materialId && (
                        <div className='invalid-feedback d-block'>Campo obrigatório</div>
                    )}
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

export default SheetModal;
