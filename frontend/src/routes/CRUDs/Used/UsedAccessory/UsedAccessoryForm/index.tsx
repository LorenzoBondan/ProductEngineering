import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import * as acessorioUsadoService from '../../../../../services/acessorioUsadoService';
import * as acessorioService from '../../../../../services/acessorioService';
import * as filhoService from '../../../../../services/filhoService';
import * as forms from '../../../../../utils/forms';
import { DAcessorio } from '../../../../../models/acessorio';
import { DFilho } from '../../../../../models/filho';
import FormLabel from '../../../../../components/FormLabel';
import FormInput from '../../../../../components/FormInput';
import FormSelect from '../../../../../components/FormSelect';

export default function UsedAccessoryForm() {

    const params = useParams();

    const navigate = useNavigate();

    const location = useLocation();

    const isEditing = params.usedAccessoryId !== 'create';

    const [previousPath] = useState(location.state?.from || "/");

    const [acessorios, setAcessorios] = useState<DAcessorio[]>([]);

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
        acessorio: {
            value: null,
            id: "acessorio",
            name: "acessorio",
            placeholder: "Acessório",
            validation: (value: any) => value !== null,
            message: "Acessório é obrigatório"
        },
        filho: {
            value: null,
            id: "filho",
            name: "filho",
            placeholder: "filho",
            validation: (value: any) => value !== null,
            message: "Filho é obrigatório"
        },
        quantidade: {
            value: null,
            id: "quantidade",
            name: "quantidade",
            type: "number",
            placeholder: "Quantidade",
            validation: function (value: any) {
                return value === "" || value === null || Number(value) >= 0;
            },
            message: "Quantidade não pode ser nula ou negativa"
        },   
        
    });

    useEffect(() => {
        acessorioService.pesquisarTodos("", "", "")
            .then(response => {
                setAcessorios(response.data.content);
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
            acessorioUsadoService.pesquisarPorId(Number(params.usedAccessoryId))
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
            requestBody.codigo = Number(params.usedAccessoryId);
        }

        const request = isEditing
            ? acessorioUsadoService.atualizar(requestBody)
            : acessorioUsadoService.criar(requestBody);

        request
            .then(() => {
                navigate(previousPath);
            })
            .catch(error => {
                const newInputs = forms.setBackendErrors(formData, error.response.data.errors);
                setFormData(newInputs);
            });
    }
    
    return(
        <main>
            <section id="form-section" className="container">
                <div className="form-container">
                    <form className="card form" onSubmit={handleSubmit}>
                        <h2>Acessório Usado</h2>
                        <div className="form-controls-container">
                            <div>
                                <FormLabel text="Acessório" isRequired />
                                <FormSelect
                                    {...formData.acessorio}
                                    className="form-control form-select-container"
                                    options={acessorios}
                                    value={formData.acessorio.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "acessorio",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    getOptionLabel={(obj: any) => obj.descricao}
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.acessorio.message}</div>
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
                                <FormLabel text="Quantidade" isRequired />
                                <FormInput
                                    {...formData.quantidade}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.quantidade.message}</div>
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