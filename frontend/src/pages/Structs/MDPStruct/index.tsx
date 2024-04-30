import { BiCommentDetail } from 'react-icons/bi';
import './styles.css';
import { Nav, Tab } from 'react-bootstrap';
import { AiOutlineTool } from 'react-icons/ai';
import { FaRegChartBar } from 'react-icons/fa';
import { useCallback, useEffect, useState } from 'react';
import Select from 'react-select';
import { Controller, useForm } from "react-hook-form";
import { BPConfigurator, DColor, DMachine, SpringPage } from 'models/entities';
import * as colorService from 'services/public/colorService';
import * as machineService from 'services/Guides/machineService';

const MDPStruct = () => {

    //const [BPConfigurator, setBPConfigurator] = useState<BPConfigurator>();
    const { register, handleSubmit, formState: { errors }, control } = useForm<BPConfigurator>();
    //const [colorsConfigurator, setColorsConfigurator] = useState<DColor[]>([]);
    
    const [selectColors, setSelectColors] = useState<SpringPage<DColor>>();
    const [selectMachines, setSelectMachines] = useState<SpringPage<DMachine>>();

    const getColors = useCallback(() => {
        colorService.findAll('')
            .then(response => {
                setSelectColors(response.data);
            });
    }, [])

    const getMachines = useCallback(() => {
        machineService.findAll('')
            .then(response => {
                setSelectMachines(response.data);
            });
    }, [])

    useEffect(() => {
        getColors();
        getMachines();
    }, [getColors, getMachines]);

    const sendStruct = (formData: BPConfigurator) => {

        
        console.log(formData);
    };

    return (
        <div className='struct-container'>
            <form onSubmit={handleSubmit(sendStruct)} className='struct-form'>
                <Tab.Container id="structs-tabs" defaultActiveKey="codes">
                    <Nav variant="pills" className="nav-pills mb-2 mt-2" id="pills-tab">
                        <Nav.Item>
                        <Nav.Link eventKey="codes"><BiCommentDetail/></Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                        <Nav.Link eventKey="materials"><AiOutlineTool/></Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                        <Nav.Link eventKey="colors"><FaRegChartBar/></Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                        <Nav.Link eventKey="ghost"><FaRegChartBar/></Nav.Link>
                        </Nav.Item>
                    </Nav>
                    <Tab.Content id="slideInUp" className='heigth-100'>
                        <Tab.Pane eventKey="codes" className='heigth-100'>
                            <div className='margin-bottom-10'>
                                <label htmlFor="">Código do Pai</label>
                                <input 
                                    {...register(`items.${0}.fatherCode`, {
                                        required: 'Campo obrigatório',
                                        minLength: { value: 6, message: 'O código deve ter pelo menos 6 caracteres' }
                                    })}
                                    type="number"
                                    inputMode="numeric"
                                    className={`form-control base-input ${errors?.items?.[0]?.fatherCode ? 'is-invalid' : ''}`}
                                    placeholder="Código do Pai"
                                />
                                {errors?.items?.[0]?.fatherCode && (
                                    <div className='invalid-feedback d-block'>{errors.items[0].fatherCode.message}</div>
                                )}
                            </div> 
                            <div className='margin-bottom-10'>
                                <label htmlFor="">Descrição</label>
                                <input 
                                    {...register(`items.${0}.description`, {
                                        required: 'Campo obrigatório',
                                        minLength: { value: 3, message: 'A descrição deve ter pelo menos 3 caracteres' }
                                    })}
                                    type="text"
                                    className={`form-control base-input ${errors?.items?.[0]?.description ? 'is-invalid' : ''}`}
                                    placeholder="Descrição"
                                />
                                {errors?.items?.[0]?.description && (
                                    <div className='invalid-feedback d-block'>{errors.items[0].description.message}</div>
                                )}
                            </div>
                            <div className='margin-bottom-10'>
                                <label htmlFor="">Código do Filho</label>
                                <input 
                                    {...register(`items.${0}.sonCode`, {
                                        required: 'Campo obrigatório',
                                        minLength: { value: 6, message: 'O código deve ter pelo menos 6 caracteres' }
                                    })}
                                    type="number"
                                    inputMode="numeric"
                                    className={`form-control base-input ${errors?.items?.[0]?.sonCode ? 'is-invalid' : ''}`}
                                    placeholder="Código do Filho"
                                />
                                {errors?.items?.[0]?.sonCode && (
                                    <div className='invalid-feedback d-block'>{errors.items[0].sonCode.message}</div>
                                )}
                            </div>
                            <div className='margin-bottom-10'>
                                <label htmlFor="">Máquinas</label> 
                                {selectMachines?.content.map((machine, index) => (
                                    <div key={machine.id} className='margin-bottom-10'>
                                        <label htmlFor={`machines-${index}`} className="checkbox-label">
                                            <input
                                                type="checkbox"
                                                id={`machines-${index}`}
                                                {...register(`items.${0}.machinesIds.${index}`)}
                                                value={machine.id}
                                                className="checkbox-input"
                                            />
                                            <span className="checkbox-custom"></span>
                                            {machine.name}
                                        </label>
                                    </div>
                                ))}
                                </div> 
                        </Tab.Pane> 
                        <Tab.Pane eventKey="materials" className='heigth-100'>
                            <div className='struct-materials-row'>
                                <h1>2
                                </h1>
                            </div>
                        </Tab.Pane>
                        <Tab.Pane eventKey="colors" className='heigth-100'>
                            <div className='struct-colors-row'>
                                <div className='margin-bottom-10'>
                                    <label htmlFor="">Cores</label> 
                                    <Controller  
                                        name = 'colors'
                                        rules = {{required: true}}
                                        control = {control}
                                        render = {( {field} ) => (
                                            <Select 
                                                {...field}
                                                options={selectColors?.content}
                                                classNamePrefix="margin-bottom-20"
                                                className="margin-bottom-20"
                                                placeholder="Cores"
                                                isMulti
                                                getOptionLabel={(color: DColor) => color.name}
                                                getOptionValue={(color: DColor) => color.code.toString()}
                                            />
                                        )}
                                    />
                                    {errors.colors && (
                                        <div className='invalid-feedback d-block'>Campo obrigatório</div>
                                    )}
                                </div> 
                            </div>
                        </Tab.Pane>
                        <Tab.Pane eventKey="ghost" className='heigth-100'>
                            <div className='struct-ghost-row'>
                                <h1>4</h1>
                            </div>
                        </Tab.Pane>
                    </Tab.Content>
                </Tab.Container>
                <button type="submit" className="btn btn-primary text-white crud-modal-button">Salvar</button>
            </form>
        </div>
    );
}

export default MDPStruct;