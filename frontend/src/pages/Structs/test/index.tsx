import React, { useCallback, useEffect, useState } from 'react';
import * as machineService from 'services/Guides/machineService';
import { BiCommentDetail } from 'react-icons/bi';
import { Nav, Tab } from 'react-bootstrap';
import { AiOutlineTool } from 'react-icons/ai';
import { FaRegChartBar } from 'react-icons/fa';
import Select from 'react-select';
import { DColor, DMachine, SpringPage } from 'models/entities';
import * as colorService from 'services/public/colorService';

interface AdditionalField {
    fatherCode: string;
    description: string;
    sonCode: string;
    machines: number[];
}

interface BPConfigurator {
    items: AdditionalField[];
    colors: DColor[];
}

const TestStruct = () => {

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
        items: [{ fatherCode: '', description: '', sonCode: '', machines: [] }],
        colors: [] 
    });

    const handleAddField = () => {
        setBPConfigurator(prevConfig => ({
            ...prevConfig,
            items: [...prevConfig.items, { fatherCode: '', description: '', sonCode: '', machines: [] }]
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

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
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
                                            value={item.description}
                                            onChange={(e) => handleChangeField(index, 'description', e.target.value)}
                                            placeholder="Descrição"
                                            className='form-control base-input'
                                        />
                                        <input
                                            type="number"
                                            value={item.sonCode}
                                            onChange={(e) => handleChangeField(index, 'sonCode', e.target.value)}
                                            placeholder="Código do Filho"
                                            className='form-control base-input'
                                        />
                                        <button type="button" onClick={() => handleRemoveField(index)} className='btn remove-button width-100'>Remover</button>
                                    </div>
                                ))}
                            </div>
                            <div className='crud-modal-buttons-container'>
                                <button type="button" onClick={handleAddField} className='btn btn-primary text-white crud-modal-button'>Adicionar Campo</button>
                            </div>
                            <div className='struct-machines-container'>
                                <label htmlFor="">Máquinas</label> 
                                {selectMachines?.content.map((machine, index) => (
                                    <div key={index} className='margin-bottom-10'>
                                        <label htmlFor={`machines-${index}`} className="checkbox-label">
                                            <input
                                                type="checkbox"
                                                id={`machines-${index}`}
                                                checked={bpConfigurator.items.every(item => item.machines.includes(machine.id))}
                                                onChange={(e) => {
                                                    const isChecked = e.target.checked;
                                                    setBPConfigurator(prevConfig => {
                                                        const updatedItems = prevConfig.items.map(item => {
                                                            if (isChecked && !item.machines.includes(machine.id)) {
                                                                return { ...item, machines: [...item.machines, machine.id] };
                                                            } else if (!isChecked && item.machines.includes(machine.id)) {
                                                                return { ...item, machines: item.machines.filter(id => id !== machine.id) };
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
                            </div>
                        </Tab.Pane>
                        <Tab.Pane eventKey="ghost" className='heigth-100'>
                            <div className='struct-ghost-row'>
                                <h1>4</h1>
                            </div>
                        </Tab.Pane>
                    </Tab.Content>
                </Tab.Container>
                <div className='crud-modal-buttons-container'>
                    <button type="submit" className='btn btn-primary text-white crud-modal-button'>Salvar</button>
                </div>
            </form>
        </div>
    );
}

export default TestStruct;
