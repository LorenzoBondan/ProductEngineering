import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import * as roteiroMaquinaService from '../../../../../services/roteiroMaquinaService';
import * as roteiroService from '../../../../../services/roteiroService';
import * as maquinaService from '../../../../../services/maquinaService';
import * as forms from '../../../../../utils/forms';
import { DRoteiro } from '../../../../../models/roteiro';
import { DMaquina } from '../../../../../models/maquina';
import FormLabel from '../../../../../components/FormLabel';
import FormInput from '../../../../../components/FormInput';
import FormSelect from '../../../../../components/FormSelect';

export default function GuideMachineForm() {

    const params = useParams();

    const navigate = useNavigate();

    const location = useLocation();

    const isEditing = params.guideMachineId !== 'create';

    const [previousPath] = useState(location.state?.from || "/");

    const [roteiros, setRoteiros] = useState<DRoteiro[]>([]);

    const [maquinas, setMaquinas] = useState<DMaquina[]>([]);

    const [formData, setFormData] = useState<any>({
        roteiro: {
            value: null,
            id: "roteiro",
            name: "roteiro",
            placeholder: "Roteiro",
            validation: (value: any) => value !== null,
            message: "Roteiro é obrigatório"
        },
        maquina: {
            value: null,
            id: "maquina",
            name: "maquina",
            placeholder: "Máquina",
            validation: (value: any) => value !== null,
            message: "Máquina é obrigatória"
        },
        tempoHomem: {
            value: null,
            id: "tempoHomem",
            name: "tempoHomem",
            type: "number",
            placeholder: "Tempo Homem",
            validation: function (value: any) {
                return Number(value) >= 0;
            },
            message: "Tempo Homem não pode ser negativo"
        },   
        tempoMaquina: {
            value: null,
            id: "tempoMaquina",
            name: "tempoMaquina",
            type: "number",
            placeholder: "Tempo Máquina",
            validation: function (value: any) {
                return Number(value) >= 0;
            },
            message: "Tempo Máquina não pode ser negativo"
        },  
    });

    useEffect(() => {
        roteiroService.pesquisarTodos("", "", "")
            .then(response => {
                setRoteiros(response.data.content);
            });
    }, []);

    useEffect(() => {
        maquinaService.pesquisarTodos("", "", "")
            .then(response => {
                setMaquinas(response.data.content);
            });
    }, []);

    useEffect(() => {
        if (isEditing) {
            roteiroMaquinaService.pesquisarPorId(Number(params.guideMachineId))
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
        ['tempoHomem', 'tempoMaquina'].forEach((field) => {
            if (requestBody[field] === "") {
                requestBody[field] = null;
            }
        });

        if (isEditing) {
            requestBody.codigo = Number(params.guideMachineId);
        }

        const request = isEditing
            ? roteiroMaquinaService.atualizar(requestBody)
            : roteiroMaquinaService.criar(requestBody);

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
                        <h2>Roteiro Máquina</h2>
                        <div className="form-controls-container">
                            <div>
                                <FormLabel text="Roteiro" isRequired />
                                <FormSelect
                                    {...formData.roteiro}
                                    className="form-control form-select-container"
                                    options={roteiros}
                                    value={formData.roteiro.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "roteiro",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    getOptionLabel={(obj: any) => obj.descricao}
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.roteiro.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Máquina" isRequired />
                                <FormSelect
                                    {...formData.maquina}
                                    className="form-control form-select-container"
                                    options={maquinas}
                                    value={formData.maquina.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "maquina",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    getOptionLabel={(obj: any) => obj.descricao}
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.maquina.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Tempo Homem" />
                                <FormInput
                                    {...formData.tempoHomem}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.tempoHomem.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Tempo Maquina" />
                                <FormInput
                                    {...formData.tempoMaquina}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.tempoMaquina.message}</div>
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