import { DColor, DMaterial, DSheet } from "models/entities";
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as DeleteSvg} from "assets/images/delete.svg";
import { useEffect, useState } from "react";
import { requestBackend } from "util/requests";
import Modal from 'react-modal';
import { Controller, useForm } from "react-hook-form";
import Select from 'react-select';
import { BiEdit } from "react-icons/bi";
import { toast } from "react-toastify";
import * as sheetService from 'services/MDP/sheetService';
import FlatPicker from 'react-flatpickr';

type Props = {
    sheet: DSheet;
    onDeleteOrEdit: Function;
}

const SheetRow = ({sheet, onDeleteOrEdit} : Props) => {

    // crud methods

    const [errorMessage, setErrorMessage] = useState(String);

    const { register, handleSubmit, formState: {errors}, setValue, control } = useForm<DSheet>();

    useEffect(() => {
        sheetService.findById(sheet.code)
            .then((response) => {
                const sheet = response.data as DSheet;
                
                setValue('code', sheet.code);
                setValue('description', sheet.description);
                setValue('family', sheet.family);
                setValue('implementation', sheet.implementation);
                setValue('lostPercentage', sheet.lostPercentage);
                setValue('color', sheet.color);
                setValue('thickness', sheet.thickness);
                setValue('faces', sheet.faces);
                setValue('materialId', sheet.materialId);
            })
    }, [sheet, setValue]);
    
    const update = (formData : DSheet) => {
        if (dateTime !== null) {
            formData.implementation = dateTime;
          }
        sheetService.update(formData)
            .then(response => {
                console.log(response.data);
                setErrorMessage('');
                toast.success("Chapa editada!");
                closeModal();
                onDeleteOrEdit();
            })
            .catch(error => {
                toast.error(error.response.data.error);
                setErrorMessage(error.response.data.error);
            });
    }

    const deleteObject = (objectId : number) => {
        if(!window.confirm("Tem certeza que deseja excluir esse item?")){
          return;
        }

        sheetService.deleteById(objectId)
            .then(() => {
                onDeleteOrEdit();
            });
    }

    // modal functions

    const [modalIsOpen, setIsOpen] = useState(false);

    function openModal(){
        setDateTime(sheet.implementation ? new Date(sheet.implementation) : null);
        setIsOpen(true);
    }

    function closeModal(){
        setIsOpen(false);
    }

    // get dependencies 

    // materialId
    const [material, setMaterial] = useState<DMaterial>();

    const getMaterial = (id: number) => {
        requestBackend({url: `/materials/${id}`, withCredentials: true})
            .then(response => {
                setMaterial(response.data)
        })
    }

    const [selectColors, setSelectColors] = useState<DColor[]>();

    useEffect(() => {
        requestBackend({url: '/colors', params: {name: '' }, withCredentials: true})
            .then(response => {
                setSelectColors(response.data.content)
        })
        getMaterial(sheet.materialId);
    }, [sheet]);

    const [selectMaterials, setSelectMaterials] = useState<DMaterial[]>();

    useEffect(() => {
        requestBackend({url: '/materials', params: {name: '' }, withCredentials: true})
            .then(response => {
                setSelectMaterials(response.data.content)
        })
    }, [sheet]);

    const materialsIds = selectMaterials?.map(material => material.id);
    const materialsNames = selectMaterials?.map(material => material.name);

    const [dateTime, setDateTime] = useState<Date | null>(null);

    return(
        <tr>
            <td>{sheet.code}</td>
            <td>{sheet.description}</td>
            <td>{sheet.family}</td>
            <td>{sheet.faces}</td>
            <td>{sheet.thickness}</td>
            {sheet.implementation ? <td>{sheet.implementation.toString()}</td> : <td>-</td>}
            <td>{sheet.lostPercentage}</td>
            {sheet.materialId ? <td>{material?.name}</td> : <td>-</td>}
            {sheet.color ? <td>{sheet.color.name}</td> : <td>-</td>}
            <td><EditSvg onClick={() => openModal()}/></td>
            <td><DeleteSvg onClick={() => deleteObject(sheet.code)}/></td>

            <Modal 
                isOpen={modalIsOpen}
                onRequestClose={closeModal}
                contentLabel="Example Modal"
                overlayClassName="modal-overlay"
                className="modal-content"
            >
                <form onSubmit={handleSubmit(update)} className="crud-modal-form">
                    <h1><BiEdit/> Editar</h1>
                    <div className="row crud-modal-inputs-container">
                        <div className="col-lg-6 crud-modal-half-container">
                            <div className='margin-bottom-10'>
                                <label htmlFor="">Descrição</label>
                                <input 
                                    {...register("description", {
                                    required: 'Campo obrigatório', minLength: 2
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
                              <label>Date</label>
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
                        <button onClick={closeModal} className="btn cancel-button crud-modal-button">Cancelar</button>
                        <button className="btn btn-primary text-white crud-modal-button">Salvar</button>
                    </div>
                </form>
            </Modal>
        </tr>
    );
}

export default SheetRow;