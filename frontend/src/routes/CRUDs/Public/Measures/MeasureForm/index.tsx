import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import 'flatpickr/dist/themes/material_red.css';
import FormInput from '../../../../../components/FormInput';
import * as forms from '../../../../../utils/forms';
import * as medidasService from '../../../../../services/medidasService';
import FormLabel from '../../../../../components/FormLabel';
import { toast } from 'react-toastify';

export default function MeasureForm() {

    const params = useParams();

    const navigate = useNavigate();

    const isEditing = params.measureId !== 'create';

    const [formData, setFormData] = useState<any>({
        altura: {
            value: null,
            id: "altura",
            name: "altura",
            type: "number",
            placeholder: "Altura",
            validation: function (value: any) {
                return value !== null && Number(value) >= 0;
            },
            message: "Altura não pode ser negativa"
        },
        largura: {
            value: null,
            id: "largura",
            name: "largura",
            type: "number",
            placeholder: "Largura",
            validation: function (value: any) {
                return value !== null && Number(value) >= 0;
            },
            message: "Largura não pode ser negativa"
        },
        espessura: {
            value: null,
            id: "espessura",
            name: "espessura",
            type: "number",
            placeholder: "Espessura",
            validation: function (value: any) {
                return value !== null && Number(value) >= 0;
            },
            message: "Espessura não pode ser negativa"
        },
    });

    useEffect(() => {
        if (isEditing) {
            medidasService.pesquisarPorId(Number(params.measureId))
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

        const successMessage = isEditing ? 'Medidas editadas!' : 'Medidas Inseridas!';

        const formDataValidated = forms.dirtyAndValidateAll(formData);
        if (forms.hasAnyInvalid(formDataValidated)) {
            setFormData(formDataValidated);
            return;
        }

        const requestBody = forms.toValues(formData);

        if (isEditing) {
            requestBody.codigo = Number(params.measureId);
        }

        const request = isEditing
            ? medidasService.atualizar(requestBody)
            : medidasService.criar(requestBody);

        request
            .then(() => {
                toast.success(successMessage);
                navigate("/measures");
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
                        <h2>Medidas</h2>
                        <div className="form-controls-container">
                            <div>
                                <FormLabel text="Altura (mm)" isRequired/>
                                <FormInput
                                    {...formData.altura}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.altura.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Largura (mm)" isRequired/>
                                <FormInput
                                    {...formData.largura}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.largura.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Espessura (mm)" isRequired/>
                                <FormInput
                                    {...formData.espessura}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.espessura.message}</div>
                            </div>
                        </div>
                        <div className="form-buttons">
                            <Link to="/measures">
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