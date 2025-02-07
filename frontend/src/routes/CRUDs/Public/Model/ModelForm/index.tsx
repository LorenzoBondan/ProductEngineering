import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import 'flatpickr/dist/themes/material_red.css';
import FormInput from '../../../../../components/FormInput';
import * as forms from '../../../../../utils/forms';
import * as modeloService from '../../../../../services/modeloService';
import FormLabel from '../../../../../components/FormLabel';
import { toast } from 'react-toastify';

export default function ModelForm() {

    const params = useParams();

    const navigate = useNavigate();

    const isEditing = params.modelId !== 'create';

    const [formData, setFormData] = useState<any>({
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
        }
    });

    useEffect(() => {
        if (isEditing) {
            modeloService.pesquisarPorId(Number(params.modelId))
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

        const successMessage = isEditing ? 'Modelo editado!' : 'Modelo Inserido!';

        const formDataValidated = forms.dirtyAndValidateAll(formData);
        if (forms.hasAnyInvalid(formDataValidated)) {
            setFormData(formDataValidated);
            return;
        }

        const requestBody = forms.toValues(formData);

        if (isEditing) {
            requestBody.codigo = Number(params.modelId);
        }

        const request = isEditing
            ? modeloService.atualizar(requestBody)
            : modeloService.criar(requestBody);

        request
            .then(() => {
                toast.success(successMessage);
                navigate("/models");
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
                        <h2>Modelo</h2>
                        <div className="form-controls-container">
                            <div>
                                <FormLabel text="Descrição" isRequired />
                                <FormInput
                                    {...formData.descricao}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.descricao.message}</div>
                            </div>
                        </div>
                        <div className="form-buttons">
                            <Link to="/models">
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