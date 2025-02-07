import * as paiService from '../../../../services/paiService';
import * as modeloService from '../../../../services/modeloService';
import * as categoriaComponenteService from '../../../../services/categoriaComponenteService';
import * as corService from '../../../../services/corService';
import * as medidasService from '../../../../services/medidasService';
import * as materialService from '../../../../services/materialService';
import * as maquinaService from '../../../../services/maquinaService';
import * as acessorioService from '../../../../services/acessorioService';
import * as forms from '../../../../utils/forms';
import { useEffect, useState } from 'react';
import { DModelo } from '../../../../models/modelo';
import { DCor } from '../../../../models/cor';
import { DMedidas } from '../../../../models/medidas';
import { DMaterial } from '../../../../models/material';
import { DMaquina } from '../../../../models/maquina';
import { DCategoriaComponente } from '../../../../models/categoriaComponente';
import { useNavigate } from 'react-router-dom';
import FormLabel from '../../../../components/FormLabel';
import FormInput from '../../../../components/FormInput';
import FormSelect from '../../../../components/FormSelect';
import FormCheckbox from '../../../../components/FormCheckBox';
import 'flatpickr/dist/themes/material_red.css';
import Flatpickr from "react-flatpickr";
import { Link } from 'react-router-dom';
import { DAcessorio } from '../../../../models/acessorio';

