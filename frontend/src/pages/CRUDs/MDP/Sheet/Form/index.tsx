import { AxiosRequestConfig } from 'axios';
import { useEffect, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { useHistory, useParams } from 'react-router-dom';
import Select from 'react-select';
import { requestBackend } from 'util/requests';

import { toast } from 'react-toastify';
import { DColor, DSheet } from 'models/entities';

type UrlParams = {
    sheetId: string;
}

const Form = () => {

    const {sheetId} = useParams<UrlParams>();

    const isEditing = sheetId !== 'create';

    const { register, handleSubmit, formState: {errors}, setValue, control } = useForm<DSheet>();

    useEffect(() => {
        if(isEditing){
            requestBackend({url:`/sheets/${sheetId}`, withCredentials:true})
                .then((response) => {
                    const sheet = response.data as DSheet;

                    setValue('description', sheet.description);
                    setValue('family', sheet.family);
                    setValue('faces', sheet.faces);
                    setValue('thickness', sheet.thickness);
                    setValue('implementation', sheet.implementation);
                    setValue('lostPercentage', sheet.lostPercentage);
                    setValue('color', sheet.color);
                    setValue('materialId', sheet.materialId);
                })
        }
        
    }, [isEditing, sheetId, setValue]);

    const history = useHistory();

    const [selectColors, setSelectColors] = useState<DColor[]>();

    useEffect(() => {
        requestBackend({url: '/colors', params: {name: ""}, withCredentials: true})
            .then(response => {
                setSelectColors(response.data.content)
        })
    }, []);

    const onSubmit = (formData : DSheet) => {

        const params : AxiosRequestConfig = {
            method: isEditing? "PUT" : "POST",
            url : isEditing? `/sheets/${sheetId}` : "/sheets",
            data: formData,
            withCredentials: true
        };

        requestBackend(params)
            .then(response => {
                console.log('Sucesso', response.data);
                history.push("/home/mdp");
            })
            .catch(() => {
                toast.error('Error.');
            })
    };

    const handleCancel = () => {
        history.push("/home/mdp")
    }

    return(
        <div className="edit-profile-form-container">
            <div className="base-card post-card-form-card">
                {isEditing ? <h1>Editar Chapa</h1> : <h1>Adicionar Chapa</h1>}
                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className='row post-crud-inputs-container'>
                        <div className='post-crud-inputs-left-container'>
                            <div className='margin-bottom-30'>
                                <label htmlFor="">Descrição</label>
                                <input 
                                    {...register("description", {
                                    required: 'Campo obrigatório',
                                    })}
                                    type="text"
                                    className={`form-control base-input ${errors.description ? 'is-invalid' : ''}`}
                                    placeholder="Descrição"
                                    name="description"
                                />
                                <div className='invalid-feedback d-block'>{errors.description?.message}</div>
                            </div>
                            <div className='margin-bottom-30'>
                                <label htmlFor="">Família</label>
                                <input 
                                    {...register("family", {
                                    required: 'Campo obrigatório',
                                    })}
                                    type="text"
                                    className={`form-control base-input ${errors.family ? 'is-invalid' : ''}`}
                                    placeholder="Família"
                                    name="family"
                                />
                                <div className='invalid-feedback d-block'>{errors.family?.message}</div>
                            </div>
                        
                            
                        </div>
                        <div className='margin-bottom-30'>
                            <label htmlFor="" style={{color:"white"}}>Cor</label> 
                                <Controller 
                                    name = 'color'
                                    rules = {{required: true}}
                                    control = {control}
                                    render = {( {field} ) => (
                                        <Select 
                                            {...field}
                                            options={selectColors}
                                            classNamePrefix="Sheets-crud-select"
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
                        <div className='post-crud-buttons-container'>
                            <button 
                                className='btn btn-outline-danger post-crud-buttons'
                                onClick={handleCancel}
                                >
                                CANCELAR
                            </button>
                            <button className='btn btn-primary text-white post-crud-buttons'>SALVAR</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Form;