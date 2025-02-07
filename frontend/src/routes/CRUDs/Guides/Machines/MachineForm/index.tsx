import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import 'flatpickr/dist/themes/material_red.css';
import FormInput from '../../../../../components/FormInput';
import FormSelect from '../../../../../components/FormSelect';
import * as forms from '../../../../../utils/forms';
import * as maquinaService from '../../../../../services/maquinaService';
import * as grupoMaquinaService from '../../../../../services/grupoMaquinaService';
import FormLabel from '../../../../../components/FormLabel';
import { DGrupoMaquina } from '../../../../../models/grupoMaquina';
import FormTextArea from '../../../../../components/FormTextArea';
import { toast } from 'react-toastify';

export default function MachineForm() {

    const params = useParams();

    const navigate = useNavigate();

    const isEditing = params.machineId !== 'create';

    const [grupoMaquinas, setGrupoMaquinas] = useState<DGrupoMaquina[]>([]);

    const [formData, setFormData] = useState<any>({
        nome: {
            value: "",
            id: "nome",
            name: "nome",
            type: "text",
            placeholder: "Nome",
            validation: function (value: string) {
                return /^.{3,50}$/.test(value);
            },
            message: "Nome deve ter entre 3 e 50 caracteres"
        },
        formula: {
            value: "",
            id: "formula",
            name: "formula",
            type: "text",
            placeholder: "Fórmula",
            validation: function (value: string) {
                return /^.{3,100}$/.test(value);
            },
            message: "Fórmula deve ter entre 3 e 100 caracteres"
        },
        valor: {
            value: null,
            id: "valor",
            name: "valor",
            type: "number",
            placeholder: "Valor",
            validation: function (value: any) {
                return value === "" || value === null || Number(value) >= 0;
            },
            message: "Valor não pode ser negativo"
        },
        grupoMaquina: {
            value: null,
            id: "grupoMaquina",
            name: "grupoMaquina",
            placeholder: "Grupo Máquina",
            validation: (value: any) => value !== null,
            message: "Grupo de Máquina é obrigatório",
        }
    });

    useEffect(() => {
        grupoMaquinaService.pesquisarTodos("", "", "")
            .then(response => {
                setGrupoMaquinas(response.data.content);
            });
    }, []);

    useEffect(() => {
        if (isEditing) {
            maquinaService.pesquisarPorId(Number(params.machineId))
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

        const successMessage = isEditing ? 'Máquina editada!' : 'Máquina Inserida!';

        const formDataValidated = forms.dirtyAndValidateAll(formData);
        if (forms.hasAnyInvalid(formDataValidated)) {
            setFormData(formDataValidated);
            return;
        }

        const requestBody = forms.toValues(formData);

        if (isEditing) {
            requestBody.codigo = Number(params.machineId);
        }

        const request = isEditing
            ? maquinaService.atualizar(requestBody)
            : maquinaService.criar(requestBody);

        request
            .then(() => {
                toast.success(successMessage);
                navigate("/machines");
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
                        <h2>Máquina</h2>
                        <div className="form-controls-container">
                            <div>
                                <FormLabel text="Nome" isRequired />
                                <FormInput
                                    {...formData.nome}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.nome.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Fórmula" isRequired />
                                <FormTextArea
                                    {...formData.formula}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.formula.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Grupo Máquina" isRequired/>
                                <FormSelect
                                    {...formData.grupoMaquina}
                                    className="form-control form-select-container"
                                    options={grupoMaquinas}
                                    value={formData.grupoMaquina.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "grupoMaquina",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    getOptionLabel={(obj: any) => obj.nome}
                                    getOptionValue={(obj: any) => String(obj.codigo)}
                                />
                                <div className="form-error">{formData.grupoMaquina.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Valor (R$)" isRequired />
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
                            <Link to="/machines">
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