export default function MultiStruct() {

    const navigate = useNavigate();

    const [selectCores, setSelectCores] = useState<DCor[]>([]);
    const [selectMedidas, setSelectMedidas] = useState<DMedidas[]>([]);
    const [selectMateriais, setSelectMateriais] = useState<DMaterial[]>([]);
    const [selectMaquinas, setSelectMaquinas] = useState<DMaquina[]>([]);
    const [selectModelos, setSelectModelos] = useState<DModelo[]>([]);
    const [selectCategoriaComponentes, setSelectCategoriaComponentes] = useState<DCategoriaComponente[]>([]);
    const [selectAcessorios, setSelectAcessorios] = useState<DAcessorio[]>([]);
    const [dateTimeStart, setDateTimeStart] = useState('');

    useEffect(() => {
        corService.pesquisarTodos("situacao", "=", "ATIVO")
            .then(response => {
                setSelectCores(response.data.content);
            });
        medidasService.pesquisarTodos("situacao", "=", "ATIVO")
            .then(response => {
                setSelectMedidas(response.data.content);
            });
        materialService.pesquisarTodos("situacao", "=", "ATIVO")
            .then(response => {
                setSelectMateriais(response.data.content);
            }); 
        maquinaService.pesquisarTodos("situacao", "=", "ATIVO")
            .then(response => {
                setSelectMaquinas(response.data.content);
            });
        modeloService.pesquisarTodos("situacao", "=", "ATIVO")
            .then(response => {
                setSelectModelos(response.data.content);
            });
        categoriaComponenteService.pesquisarTodos("situacao", "=", "ATIVO")
            .then(response => {
                setSelectCategoriaComponentes(response.data.content);
            });
        acessorioService.pesquisarTodos("situacao", "=", "ATIVO")
            .then(response => {
                setSelectAcessorios(response.data.content);
            });          
    }, []);

    const [formData, setFormData] = useState<any>({
        modeloPaiPrincipal: {
            value: null,
            id: "modeloPaiPrincipal",
            name: "modeloPaiPrincipal",
            placeholder: "Modelo",
            validation: (value: any) => value !== null,
            message: "Modelo é obrigatório",
        },
        categoriaComponentePaiPrincipal: {
            value: null,
            id: "categoriaComponentePaiPrincipal",
            name: "categoriaComponentePaiPrincipal",
            placeholder: "Categoria Componente",
            validation: (value: any) => value !== null,
            message: "Categoria Componente é obrigatória",
        },
        cores: {
            value: [],
            id: "cores",
            name: "cores",
            placeholder: "Cores",
            validation: function (value: DCor[]) {
                return value.length > 0;
            },
            message: "Escolha ao menos uma cor"
        },
        medidasPaiPrincipal: {
            value: null,
            id: "medidasPaiPrincipal",
            name: "medidasPaiPrincipal",
            placeholder: "Medidas",
            validation: function (value: DMedidas) {
                return value !== null;
            },
            message: "Medidas são obrigatórias"
        },
        materiais: {
            value: [],
            id: "materiais",
            name: "materiais",
            placeholder: "Materiais",
            validation: function (value: DMaterial[]) {
                return value.length > 0;
            },
            message: "Escolha ao menos um material"
        },
        maquinas: {
            value: [],
            id: "maquinas",
            name: "maquinas",
            placeholder: "Máquinas",
            validation: function (value: DMaquina[]) {
                return value.length > 0;
            },
            message: "Escolha ao menos um máquina"
        },
        implantacao: {
            value: "",
            id: "implantacao",
            name: "implantacao",
            placeholder: "Implantação"
        },
        bordasComprimentoPaiPrincipal: {
            value: null,
            id: "bordasComprimentoPaiPrincipal",
            name: "bordasComprimentoPaiPrincipal",
            type: "number",
            placeholder: "Bordas no Comprimento",
            validation: function (value: any) {
                return Number(value) >= 0;
            },
            message: "Bordas no Comprimento não pode ser negativa"
        },
        bordasLarguraPaiPrincipal: {
            value: null,
            id: "bordasLarguraPaiPrincipal",
            name: "bordasLarguraPaiPrincipal",
            type: "number",
            placeholder: "Bordas na Largura",
            validation: function (value: any) {
                return Number(value) >= 0;
            },
            message: "Bordas na Largura não pode ser negativa"
        },
        numeroCantoneirasPaiPrincipal: {
            value: null,
            id: "numeroCantoneirasPaiPrincipal",
            name: "numeroCantoneirasPaiPrincipal",
            type: "number",
            placeholder: "Número de Cantoneiras",
            validation: function (value: any) {
                return value === "" || value === null || Number(value) >= 0;
            },
            message: "Número de Cantoneiras não pode ser negativo"
        },
        tntUmaFacePaiPrincipal: {
            value: false,
            id: "tntUmaFacePaiPrincipal",
            name: "tntUmaFacePaiPrincipal",
            type: "boolean",
            placeholder: "Tnt uma Face",
            validation: (value: any) => value !== null,
            message: "Tnt um face é obrigatório",
        },
        plasticoAcimaPaiPrincipal: {
            value: false,
            id: "plasticoAcimaPaiPrincipal",
            name: "plasticoAcimaPaiPrincipal",
            type: "boolean",
            placeholder: "Plástico Acima",
            validation: (value: any) => value !== null,
            message: "Plástico acima é obrigatório",
        },
        plasticoAdicionalPaiPrincipal: {
            value: null,
            id: "plasticoAdicionalPaiPrincipal",
            name: "plasticoAdicionalPaiPrincipal",
            type: "number",
            placeholder: "Plástico Adicional",
            validation: function (value: any) {
                return value === "" || value === null || Number(value) >= 0;
            },
            message: "Plástico Adicional não pode ser negativo"
        },
        larguraPlasticoPaiPrincipal: {
            value: null,
            id: "larguraPlasticoPaiPrincipal",
            name: "larguraPlasticoPaiPrincipal",
            type: "number",
            placeholder: "Largura Plástico",
            validation: function (value: any) {
                return value === "" || value === null || Number(value) >= 0;
            },
            message: "Largura Plástico não pode ser negativo"
        },
        facesPaiPrincipal: {
            value: null,
            id: "facesPaiPrincipal",
            name: "facesPaiPrincipal",
            type: "number",
            placeholder: "Faces",
            validation: function (value: any) {
                return Number(value) >= 0;
            },
            message: "Faces não pode ser negativo"
        },
        paisSecundarios: [],
        acessoriosQuantidades: [],
    });

    function handleInputChange(event: any) {
        setFormData(forms.updateAndValidate(formData, event.target.name, event.target.value));
    }

    function handleTurnDirty(name: string) {
        setFormData(forms.dirtyAndValidate(formData, name));
    }

    function handleSubmit(event: any) {
        event.preventDefault();
    
        const requestBody: any = {
            paiPrincipal: {
                codigo: 0,
                descricao: "",
                modelo: formData.modeloPaiPrincipal.value,
                categoriaComponente: formData.categoriaComponentePaiPrincipal.value,
                bordasComprimento: formData.bordasComprimentoPaiPrincipal.value,
                bordasLargura: formData.bordasLarguraPaiPrincipal.value,
                numeroCantoneiras: formData.numeroCantoneirasPaiPrincipal.value,
                tntUmaFace: formData.tntUmaFacePaiPrincipal.value,
                plasticoAcima: formData.plasticoAcimaPaiPrincipal.value,
                plasticoAdicional: formData.plasticoAdicionalPaiPrincipal.value,
                larguraPlastico: formData.larguraPlasticoPaiPrincipal.value,
                faces: formData.facesPaiPrincipal.value,
                especial: false, 
                tipoPintura: 'ACETINADA', 
                situacao: 'ATIVO',
                filhos: []
            },
            medidasPaiPrincipal: formData.medidasPaiPrincipal.value,
            paisSecundarios: formData.paisSecundarios.map((obj: any) => ({
                pai: {
                    modelo: obj.pai.modelo,
                    categoriaComponente:obj.pai.categoriaComponente,
                    bordasComprimento: obj.pai.bordasComprimento,
                    bordasLargura: obj.pai.bordasLargura,
                    numeroCantoneiras: obj.pai.numeroCantoneiras,
                    tntUmaFace: obj.pai.tntUmaFace,
                    plasticoAcima: obj.pai.plasticoAcima,
                    plasticoAdicional: obj.pai.plasticoAdicional,
                    larguraPlastico: obj.pai.larguraPlastico,
                    faces: obj.pai.faces
                },
                medidas: obj.medidas,
                maquinas: obj.maquinas.map((m: any) => ({ codigo: m.codigo }))
            })),
            cores: formData.cores.value.map((cor: any) => ({ codigo: cor.codigo })),
            materiais: formData.materiais.value.map((mat: any) => ({ codigo: mat.codigo })),
            implantacao: dateTimeStart,
            acessoriosQuantidades: formData.acessoriosQuantidades.map((acc: any) => ({
                acessorio: { codigo: acc.acessorio.codigo }, 
                quantidade: acc.quantidade
            }))
            
        };

        const request = paiService.criarEstruturaModulacao(requestBody);

        request
            .then(() => {
                navigate("/fathers");
            })
            .catch(error => {
                const newInputs = forms.setBackendErrors(formData, error.response.data.errors);
                setFormData(newInputs);
            });
    }
    
    const handleDateTimeStartChange = (selectedDateTime: string | Date[]) => {
        if (Array.isArray(selectedDateTime) && selectedDateTime.length > 0) {
            const selectedDate = selectedDateTime[0] as Date;
            const formattedDate = selectedDate.toISOString().split('T')[0]; // Extrai "yyyy-MM-dd"
            setDateTimeStart(formattedDate);
        } else {
            setDateTimeStart('');
        }
    }; 

    const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = event.target;
        setFormData((prevState: any) => ({
            ...prevState,
            [name]: {
                ...prevState[name],
                value: checked
            }
        }));
    };

    const handleAddPaiSecundario = () => {
        const novoPaiSecundario = {
            id: Date.now(), // Um ID único
            pai: {
                modelo: null,
                categoriaComponente: null,
                bordasComprimento: "",
                bordasLargura: "",
                numeroCantoneiras: "",
                plasticoAdicional: "",
                larguraPlastico: "",
                faces: "",
                plasticoAcima: false,
                tntUmaFace: false
            },
            medidas: [],
            maquinas: [],
        };
    
        setFormData((prevState: any) => ({
            ...prevState,
            paisSecundarios: [...prevState.paisSecundarios, novoPaiSecundario]
        }));
    };
    
    const handleAddAcessorioQuantidade = () => {
        const novoAcessorioQuantidade = {
            id: Date.now(), // Um ID único
            acessorio: null,
            quantidade: ""
        };
    
        setFormData((prevState: any) => ({
            ...prevState,
            acessoriosQuantidades: [...prevState.acessoriosQuantidades, novoAcessorioQuantidade]
        }));
    };

    return(
        <main>
            <section id="form-section" className="container">
                <div className="form-container">
                    <form className="card form" onSubmit={handleSubmit}>
                        <h2>Estrutura Modulação/Alumínios</h2>
                        <div className="form-controls-container">
                            <h3>Pai Principal</h3>
                            <div>
                                <FormLabel text="Modelo" isRequired/>
                                <FormSelect
                                    {...formData.modeloPaiPrincipal}
                                    className="form-control form-select-container"
                                    options={selectModelos}
                                    value={formData.modeloPaiPrincipal.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "modeloPaiPrincipal",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    getOptionLabel={(obj: any) => obj.descricao}
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.modeloPaiPrincipal.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Categoria Componente" isRequired/>
                                <FormSelect
                                    {...formData.categoriaComponentePaiPrincipal}
                                    className="form-control form-select-container"
                                    options={selectCategoriaComponentes}
                                    value={formData.categoriaComponentePaiPrincipal.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "categoriaComponentePaiPrincipal",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    getOptionLabel={(obj: any) => obj.descricao}
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.categoriaComponentePaiPrincipal.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Cores" isRequired/>
                                <FormSelect
                                    {...formData.cores}
                                    className="form-control form-select-container"
                                    options={selectCores}
                                    onChange={(obj: any) => {
                                        const newFormData = forms.updateAndValidate(formData, "cores", obj);
                                        console.log(newFormData.cores);
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    isMulti
                                    getOptionLabel={(obj: any) => obj.descricao}
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.cores.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Medidas" isRequired/>
                                <FormSelect
                                    {...formData.medidasPaiPrincipal}
                                    className="form-control form-select-container"
                                    options={selectMedidas}
                                    value={formData.medidasPaiPrincipal.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "medidasPaiPrincipal",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    getOptionLabel={(obj: any) => `${obj.altura}X${obj.largura}X${obj.espessura}`}
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.medidasPaiPrincipal.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Materiais"/>
                                <FormSelect
                                    {...formData.materiais}
                                    className="form-control form-select-container"
                                    options={selectMateriais}
                                    onChange={(obj: any) => {
                                        const newFormData = forms.updateAndValidate(formData, "materiais", obj);
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    isMulti
                                    getOptionLabel={(obj: any) => obj.descricao}
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.materiais.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Máquinas" isRequired/>
                                <FormSelect
                                    {...formData.maquinas}
                                    className="form-control form-select-container"
                                    options={selectMaquinas}
                                    onChange={(obj: any) => {
                                        const newFormData = forms.updateAndValidate(formData, "maquinas", obj);
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    isMulti
                                    getOptionLabel={(obj: any) => obj.descricao}
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.maquinas.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Bordas Comprimento" />
                                <FormInput
                                    {...formData.bordasComprimentoPaiPrincipal}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.bordasComprimentoPaiPrincipal.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Bordas Largura" />
                                <FormInput
                                    {...formData.bordasLarguraPaiPrincipal}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.bordasLarguraPaiPrincipal.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Número Cantoneiras" isRequired/>
                                <FormInput
                                    {...formData.numeroCantoneirasPaiPrincipal}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.numeroCantoneirasPaiPrincipal.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Plástico Adicional" isRequired/>
                                <FormInput
                                    {...formData.plasticoAdicionalPaiPrincipal}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.plasticoAdicionalPaiPrincipal.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Largura Plastico" isRequired/>
                                <FormInput
                                    {...formData.larguraPlasticoPaiPrincipal}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.larguraPlasticoPaiPrincipal.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Faces" />
                                <FormInput
                                    {...formData.facesPaiPrincipal}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.facesPaiPrincipal.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Implantação" />
                                <Flatpickr
                                    id="implantacao"
                                    name="implantacao"
                                    value={dateTimeStart}
                                    onChange={(selectedDateTime: Date[]) => handleDateTimeStartChange(selectedDateTime)}
                                    options={{
                                        enableTime: false,
                                        dateFormat: 'Y-m-d',
                                    }}
                                    className="form-control"
                                />
                          </div>
                            <div>
                                <FormCheckbox
                                    id={formData.plasticoAcimaPaiPrincipal.id}
                                    name={formData.plasticoAcimaPaiPrincipal.name}
                                    label="Plástico Acima"
                                    checked={formData.plasticoAcimaPaiPrincipal.value}
                                    onChange={handleCheckboxChange}
                                />
                            </div>
                            <div>
                                <FormCheckbox
                                    id={formData.tntUmaFacePaiPrincipal.id}
                                    name={formData.tntUmaFacePaiPrincipal.name}
                                    label="Tnt uma Face"
                                    checked={formData.tntUmaFacePaiPrincipal.value}
                                    onChange={handleCheckboxChange}
                                />
                            </div>
                        </div>
                        <div className="form-controls-container">
                            <h3>Pais Secundários</h3>
                            <button type="button" className="btn btn-primary" onClick={handleAddPaiSecundario}>
                                Adicionar Pai Secundário
                            </button>
                            {formData.paisSecundarios.map((pai: any, index: number) => (
                                <div key={pai.id} className="pai-secundario-container">
                                    <h4>Pai Secundário {index + 1}</h4>
                                    <div>
                                    <FormLabel text="Modelo" />
                                    <FormSelect
                                        placeholder="Modelo"
                                        className="form-control form-select-container"
                                        options={selectModelos}
                                        value={pai.modelo}
                                        onChange={(selectedOption: any) => {
                                            const updatedPaisSecundarios = [...formData.paisSecundarios];
                                            updatedPaisSecundarios[index].pai.modelo = selectedOption;
                                            setFormData({ ...formData, paisSecundarios: updatedPaisSecundarios });
                                        }}
                                        getOptionLabel={(obj: any) => obj.descricao}
                                        getOptionValue={(obj: any) => String(obj.codigo)}
                                    />
                                    </div>
                                    <div>
                                    <FormLabel text="Categoria Componente" />
                                    <FormSelect
                                        placeholder="Categoria Componente"
                                        options={selectCategoriaComponentes}
                                        className="form-control form-select-container"
                                        value={pai.pai.categoriaComponente}
                                        onChange={(selectedOption: any) => {
                                            const updatedPaisSecundarios = [...formData.paisSecundarios];
                                            updatedPaisSecundarios[index].pai.categoriaComponente = selectedOption;
                                            setFormData({ ...formData, paisSecundarios: updatedPaisSecundarios });
                                        }}
                                        getOptionLabel={(obj: any) => obj.descricao}
                                        getOptionValue={(obj: any) => String(obj.codigo)}
                                    />
                                    </div>
                                    <FormLabel text="Medidas" />
                                    <FormSelect
                                        placeholder="Medidas"
                                        options={selectMedidas}
                                        className="form-control form-select-container"
                                        value={pai.medidas}
                                        onChange={(selectedOption: any) => {
                                            const updatedPaisSecundarios = [...formData.paisSecundarios];
                                            updatedPaisSecundarios[index].medidas = selectedOption;
                                            setFormData({ ...formData, paisSecundarios: updatedPaisSecundarios });
                                        }}
                                        getOptionLabel={(obj: any) => `${obj.altura}X${obj.largura}X${obj.espessura}`}
                                        getOptionValue={(obj: any) => String(obj.codigo)}
                                    />
                                    <FormLabel text="Bordas Comprimento" />
                                    <FormInput
                                        className="form-control"
                                        //onTurnDirty={handleTurnDirty}
                                        value={pai.pai.bordasComprimento}
                                        onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                                            const { value } = e.target;
                                            setFormData((prevState: any) => {
                                                const updatedPaisSecundarios = prevState.paisSecundarios.map((p: any, i: number) =>
                                                    i === index ? { ...p, pai: { ...p.pai, bordasComprimento: Number(value) } } : p
                                                );
                                                return { ...prevState, paisSecundarios: updatedPaisSecundarios };
                                            });
                                        }}                                      
                                    />
                                    <FormLabel text="Bordas Largura" />
                                    <FormInput
                                        className="form-control"
                                        //onTurnDirty={handleTurnDirty}
                                        value={pai.pai.bordasLargura}
                                        onChange={(e: any) => {
                                            const updatedPaisSecundarios = [...formData.paisSecundarios];
                                            updatedPaisSecundarios[index].pai.bordasLargura = Number(e.target.value);
                                            setFormData({ ...formData, paisSecundarios: updatedPaisSecundarios });
                                        }} 
                                    />
                                    <FormLabel text="Número Cantoneiras" />
                                    <FormInput
                                        className="form-control"
                                        //onTurnDirty={handleTurnDirty}
                                        value={pai.pai.numeroCantoneiras}
                                        onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                                            const { value } = e.target;
                                            setFormData((prevState: any) => {
                                                const updatedPaisSecundarios = prevState.paisSecundarios.map((p: any, i: number) =>
                                                    i === index ? { ...p, pai: { ...p.pai, numeroCantoneiras: value } } : p
                                                );
                                                return { ...prevState, paisSecundarios: updatedPaisSecundarios };
                                            });
                                        }} 
                                    />
                                    <FormCheckbox
                                        id={pai.id}
                                        name={pai.name}
                                        label="Tnt uma Face"
                                        checked={pai.pai.tntUmaFace.value}
                                        onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                                            const { value } = e.target;
                                            setFormData((prevState: any) => {
                                                const updatedPaisSecundarios = prevState.paisSecundarios.map((p: any, i: number) =>
                                                    i === index ? { ...p, pai: { ...p.pai, tntUmaFace: value } } : p
                                                );
                                                return { ...prevState, paisSecundarios: updatedPaisSecundarios };
                                            });
                                        }} 
                                    />
                                    <FormCheckbox
                                        id={pai.id}
                                        name={pai.name}
                                        label="Plástico Acima"
                                        checked={pai.pai.plasticoAcima.value}
                                        onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                                            const { value } = e.target;
                                            setFormData((prevState: any) => {
                                                const updatedPaisSecundarios = prevState.paisSecundarios.map((p: any, i: number) =>
                                                    i === index ? { ...p, pai: { ...p.pai, plasticoAcima: value } } : p
                                                );
                                                return { ...prevState, paisSecundarios: updatedPaisSecundarios };
                                            });
                                        }} 
                                    />
                                    <FormLabel text="Plástico Adicional" />
                                    <FormInput
                                        className="form-control"
                                        //onTurnDirty={handleTurnDirty}
                                        value={pai.pai.plasticoAdicional}
                                        onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                                            const { value } = e.target;
                                            setFormData((prevState: any) => {
                                                const updatedPaisSecundarios = prevState.paisSecundarios.map((p: any, i: number) =>
                                                    i === index ? { ...p, pai: { ...p.pai, plasticoAdicional: value } } : p
                                                );
                                                return { ...prevState, paisSecundarios: updatedPaisSecundarios };
                                            });
                                        }} 
                                    />
                                    <FormLabel text="Largura Plástico" />
                                    <FormInput
                                        className="form-control"
                                        //onTurnDirty={handleTurnDirty}
                                        value={pai.pai.larguraPlastico}
                                        onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                                            const { value } = e.target;
                                            setFormData((prevState: any) => {
                                                const updatedPaisSecundarios = prevState.paisSecundarios.map((p: any, i: number) =>
                                                    i === index ? { ...p, pai: { ...p.pai, larguraPlastico: value } } : p
                                                );
                                                return { ...prevState, paisSecundarios: updatedPaisSecundarios };
                                            });
                                        }} 
                                    />
                                    <FormLabel text="Faces" />
                                    <FormInput
                                        className="form-control"
                                        //onTurnDirty={handleTurnDirty}
                                        value={pai.pai.faces}
                                        onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                                            const { value } = e.target;
                                            setFormData((prevState: any) => {
                                                const updatedPaisSecundarios = prevState.paisSecundarios.map((p: any, i: number) =>
                                                    i === index ? { ...p, pai: { ...p.pai, faces: value } } : p
                                                );
                                                return { ...prevState, paisSecundarios: updatedPaisSecundarios };
                                            });
                                        }}                                       
                                    />
                                    <FormLabel text="Máquinas" />
                                    <FormSelect
                                        placeholder="Máquinas"
                                        className="form-control form-select-container"
                                        options={selectMaquinas}
                                        value={pai.maquinas}
                                        onChange={(selectedOption: any) => {
                                            const updatedPaisSecundarios = [...formData.paisSecundarios];
                                            updatedPaisSecundarios[index].maquinas = selectedOption;
                                            setFormData({ ...formData, paisSecundarios: updatedPaisSecundarios });
                                        }}
                                        isMulti
                                        getOptionLabel={(obj: any) => obj.nome}
                                        getOptionValue={(obj: any) => String(obj.codigo)}
                                    />
                                    <button
                                        type="button"
                                        className="btn btn-inverse"
                                        onClick={() => {
                                            const updatedPaisSecundarios = formData.paisSecundarios.filter((_ : any, i : any) => i !== index);
                                            setFormData({ ...formData, paisSecundarios: updatedPaisSecundarios });
                                        }}
                                    >
                                        Remover Pai Secundário
                                    </button>
                                </div>
                            ))}
                        </div>
                        <div className="form-controls-container">
                            <h3>Acessórios</h3>
                            <button type="button" className="btn btn-primary" onClick={handleAddAcessorioQuantidade}>
                                Adicionar Acessório
                            </button>
                            {formData.acessoriosQuantidades.map((pai: any, index: number) => (
                                <div key={pai.id} className="pai-secundario-container">
                                    <h4>Acessório {index + 1}</h4>
                                    <FormSelect
                                        placeholder="Acessório"
                                        className="form-control form-select-container"
                                        options={selectAcessorios}
                                        value={pai.acessorio}
                                        onChange={(selectedOption: any) => {
                                            const updatedacessoriosQuantidades = [...formData.acessoriosQuantidades];
                                            updatedacessoriosQuantidades[index].acessorio = selectedOption;
                                            setFormData({ ...formData, acessoriosQuantidades: updatedacessoriosQuantidades });
                                        }}
                                        getOptionLabel={(obj: any) => obj.descricao}
                                        getOptionValue={(obj: any) => String(obj.codigo)}
                                    />
                                    <FormLabel text="Quantidade" />
                                    <FormInput
                                        className="form-control"
                                        value={pai.quantidade}
                                        onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                                            const value = Number(e.target.value); 
                                            setFormData((prevState: any) => {
                                                const updatedAcessoriosQuantidades = prevState.acessoriosQuantidades.map((p: any, i: number) =>
                                                    i === index ? { ...p, quantidade: value } : p 
                                                );
                                                return { ...prevState, acessoriosQuantidades: updatedAcessoriosQuantidades };
                                            });
                                        }}                                       
                                    />
                                    <button
                                        type="button"
                                        className="btn btn-inverse"
                                        onClick={() => {
                                            const updatedacessoriosQuantidades = formData.acessoriosQuantidades.filter((_ : any, i : any) => i !== index);
                                            setFormData({ ...formData, acessoriosQuantidades: updatedacessoriosQuantidades });
                                        }}
                                    >
                                        Remover Acessório
                                    </button>
                                </div>
                            ))}
                        </div>
                        <div className="form-buttons">
                            <Link to="/fathers">
                                <button type="reset" className="btn btn-white">Cancelar</button>
                            </Link>
                            <button type="submit" className="btn btn-primary">Salvar</button>
                        </div>
                    </form>
                </div>
            </section>
        </main>
    );
}