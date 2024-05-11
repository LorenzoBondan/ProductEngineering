import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { Controller, useForm } from "react-hook-form";
import FlatPicker from 'react-flatpickr';
import { toast } from 'react-toastify';
import * as PaintingSonService from 'services/MDF/paintingSonService';
import * as colorService from 'services/public/colorService';
import * as backService from 'services/MDF/backService';
import { DColor, DPaintingSon, DBack } from 'models/entities';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";
import Select from 'react-select';

type PaintingSonModalProps = {
    PaintingSon?: DPaintingSon;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
}

const PaintingSonModal: React.FC<PaintingSonModalProps> = ({ PaintingSon, isOpen, isEditing, onClose, onDeleteOrEdit }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue, control } = useForm<DPaintingSon>();
  const [selectColors, setSelectColors] = useState<DColor[]>();
  const [selectBacks, setSelectBacks] = useState<DBack[]>();
  const [dateTime, setDateTime] = useState<Date | null>(null);
  const [special, setSpecial] = useState<boolean>(false);
  const [satin, setSatin] = useState<boolean>(false);
  const [highShine, setHighShine] = useState<boolean>(false);
  const [satinGlass, setSatinGlass] = useState<boolean>(false);

  // load inputs

  useEffect(() => {
    if(isEditing && PaintingSon){
        PaintingSonService.findById(PaintingSon.code)
        .then((response) => {
            const fetchedPaintingSon = response.data as DPaintingSon;

            setValue('code', fetchedPaintingSon.code);
            setValue('description', fetchedPaintingSon.description);
            setValue('measure1', fetchedPaintingSon.measure1);
            setValue('measure2', fetchedPaintingSon.measure2);
            setValue('measure3', fetchedPaintingSon.measure3);
            setValue('measurementUnit', fetchedPaintingSon.measurementUnit);
            setValue('value', fetchedPaintingSon.value);
            setValue('color', fetchedPaintingSon.color);
            setValue('fatherCode', fetchedPaintingSon.fatherCode);
            setValue('satin', fetchedPaintingSon.satin);
            setValue('highShine', fetchedPaintingSon.highShine);
            setValue('satinGlass', fetchedPaintingSon.satinGlass);
            setValue('faces', fetchedPaintingSon.faces);
            setValue('special', fetchedPaintingSon.special);
            setValue('suffix', fetchedPaintingSon.suffix);
            setValue('back', fetchedPaintingSon.back);
            setValue('value', fetchedPaintingSon.value);

            setDateTime(fetchedPaintingSon.implementation ? new Date(fetchedPaintingSon.implementation) : null);
        });
    }
  }, [isEditing, PaintingSon, setValue]);

    // populate comboboxes

    useEffect(() => {

        if(PaintingSon?.color){
            colorService.findAllActiveAndCurrentOne(PaintingSon?.color.code)
                .then(response => setSelectColors(response.data));
        } else{
            colorService.findAll('')
                .then(response => setSelectColors(response.data.content));
        }

        if(PaintingSon?.back){
            backService.findAllActiveAndCurrentOne(PaintingSon.back.code)
                .then(response => setSelectBacks(response.data));
        } else {
            backService.findAll('')
                .then(response => setSelectBacks(response.data.content));
        }
    
    }, [PaintingSon?.color, PaintingSon?.fatherCode, PaintingSon?.back]);

    useEffect(() => {
        // Definir os estados das checkboxes com base no valor de PaintingSon
        if (PaintingSon) {
            setSpecial(PaintingSon.special || false);
            setSatin(PaintingSon.satin || false);
            setHighShine(PaintingSon.highShine || false);
            setSatinGlass(PaintingSon.satinGlass || false);
        }
    }, [PaintingSon]);

  // insert / update method

  const insertOrUpdate = (formData: DPaintingSon) => {
    if (dateTime !== null) {
      formData.implementation = dateTime;
    }
    formData.special = special;
    formData.satin = satin;
    formData.highShine = highShine;
    formData.satinGlass = satinGlass;
  
    const serviceFunction = isEditing ? PaintingSonService.update : PaintingSonService.insert;
    const successMessage = isEditing ? 'Filho editado!' : 'Filho Inserido!';
  
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
                <div className='margin-bottom-10'>
                    <label htmlFor="">Fundo</label> 
                    <Controller 
                        name = 'back'
                        rules = {{required: false}}
                        control = {control}
                        render = {( {field} ) => (
                            <Select 
                                {...field}
                                options={selectBacks}
                                classNamePrefix="margin-bottom-20"
                                className="margin-bottom-20"
                                placeholder="Fundo"
                                isClearable
                                getOptionLabel={(back: DBack) => back.description}
                                getOptionValue={(back: DBack) => back.code.toString()}
                            />    
                        )}
                    />
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
                    <label htmlFor="">Código do Pai</label>
                    <input 
                        {...register("fatherCode", {
                        required: 'Campo obrigatório', minLength: 9
                        })}
                        type="number"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.fatherCode ? 'is-invalid' : ''}`}
                        placeholder="Código do Pai"
                        name="fatherCode"
                    />
                    <div className='invalid-feedback d-block'>{errors.fatherCode?.message}</div>
                </div> 
                <div className='margin-bottom-10'>
                    <label htmlFor="">Faces</label>
                    <input 
                        {...register("faces", {
                        })}
                        type="number"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.faces ? 'is-invalid' : ''}`}
                        placeholder="Faces"
                        name="faces"
                    />
                    {errors.faces && <div className='invalid-feedback d-block'>{errors.faces.message}</div>}
                </div>
                <div className='struct-ghost-checks-container'>
                    <label htmlFor="special-checkbox" className="checkbox-label">
                        <input
                            type="checkbox"
                            
                            checked={special}
                            onChange={(e) => setSpecial(e.target.checked)}
                            className="checkbox-input"
                            id="special-checkbox"
                        />
                        <span className='checkbox-custom'></span>
                            Especial
                    </label>  
                </div>
                <div className='margin-bottom-10 margin-top-20'>
                    <label htmlFor="">Tipo de Pintura</label>
                    <div className='struct-ghost-checks-container space-between'>
                        <label htmlFor="satin">
                            <input
                                type="radio"
                                id="satin"
                                value="satin"
                                checked={satin}
                                name="satin"
                                onChange={() => {
                                    setHighShine(false);
                                    setSatin(true);
                                    setSatinGlass(false);
                                }}
                            />
                            Acetinada
                        </label>
                        <label htmlFor="high-shine">
                            <input
                                type="radio"
                                id="high-shine"
                                value="high-shine"
                                checked={highShine}
                                name="highShine"
                                onChange={() => {
                                    setHighShine(true);
                                    setSatin(false);
                                    setSatinGlass(false);
                                }}
                            />
                            Alto Brilho
                        </label>
                        <label htmlFor="satin-glass">
                            <input
                                type="radio"
                                id="satin-glass"
                                value="satin-glass"
                                checked={satinGlass}
                                name="satinGlass"
                                onChange={() => {
                                    setHighShine(false);
                                    setSatin(false);
                                    setSatinGlass(true);
                                }}
                            />
                            Acetinada Vidros
                        </label>
                    </div>
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

export default PaintingSonModal;
