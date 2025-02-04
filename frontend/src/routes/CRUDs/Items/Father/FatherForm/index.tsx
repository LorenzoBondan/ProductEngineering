import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import 'flatpickr/dist/themes/material_red.css';
import FormInput from '../../../../../components/FormInput';
import FormSelect from '../../../../../components/FormSelect';
import * as forms from '../../../../../utils/forms';
import * as paiService from '../../../../../services/paiService';
import * as modeloService from '../../../../../services/modeloService';
import * as categoriaComponenteService from '../../../../../services/categoriaComponenteService';
import FormLabel from '../../../../../components/FormLabel';
import { DModelo } from '../../../../../models/modelo';
import { DCategoriaComponente } from '../../../../../models/categoriaComponente';
import { DTipoPinturaEnum } from '../../../../../models/enums/tipoPintura';
import FormCheckbox from '../../../../../components/FormCheckBox';

export default function FatherForm() {

    const params = useParams();

    const navigate = useNavigate();

    const isEditing = params.fatherId !== 'create';

    const [modelos, setModelos] = useState<DModelo[]>([]);
    const [categoriaComponentes, setCategoriaComponentes] = useState<DCategoriaComponente[]>([]);

    const [formData, setFormData] = useState<any>({
        modelo: {
            value: null,
            id: "modelo",
            name: "modelo",
            placeholder: "Modelo",
            validation: (value: any) => value !== null,
            message: "modelo é obrigatório",
        },
        categoriaComponente: {
            value: null,
            id: "categoriaComponente",
            name: "categoriaComponente",
            placeholder: "Categoria Componente",
            validation: (value: any) => value !== null,
            message: "Categoria Componente é obrigatória",
        },
        descricao: {
            value: "",
            id: "descricao",
            name: "descricao",
            type: "text",
            placeholder: "Descrição",
            validation: function (value: string) {
                return /^.{3,50}$/.test(value);
            },
            message: "Descrição deve ter entre 3 e 50 caracteres"
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
            message: "Bordas no Comprimento não pode ser negativo"
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
            message: "Bordas na Largura não pode ser negativo"
        },
        numeroCantoneiras: {
            value: null,
            id: "numeroCantoneiras",
            name: "numeroCantoneiras",
            type: "number",
            placeholder: "Número de Cantoneiras",
            validation: function (value: any) {
                return Number(value) >= 0;
            },
            message: "Número de Cantoneiras não pode ser negativo"
        },
        tntUmaFace: {
            value: null,
            id: "tntUmaFace",
            name: "tntUmaFace",
            type: "boolean",
            placeholder: "Tnt uma Face"
        },
        plasticoAcima: {
            value: null,
            id: "plasticoAcima",
            name: "plasticoAcima",
            type: "boolean",
            placeholder: "Plástico Acima"
        },
        plasticoAdicional: {
            value: null,
            id: "plasticoAdicional",
            name: "plasticoAdicional",
            type: "number",
            placeholder: "Plástico Adicional",
            validation: function (value: any) {
                return Number(value) >= 0;
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
                return Number(value) >= 0;
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
            value: null,
            id: "especial",
            name: "especial",
            type: "boolean",
            placeholder: "Especial"
        },
        tipoPintura: {
            value: null,
            id: "tipoPintura",
            name: "tipoPintura",
            placeholder: "Tipo de Pintura",
            validation: (value: any) => value !== null,
            message: "Tipo de Pintura é obrigatório",
        },
    });

    useEffect(() => {
        modeloService.pesquisarTodos("", "", "")
            .then(response => {
                setModelos(response.data.content);
            });
    }, []);

    useEffect(() => {
        categoriaComponenteService.pesquisarTodos("", "", "")
            .then(response => {
                setCategoriaComponentes(response.data.content);
            });
    }, []);

    useEffect(() => {
        if (isEditing) {
            paiService.pesquisarPorId(Number(params.fatherId))
                .then(response => {
                    const newFormData = forms.updateAll(formData, response.data);
                    
                    // enums
                    const tipoPinturaValue = response.data.tipoPintura;
                    newFormData.tipoPintura.value = tipoPinturaOptions.find(option => option.value === tipoPinturaValue);
                    
                    setFormData(newFormData);
                });
        }
    }, []);

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

        // enum format
        requestBody.tipoPintura = formData.tipoPintura.value.value;

        // nullable fields
        ['bordasComprimento', 'bordasLargura', 'numeroCantoneiras', 'tntUmaFace', 
            'plasticoAcima', 'plasticoAdicional', 'larguraPlastico', 'faces', 'especial', 
            'tipoPintura'
        ].forEach((field) => {
            if (requestBody[field] === "") {
                requestBody[field] = null;
            }
        });

        if (isEditing) {
            requestBody.codigo = Number(params.fatherId);
        }

        const request = isEditing
            ? paiService.atualizar(requestBody)
            : paiService.criar(requestBody);

        request
            .then(() => {
                navigate("/fathers");
            })
            .catch(error => {
                const newInputs = forms.setBackendErrors(formData, error.response.data.errors);
                setFormData(newInputs);
            });
    }

    const tipoPinturaOptions = Object.values(DTipoPinturaEnum).map((item) => ({
        value: item.name,
        label: item.label,
    }));

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
                        <h2>Pai</h2>
                        <div className="form-controls-container">
                            <div>
                                <FormLabel text="descricao" isRequired />
                                <FormInput
                                    {...formData.descricao}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.descricao.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Modelo" isRequired/>
                                <FormSelect
                                    {...formData.modelo}
                                    className="form-control form-select-container"
                                    options={modelos}
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
                                    getOptionValue={(obj: any) => String(obj.id)}
                                />
                                <div className="form-error">{formData.modelo.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Categoria Componente" isRequired/>
                                <FormSelect
                                    {...formData.categoriaComponente}
                                    className="form-control form-select-container"
                                    options={categoriaComponentes}
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
                                    getOptionValue={(obj: any) => String(obj.id)}
                                />
                                <div className="form-error">{formData.categoriaComponente.message}</div>
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
                                <FormLabel text="Número Cantoneiras" />
                                <FormInput
                                    {...formData.numeroCantoneiras}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.numeroCantoneiras.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Plástico Adicional" />
                                <FormInput
                                    {...formData.plasticoAdicional}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.plasticoAdicional.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Largura Plastico" />
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
                                <FormLabel text="Tipo de Pintura" isRequired />    
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