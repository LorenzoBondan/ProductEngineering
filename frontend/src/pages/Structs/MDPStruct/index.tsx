import React, { useCallback, useEffect, useState } from 'react';
import * as machineService from 'services/Guides/machineService';
import { Nav, Tab } from 'react-bootstrap';
import { GiMaterialsScience } from "react-icons/gi";
import { RiGhost2Line } from "react-icons/ri";
import { IoColorPaletteOutline } from "react-icons/io5";
import { TbSitemap } from "react-icons/tb";
import Select from 'react-select';
import FlatPicker from 'react-flatpickr';
import { DColor, DCornerBracket, DGlue, DMachine, DNonwovenFabric, DPlastic, DPolyethylene, SpringPage } from 'models/entities';
import * as colorService from 'services/public/colorService';
import * as glueService from 'services/MDP/glueService';
import * as cornerBracketService from 'services/Packaging/cornerBracketService';
import * as plasticService from 'services/Packaging/plasticService';
import * as nonwovenFabricService from 'services/Packaging/nonwovenFabricService';
import * as polyethyleneService from 'services/Packaging/polyethyleneService';
import * as configuratorService from 'services/Configurator/configuratorService';
import './styles.css';
import { toast } from 'react-toastify';

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

    const [errorMessage, setErrorMessage] = useState<string>('');

    const [selectColors, setSelectColors] = useState<SpringPage<DColor>>();
    const [selectGlues, setSelectGlues] = useState<SpringPage<DGlue>>();
    const [selectMachines, setSelectMachines] = useState<SpringPage<DMachine>>();
    const [selectedColors, setSelectedColors] = useState<DColor[]>([]);
    const [selectedGlue, setSelectedGlue] = useState<DGlue>();
    const [edgeLength, setEdgeLength] = useState<number>();
    const [edgeWidth, setEdgeWidth] = useState<number>();
    const [dateTime, setDateTime] = useState<string>('');

    const [ghostSuffix, setGhostSuffix] = useState<string>();
    const [selectCornerBrackets, setSelectCornerBrackets] = useState<SpringPage<DCornerBracket>>();
    const [selectedCornerBracket, setSelectedCornerBracket] = useState<DCornerBracket>();
    const [selectPlastics, setSelectPlastics] = useState<SpringPage<DPlastic>>();
    const [selectedPlastic, setSelectedPlastic] = useState<DPlastic>();
    const [selectNonwovenFabrics, setSelectNonwovenFabrics] = useState<SpringPage<DNonwovenFabric>>();
    const [selectedNonwovenFabric, setSelectedNonwovenFabric] = useState<DNonwovenFabric>();
    const [selectPolyethylenes, setSelectPolyethylenes] = useState<SpringPage<DPolyethylene>>();
    const [selectedPolyethylene, setSelectedPolyethylene] = useState<DPolyethylene>();
    const [upper, setUpper] = useState<boolean>(false);
    const [additional, setAdditional] = useState<number>();
    const [width, setWidth] = useState<number>();
    const [quantity, setQuantity] = useState<number>();
    const [oneFace, setOneFace] = useState<boolean>(false);

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

    const getGlues = useCallback(() => {
        glueService.findAll('')
            .then(response => {
                setSelectGlues(response.data);
            });
    }, [])

    const getCornerBrackets = useCallback(() => {
        cornerBracketService.findAll('')
            .then(response => {
                setSelectCornerBrackets(response.data);
            });
    }, [])

    const getPlastics = useCallback(() => {
        plasticService.findAll('')
            .then(response => {
                setSelectPlastics(response.data);
            });
    }, [])

    const getNonwovenFabrics = useCallback(() => {
        nonwovenFabricService.findAll('')
            .then(response => {
                setSelectNonwovenFabrics(response.data);
            });
    }, [])

    const getPolyethylenes = useCallback(() => {
        polyethyleneService.findAll('')
            .then(response => {
                setSelectPolyethylenes(response.data);
            });
    }, [])

    useEffect(() => {
        getColors();
        getMachines();
        getGlues();
        getCornerBrackets();
        getPlastics();
        getNonwovenFabrics();
        getPolyethylenes();
    }, [getColors, getMachines, getGlues, getCornerBrackets, getPlastics, getNonwovenFabrics, getPolyethylenes]);
    
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

        //console.log(bpConfigurator);
        //console.log(`https://localhost:8080/mdpConfigurator?glueCode=${selectedGlue?.code}&edgeLength=${edgeLength}&edgeWidth=${edgeWidth}&implementation=${dateTime}&ghostSuffix=${ghostSuffix}&cornerBracketCode=${selectedCornerBracket?.code}&plasticCode=${selectedPlastic?.code}&nonwovenFabricCode=${selectedNonwovenFabric?.code}&polyethyleneCode=${selectedPolyethylene?.code}&additional=${additional}&width=${width}&quantity=${quantity}&oneFace=${oneFace}&upper=${upper}`)

        configuratorService.mdpConfigurator(
            bpConfigurator,
            edgeLength ?? 0,
            edgeWidth ?? 0, 
            ghostSuffix ?? '',
            selectedGlue?.code ?? 0,
            selectedCornerBracket?.code ?? 0,
            selectedPlastic?.code ?? 0,
            selectedNonwovenFabric?.code ?? 0,
            selectedPolyethylene?.code ?? 0,
            upper,
            additional ?? 0,
            width ?? 0,
            quantity ?? 0,
            oneFace,
            dateTime
        ).then(response => {
            console.log(response.data);
            toast.success("Cadastros, estruturas e roteiros realizados!");
            clearFields();
        }).catch(error => {
            toast.error(error.response.data.error);
            setErrorMessage(error.response.data.error);
        });
    };

    const clearFields = () => {
        setBPConfigurator({
            items: [{ fatherCode: '', description: '', descriptionText: '', measure1: '', measure2: '', measure3: '', sonCode: '', machinesIds: [] }],
            colors: []
        });
        setSelectColors(undefined);
        setSelectGlues(undefined);
        setSelectedColors([]);
        setSelectedGlue(undefined);
        setEdgeLength(undefined);
        setEdgeWidth(undefined);
        setDateTime('');
        setGhostSuffix(undefined);
        setSelectCornerBrackets(undefined);
        setSelectedCornerBracket(undefined);
        setSelectPlastics(undefined);
        setSelectedPlastic(undefined);
        setSelectNonwovenFabrics(undefined);
        setSelectedNonwovenFabric(undefined);
        setSelectPolyethylenes(undefined);
        setSelectedPolyethylene(undefined);
        setUpper(false);
        setAdditional(undefined);
        setWidth(undefined);
        setQuantity(undefined);
        setOneFace(false);
        setErrorMessage('');
    };

    return (
        <div className='struct-container'>
            <form onSubmit={handleSubmit} className='struct-form'>
                <Tab.Container id="structs-tabs" defaultActiveKey="codes">
                    <Nav variant="pills" className="nav-pills mb-2 mt-2" id="pills-tab">
                        <Nav.Item>
                        <Nav.Link eventKey="codes"><TbSitemap/></Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                        <Nav.Link eventKey="colors"><IoColorPaletteOutline/></Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                        <Nav.Link eventKey="materials"><GiMaterialsScience/></Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                        <Nav.Link eventKey="ghost"><RiGhost2Line/></Nav.Link>
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
                                <button type="button" onClick={handleAddField} className='btn btn-primary text-white crud-modal-button'>Adicionar Item</button>
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
                            <div className='struct-implementation-container'>
                                <label htmlFor="">Implementação</label> 
                                <FlatPicker
                                    value={dateTime}
                                    onChange={(dates) => {
                                        if (dates && dates.length > 0) {
                                            const selectedDate = dates[0];
                                            const formattedDate = selectedDate.toISOString().split('T')[0];
                                            setDateTime(formattedDate);
                                        } else {
                                            setDateTime('');
                                        }
                                    }}
                                    options={{
                                        enableTime: false,
                                        dateFormat: 'Y-m-d',
                                    }}
                                    className="base-input time-input"
                                    name="implementation"
                                />
                            </div>
                        </Tab.Pane>
                        <Tab.Pane eventKey="materials" className='heigth-100'>
                            <div className='struct-colors-container'>
                                <label htmlFor="">Cola</label> 
                                <Select 
                                    options={selectGlues?.content}
                                    value={selectedGlue}
                                    onChange={(selectedOptions: any) => {
                                        setSelectedGlue(selectedOptions);
                                    }}
                                    classNamePrefix="margin-bottom-20"
                                    className="margin-bottom-20"
                                    placeholder="Cola"
                                    isClearable
                                    getOptionLabel={(glue: DGlue) => glue.description}
                                    getOptionValue={(glue: DGlue) => glue.code.toString()}
                                />
                                <label htmlFor="">Bordas no comprimento</label> 
                                <input
                                    type="number"
                                    onChange={(e) => setEdgeLength(Number(e.target.value))}
                                    placeholder="Bordas no comprimento"
                                    className='form-control base-input'
                                />
                                <label htmlFor="">Bordas na largura</label> 
                                <input
                                    type="number"
                                    onChange={(e) => setEdgeWidth(Number(e.target.value))}
                                    placeholder="Bordas na largura"
                                    className='form-control base-input'
                                />
                            </div>
                        </Tab.Pane>
                        <Tab.Pane eventKey="ghost" className='heigth-100'>
                            <div className='row struct-ghost-container'>
                                <div className="col-lg-6 crud-modal-half-container">
                                    <label htmlFor="">Sufixo do fantasma</label> 
                                    <input
                                        type="text"
                                        onChange={(e) => setGhostSuffix(e.target.value)}
                                        placeholder="Sufixo do fantasma"
                                        className='form-control base-input'
                                    />
                                    <label htmlFor="">Plástico</label> 
                                    <Select 
                                        options={selectPlastics?.content}
                                        value={selectedPlastic}
                                        onChange={(selectedOptions: any) => {
                                            setSelectedPlastic(selectedOptions);
                                        }}
                                        classNamePrefix="margin-bottom-20"
                                        className="margin-bottom-20"
                                        placeholder="Plástico"
                                        isClearable
                                        getOptionLabel={(plastic: DPlastic) => plastic.description}
                                        getOptionValue={(plastic: DPlastic) => plastic.code.toString()}
                                    />
                                    <label htmlFor="">Cantoneira</label> 
                                    <Select 
                                        options={selectCornerBrackets?.content}
                                        value={selectedCornerBracket}
                                        onChange={(selectedOptions: any) => {
                                            setSelectedCornerBracket(selectedOptions);
                                        }}
                                        classNamePrefix="margin-bottom-20"
                                        className="margin-bottom-20"
                                        placeholder="Cantoneira"
                                        isClearable
                                        getOptionLabel={(cornerBracket: DCornerBracket) => cornerBracket.description}
                                        getOptionValue={(cornerBracket: DCornerBracket) => cornerBracket.code.toString()}
                                    />
                                    <label htmlFor="">TNT</label> 
                                    <Select 
                                        options={selectNonwovenFabrics?.content}
                                        value={selectedNonwovenFabric}
                                        onChange={(selectedOptions: any) => {
                                            setSelectedNonwovenFabric(selectedOptions);
                                        }}
                                        classNamePrefix="margin-bottom-20"
                                        className="margin-bottom-20"
                                        placeholder="TNT"
                                        isClearable
                                        getOptionLabel={(nonwovenFabric: DNonwovenFabric) => nonwovenFabric.description}
                                        getOptionValue={(nonwovenFabric: DNonwovenFabric) => nonwovenFabric.code.toString()}
                                    />
                                    <label htmlFor="">Polietileno</label> 
                                    <Select 
                                        options={selectPolyethylenes?.content}
                                        value={selectedPolyethylene}
                                        onChange={(selectedOptions: any) => {
                                            setSelectedPolyethylene(selectedOptions);
                                        }}
                                        classNamePrefix="margin-bottom-20"
                                        className="margin-bottom-20"
                                        placeholder="Polietileno"
                                        isClearable
                                        getOptionLabel={(polyethylene: DPolyethylene) => polyethylene.description}
                                        getOptionValue={(polyethylene: DPolyethylene) => polyethylene.code.toString()}
                                    />
                                </div>
                                <div className="col-lg-6 crud-modal-half-container">
                                    <label htmlFor="">Plástico adicional</label> 
                                    <input
                                        type="number"
                                        onChange={(e) => setAdditional(Number(e.target.value))}
                                        placeholder="Plástico adicional"
                                        className='form-control base-input'
                                    />
                                    <label htmlFor="">Largura plástico</label> 
                                    <input
                                        type="number"
                                        onChange={(e) => setWidth(Number(e.target.value))}
                                        placeholder="Largura plástico"
                                        className='form-control base-input'
                                    />
                                    <label htmlFor="">Quantidade cantoneira</label> 
                                    <input
                                        type="number"
                                        onChange={(e) => setQuantity(Number(e.target.value))}
                                        placeholder="Quantidade cantoneira"
                                        className='form-control base-input'
                                    />
                                    <div className='struct-ghost-checks-container'>
                                        <label htmlFor="upper-checkbox" className="checkbox-label">
                                            <input
                                                type="checkbox"
                                                onChange={(e) => setUpper(e.target.checked)}
                                                className="checkbox-input"
                                                id="upper-checkbox"
                                            />
                                            <span className='checkbox-custom'></span>
                                            Acima
                                        </label>
                                        <label htmlFor="one-face-checkbox" className="checkbox-label">
                                            <input
                                                type="checkbox"
                                                onChange={(e) => setOneFace(e.target.checked)}
                                                className="checkbox-input"
                                                id="one-face-checkbox"
                                            />
                                            <span className='checkbox-custom'></span>
                                            Uma face
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div className='crud-modal-buttons-container'>
                                {errorMessage && <div className='invalid-feedback d-block'>{errorMessage}</div>}
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
