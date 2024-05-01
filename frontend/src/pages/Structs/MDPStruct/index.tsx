import React, { useCallback, useEffect, useState } from 'react';
import * as machineService from 'services/Guides/machineService';
import { BiCommentDetail } from 'react-icons/bi';
import { Nav, Tab } from 'react-bootstrap';
import { AiOutlineTool } from 'react-icons/ai';
import { FaRegChartBar } from 'react-icons/fa';
import Select from 'react-select';
import { DColor, DMachine, SpringPage } from 'models/entities';
import * as colorService from 'services/public/colorService';
import './styles.css';

interface AdditionalField {
    fatherCode: string;
    description: string;
    descriptionText: string;
    measure1: string;
    measure2: string;
    measure3: string;
    sonCode: string;
    machinesIds: number[];
}

interface BPConfigurator {
    items: AdditionalField[];
    colors: DColor[];
}

const MDPStruct = () => {

    const [selectColors, setSelectColors] = useState<SpringPage<DColor>>();
    const [selectMachines, setSelectMachines] = useState<SpringPage<DMachine>>();
    const [selectedColors, setSelectedColors] = useState<DColor[]>([]);

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
    
    const [bpConfigurator, setBPConfigurator] = useState<BPConfigurator>({
        items: [{ fatherCode: '', description: '', descriptionText: '', measure1: '', measure2: '', measure3: '', sonCode: '', machinesIds: [] }],
        colors: [] 
    });

    const handleAddField = () => {
        setBPConfigurator(prevConfig => ({
            ...prevConfig,
            items: [...prevConfig.items, { fatherCode: '', description: '', descriptionText: '', measure1: '', measure2: '', measure3: '', sonCode: '', machinesIds: [] }]
        }));
    };

    const handleRemoveField = (indexToRemove: number) => {
        setBPConfigurator(prevConfig => ({
            ...prevConfig,
            items: prevConfig.items.filter((_, index) => index !== indexToRemove)
        }));
    };

    const handleChangeField = (index: number, key: keyof AdditionalField, newValue: string | number) => {
        setBPConfigurator(prevConfig => {
            const updatedItems = [...prevConfig.items];
            updatedItems[index][key] = newValue as never;
            return { ...prevConfig, items: updatedItems };
        });
    };

    const concatenateDescription = (item: AdditionalField): string => {
        return `${item.descriptionText} ${item.measure1}X${item.measure2}X${item.measure3}`;
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        bpConfigurator.items.map(item => item.description = concatenateDescription(item));
        // Envie os dados, incluindo os campos adicionais, para onde você precisar
        console.log(bpConfigurator);
    };

    return (
        <div className='struct-container'>
            <form onSubmit={handleSubmit} className='struct-form'>
                <Tab.Container id="structs-tabs" defaultActiveKey="codes">
                    <Nav variant="pills" className="nav-pills mb-2 mt-2" id="pills-tab">
                        <Nav.Item>
                        <Nav.Link eventKey="codes"><BiCommentDetail/></Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                        <Nav.Link eventKey="colors"><AiOutlineTool/></Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                        <Nav.Link eventKey="materials"><FaRegChartBar/></Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                        <Nav.Link eventKey="ghost"><FaRegChartBar/></Nav.Link>
                        </Nav.Item>
                    </Nav>
                    <Tab.Content id="slideInUp" className='heigth-100'>
                        <Tab.Pane eventKey="codes" className='heigth-100'>
                            <div>
                                {bpConfigurator.items.map((item, index) => (
                                    <div key={index} className='struct-item-container'>
                                        <input
                                            type="number"
                                            value={item.fatherCode}
                                            onChange={(e) => handleChangeField(index, 'fatherCode', e.target.value)}
                                            placeholder="Código do Pai"
                                            className='form-control base-input'
                                        />
                                        <input
                                            type="text"
                                            value={item.descriptionText}
                                            onChange={(e) => handleChangeField(index, 'descriptionText', e.target.value)}
                                            placeholder="Descrição"
                                            className='form-control base-input'
                                        />
                                        <div className='struct-measures-container'>
                                            <input
                                                type="number"
                                                value={item.measure1}
                                                onChange={(e) => handleChangeField(index, 'measure1', e.target.value)}
                                                placeholder="Altura"
                                                className='form-control base-input'
                                            />
                                            <p>X</p>
                                            <input
                                                type="number"
                                                value={item.measure2}
                                                onChange={(e) => handleChangeField(index, 'measure2', e.target.value)}
                                                placeholder="Largura"
                                                className='form-control base-input'
                                            />
                                            <p>X</p>
                                            <input
                                                type="number"
                                                value={item.measure3}
                                                onChange={(e) => handleChangeField(index, 'measure3', e.target.value)}
                                                placeholder="Espessura"
                                                className='form-control base-input'
                                            />
                                        </div>
                                        <input
                                            type="number"
                                            value={item.sonCode}
                                            onChange={(e) => handleChangeField(index, 'sonCode', e.target.value)}
                                            placeholder="Código do Filho"
                                            className='form-control base-input'
                                        />
                                        <button type="button" onClick={() => handleRemoveField(index)} className='btn remove-button'>Remover</button>
                                    </div>
                                ))}
                            </div>
                            <div className='crud-modal-buttons-container struct-button-container'>
                                <button type="button" onClick={handleAddField} className='btn btn-primary text-white crud-modal-button'>Adicionar Campo</button>
                            </div>
                        </Tab.Pane> 
                        <Tab.Pane eventKey="colors" className='heigth-100'>
                            <div className='struct-machines-container'>
                                <label htmlFor="">Máquinas</label> 
                                <div className='row'>
                                    {selectMachines?.content.map((machine, index) => (
                                        <div key={index} className='margin-bottom-10 col-sm-6 col-lg-4 col-xl-3 col-xxl-2'>
                                            <label htmlFor={`machines-${index}`} className="checkbox-label">
                                                <input
                                                    type="checkbox"
                                                    id={`machines-${index}`}
                                                    checked={bpConfigurator.items.every(item => item.machinesIds.includes(machine.id))}
                                                    onChange={(e) => {
                                                        const isChecked = e.target.checked;
                                                        setBPConfigurator(prevConfig => {
                                                            const updatedItems = prevConfig.items.map(item => {
                                                                if (isChecked && !item.machinesIds.includes(machine.id)) {
                                                                    return { ...item, machinesIds: [...item.machinesIds, machine.id] };
                                                                } else if (!isChecked && item.machinesIds.includes(machine.id)) {
                                                                    return { ...item, machinesIds: item.machinesIds.filter(id => id !== machine.id) };
                                                                }
                                                                return item;
                                                            });
                                                            return { ...prevConfig, items: updatedItems };
                                                        });
                                                    }}
                                                    className="checkbox-input"
                                                />
                                                <span className="checkbox-custom"></span>
                                                {machine.name}
                                            </label>
                                        </div>
                                    ))}
                                </div>
                            </div>
                            <div className='struct-colors-container'>
                                <label htmlFor="">Cores</label> 
                                <Select 
                                    options={selectColors?.content}
                                    value={selectedColors}
                                    onChange={(selectedOptions: any) => {
                                        setSelectedColors(selectedOptions);
                                        const selectedColorObjects = selectedOptions.map((option: any) => ({
                                            code: option.code,
                                            name: option.name
                                        }));
                                        setBPConfigurator(prevConfig => ({
                                            ...prevConfig,
                                            colors: selectedColorObjects
                                        }));
                                    }}
                                    classNamePrefix="margin-bottom-20"
                                    className="margin-bottom-20"
                                    placeholder="Cores"
                                    isMulti
                                    getOptionLabel={(color: DColor) => color.name}
                                    getOptionValue={(color: DColor) => color.code.toString()}
                                />
                            </div>
                        </Tab.Pane>
                        <Tab.Pane eventKey="materials" className='heigth-100'>
                            <div className='struct-materials-row'>
                                <h1>3</h1>
                            </div>
                        </Tab.Pane>
                        
                        <Tab.Pane eventKey="ghost" className='heigth-100'>
                            <div className='struct-ghost-row'>
                                <h1>4</h1>
                            </div>
                            <div className='crud-modal-buttons-container'>
                                <button type="submit" className='btn btn-primary text-white crud-modal-button'>Salvar</button>
                            </div>
                        </Tab.Pane>
                    </Tab.Content>
                </Tab.Container>
                
            </form>
        </div>
    );
}

export default MDPStruct;
