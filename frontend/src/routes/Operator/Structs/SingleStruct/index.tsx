import * as paiService from '../../../../services/paiService';
import * as modeloService from '../../../../services/modeloService';
import * as categoriaComponenteService from '../../../../services/categoriaComponenteService';
import * as corService from '../../../../services/corService';
import * as medidasService from '../../../../services/medidasService';
import * as materialService from '../../../../services/materialService';
import * as maquinaService from '../../../../services/maquinaService';
import * as forms from '../../../../utils/forms';
import { useEffect, useState } from 'react';
import { DModelo } from '../../../../models/modelo';
import { DCor } from '../../../../models/cor';
import { DMedidas } from '../../../../models/medidas';
import { DMaterial } from '../../../../models/material';
import { DMaquina } from '../../../../models/maquina';
import { DCategoriaComponente } from '../../../../models/categoriaComponente';
import { DTipoPinturaEnum } from '../../../../models/enums/tipoPintura';
import { DTipoFilhoEnum } from '../../../../models/enums/tipoFilho';
import { useNavigate } from 'react-router-dom';
import FormLabel from '../../../../components/FormLabel';
import FormInput from '../../../../components/FormInput';
import FormSelect from '../../../../components/FormSelect';
import FormCheckbox from '../../../../components/FormCheckBox';
import 'flatpickr/dist/themes/material_red.css';
import Flatpickr from "react-flatpickr";
import { Link } from 'react-router-dom';

