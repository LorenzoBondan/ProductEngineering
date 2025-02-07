import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import * as materialUsadoService from '../../../../../services/materialUsadoService';
import * as materialService from '../../../../../services/materialService';
import * as filhoService from '../../../../../services/filhoService';
import * as forms from '../../../../../utils/forms';
import { DMaterial } from '../../../../../models/material';
import { DFilho } from '../../../../../models/filho';
import FormLabel from '../../../../../components/FormLabel';
import FormInput from '../../../../../components/FormInput';
import FormSelect from '../../../../../components/FormSelect';
import { toast } from 'react-toastify';

export default function UsedMaterialForm() {

    const params = useParams();

    const navigate = useNavigate();

    const location = useLocation();

    const isEditing = params.usedMaterialId !== 'create';

    const [previousPath] = useState(location.state?.from || "/");

    const [materiais, setMateriais] = useState<DMaterial[]>([]);

    const [filhos, setFilhos] = useState<DFilho[]>([]);

    const [formData, setFormData] = useState<any>({
        valor: {
            value: null,
            id: "valor",
            name: "valor",
            type: "number",
            placeholder: "Valor",
            validation: function (value: any) {
                return Number(value) >= 0;
            },
            message: "Valor não pode ser negativo"
        },
        material: {
            value: null,
            id: "material",
            name: "material",
            placeholder: "Material",
            validation: (value: any) => value !== null,
            message: "Material é obrigatório"
        },
        filho: {
            value: null,
            id: "filho",
            name: "filho",
            placeholder: "filho",
            validation: (value: any) => value !== null,
            message: "Filho é obrigatório"
        },
        quantidadeLiquida: {
            value: null,
            id: "quantidadeLiquida",
            name: "quantidadeLiquida",
            type: "number",
            placeholder: "Quantidade Líquida",
            validation: function (value: any) {
                return value === "" || value === null || Number(value) >= 0;
            },
            message: "Quantidade Líquida não pode ser nula ou negativa"
        },   
        quantidadeBruta: {
            value: null,
            id: "quantidadeBruta",
            name: "quantidadeBruta",
            type: "number",
            placeholder: "Quantidade Bruta",
            validation: function (value: any) {
                return value === "" || value === null || Number(value) >= 0;
            },
            message: "Quantidade Bruta não pode ser nula ou negativa"
        },  
    });

    useEffect(() => {
        materialService.pesquisarTodos("", "", "")
            .then(response => {
                setMateriais(response.data.content);
            });
    }, []);

    useEffect(() => {
        filhoService.pesquisarTodos("", "", "")
            .then(response => {
                setFilhos(response.data.content);
            });
    }, []);

    useEffect(() => {
        if (isEditing) {
            materialUsadoService.pesquisarPorId(Number(params.usedMaterialId))
                .then(response => {
                    const newFormData = forms.updateAll(formData, response.data);
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

        const successMessage = isEditing ? 'Material usado editado!' : 'Material usado Inserido!';

        const formDataValidated = forms.dirtyAndValidateAll(formData);
        if (forms.hasAnyInvalid(formDataValidated)) {
            setFormData(formDataValidated);
            return;
        }

        const requestBody = forms.toValues(formData);

        // nullable fields
        ['valor'].forEach((field) => {
            if (requestBody[field] === "") {
                requestBody[field] = null;
            }
        });

        if (isEditing) {
            requestBody.codigo = Number(params.usedMaterialId);
        }

        const request = isEditing
            ? materialUsadoService.atualizar(requestBody)
            : materialUsadoService.criar(requestBody);

        request
            .then(() => {
                toast.success(successMessage);
                navigate(previousPath);
            })
            .catch(error => {
                toast.error(error.response.data.error);
                const newInputs = forms.setBackendErrors(formData, error.response.data.errors);
                setFormData(newInputs);
            });
    }
    
    return(
        <main>
            <section id="form-section" className="container">
                <div className="form-container">
                    <form className="card form" onSubmit={handleSubmit}>
                        <h2>Material Usado</h2>
                        <div className="form-controls-container">
                            <div>
                                <FormLabel text="Material" isRequired />
                                <FormSelect
                                    {...formData.material}
                                    className="form-control form-select-container"
                                    options={materiais}
                                    value={formData.material.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "material",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    getOptionLabel={(obj: any) => obj.descricao}
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.material.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Filho" isRequired />
                                <FormSelect
                                    {...formData.filho}
                                    className="form-control form-select-container"
                                    options={filhos}
                                    value={formData.filho.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "filho",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    getOptionLabel={(obj: any) => obj.descricao}
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.filho.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Quantidade Líquida" isRequired />
                                <FormInput
                                    {...formData.quantidadeLiquida}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.quantidadeLiquida.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Quantidade Bruta" isRequired />
                                <FormInput
                                    {...formData.quantidadeBruta}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.quantidadeBruta.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Valor (R$)" />
                                <FormInput
                                    {...formData.valor}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.valor.message}</div>
                            </div>
                        </div>
                        <div className="form-buttons">
                            <Link to={previousPath}>
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