export default function SingleStruct() {

    const navigate = useNavigate();

    const [selectCores, setSelectCores] = useState<DCor[]>([]);
    const [selectMedidas, setSelectMedidas] = useState<DMedidas[]>([]);
    const [selectMateriais, setSelectMateriais] = useState<DMaterial[]>([]);
    const [selectMaquinas, setSelectMaquinas] = useState<DMaquina[]>([]);
    const [selectModelos, setSelectModelos] = useState<DModelo[]>([]);
    const [selectCategoriaComponentes, setSelectCategoriaComponentes] = useState<DCategoriaComponente[]>([]);
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
    }, []);

    const [formData, setFormData] = useState<any>({
        modelo: {
            value: null,
            id: "modelo",
            name: "modelo",
            placeholder: "Modelo",
            validation: (value: any) => value !== null,
            message: "Modelo é obrigatório",
        },
        categoriaComponente: {
            value: null,
            id: "categoriaComponente",
            name: "categoriaComponente",
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
        medidas: {
            value: [],
            id: "medidas",
            name: "medidas",
            placeholder: "Medidas",
            validation: function (value: DMedidas[]) {
                return value.length > 0;
            },
            message: "Escolha ao menos uma medida"
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
        tipoFilho: {
            value: null,
            id: "tipoFilho",
            name: "tipoFilho",
            placeholder: "Tipo de Filho",
            validation: (value: any) => value !== null,
            message: "Tipo de Filho é obrigatório",
        },
        bordasComprimento: {
            value: null,
            id: "bordasComprimento",
            name: "bordasComprimento",
            type: "number",
            placeholder: "Bordas no Comprimento",
            validation: function (value: any) {
                return Number(value) >= 0;
            },
            message: "Bordas no Comprimento não pode ser negativa"
        },
        bordasLargura: {
            value: null,
            id: "bordasLargura",
            name: "bordasLargura",
            type: "number",
            placeholder: "Bordas na Largura",
            validation: function (value: any) {
                return Number(value) >= 0;
            },
            message: "Bordas na Largura não pode ser negativa"
        },
        numeroCantoneiras: {
            value: null,
            id: "numeroCantoneiras",
            name: "numeroCantoneiras",
            type: "number",
            placeholder: "Número de Cantoneiras",
            validation: function (value: any) {
                return value === "" || value === null || Number(value) >= 0;
            },
            message: "Número de Cantoneiras não pode ser negativo"
        },
        tntUmaFace: {
            value: false,
            id: "tntUmaFace",
            name: "tntUmaFace",
            type: "boolean",
            placeholder: "Tnt uma Face",
            validation: (value: any) => value !== null,
            message: "Tnt um face é obrigatório",
        },
        plasticoAcima: {
            value: false,
            id: "plasticoAcima",
            name: "plasticoAcima",
            type: "boolean",
            placeholder: "Plástico Acima",
            validation: (value: any) => value !== null,
            message: "Plástico acima é obrigatório",
        },
        plasticoAdicional: {
            value: null,
            id: "plasticoAdicional",
            name: "plasticoAdicional",
            type: "number",
            placeholder: "Plástico Adicional",
            validation: function (value: any) {
                return value === "" || value === null || Number(value) >= 0;
            },
            message: "Plástico Adicional não pode ser negativo"
        },
        larguraPlastico: {
            value: null,
            id: "larguraPlastico",
            name: "larguraPlastico",
            type: "number",
            placeholder: "Largura Plástico",
            validation: function (value: any) {
                return value === "" || value === null || Number(value) >= 0;
            },
            message: "Largura Plástico não pode ser negativo"
        },
        faces: {
            value: null,
            id: "faces",
            name: "faces",
            type: "number",
            placeholder: "Faces",
            validation: function (value: any) {
                return Number(value) >= 0;
            },
            message: "Faces não pode ser negativo"
        },
        especial: {
            value: false,
            id: "especial",
            name: "especial",
            type: "boolean",
            placeholder: "Especial"
        },
        tipoPintura: {
            value: null,
            id: "tipoPintura",
            name: "tipoPintura",
            placeholder: "Tipo de Pintura"
        },
    });

    function handleInputChange(event: any) {
        setFormData(forms.updateAndValidate(formData, event.target.name, event.target.value));
    }

    function handleTurnDirty(name: string) {
        setFormData(forms.dirtyAndValidate(formData, name));
    }

    function handleSubmit(event: any) {
        event.preventDefault();

        const formDataValidated = forms.dirtyAndValidateAll(formData);
        if (forms.hasAnyInvalid(formDataValidated)) {
            setFormData(formDataValidated);
            return;
        }

        const requestBody = forms.toValues(formData);
        console.log(requestBody);

        // date format
        requestBody.implantacao = dateTimeStart;

        // enum format
        if(requestBody.tipoPintura !== null){
            requestBody.tipoPintura = formData.tipoPintura.value.value;
        }
        requestBody.tipoFilho = formData.tipoFilho.value.value;

        // nullable fields
        ['bordasComprimento', 'bordasLargura'].forEach((field) => {
            if (requestBody[field] === "") {
                requestBody[field] = null;
            }
        });

        const request = paiService.criarEstrutura(requestBody);

        request
            .then(() => {
                navigate("/fathers");
            })
            .catch(error => {
                const newInputs = forms.setBackendErrors(formData, error.response.data.errors);
                setFormData(newInputs);
            });
    }

    const tipoFilhoOptions = Object.values(DTipoFilhoEnum).map((item) => ({
        value: item.name,
        label: item.label,
    }));

    const tipoPinturaOptions = Object.values(DTipoPinturaEnum).map((item) => ({
        value: item.name,
        label: item.label,
    }));

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

    return(
        <main>
            <section id="form-section" className="container">
                <div className="form-container">
                    <form className="card form" onSubmit={handleSubmit}>
                        <h2>Estrutura MDP/MDF</h2>
                        <div className="form-controls-container">
                            <div>
                                <FormLabel text="Modelo" isRequired/>
                                <FormSelect
                                    {...formData.modelo}
                                    className="form-control form-select-container"
                                    options={selectModelos}
                                    value={formData.modelo.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "modelo",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    getOptionLabel={(obj: any) => obj.descricao}
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.modelo.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Categoria Componente" isRequired/>
                                <FormSelect
                                    {...formData.categoriaComponente}
                                    className="form-control form-select-container"
                                    options={selectCategoriaComponentes}
                                    value={formData.categoriaComponente.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "categoriaComponente",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    getOptionLabel={(obj: any) => obj.descricao}
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.categoriaComponente.message}</div>
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
                                    {...formData.medidas}
                                    className="form-control form-select-container"
                                    options={selectMedidas}
                                    onChange={(obj: any) => {
                                        const newFormData = forms.updateAndValidate(formData, "medidas", obj);
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    isMulti
                                    getOptionLabel={(obj: any) => `${obj.altura}X${obj.largura}X${obj.espessura}` }
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.medidas.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Materiais" isRequired/>
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
                                    {...formData.bordasComprimento}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.bordasComprimento.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Bordas Largura" />
                                <FormInput
                                    {...formData.bordasLargura}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.bordasLargura.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Número Cantoneiras" isRequired/>
                                <FormInput
                                    {...formData.numeroCantoneiras}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.numeroCantoneiras.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Plástico Adicional" isRequired/>
                                <FormInput
                                    {...formData.plasticoAdicional}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.plasticoAdicional.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Largura Plastico" isRequired/>
                                <FormInput
                                    {...formData.larguraPlastico}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.larguraPlastico.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Faces" />
                                <FormInput
                                    {...formData.faces}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.faces.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Tipo de Filho" isRequired />    
                                <FormSelect
                                    {...formData.tipoFilho}
                                    className="form-control form-select-container"
                                    options={tipoFilhoOptions}
                                    value={formData.tipoFilho.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "tipoFilho",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                />
                                <div className="form-error">{formData.tipoFilho.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Tipo de Pintura" />    
                                <FormSelect
                                    {...formData.tipoPintura}
                                    className="form-control form-select-container"
                                    options={tipoPinturaOptions}
                                    value={formData.tipoPintura.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "tipoPintura",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                />
                                <div className="form-error">{formData.tipoPintura.message}</div>
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
                                    id={formData.plasticoAcima.id}
                                    name={formData.plasticoAcima.name}
                                    label="Plástico Acima"
                                    checked={formData.plasticoAcima.value}
                                    onChange={handleCheckboxChange}
                                />
                            </div>
                            <div>
                                <FormCheckbox
                                    id={formData.especial.id}
                                    name={formData.especial.name}
                                    label="Especial"
                                    checked={formData.especial.value}
                                    onChange={handleCheckboxChange}
                                />
                            </div>
                            <div>
                                <FormCheckbox
                                    id={formData.tntUmaFace.id}
                                    name={formData.tntUmaFace.name}
                                    label="Tnt uma Face"
                                    checked={formData.tntUmaFace.value}
                                    onChange={handleCheckboxChange}
                                />
                            </div>
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