import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import 'flatpickr/dist/themes/material_red.css';
import Flatpickr from "react-flatpickr";
import FormInput from '../../../../../components/FormInput';
import FormSelect from '../../../../../components/FormSelect';
import * as forms from '../../../../../utils/forms';
import * as poliesterService from '../../../../../services/poliesterService';
import { DTipoMaterialEnum } from '../../../../../models/enums/tipoMaterial';
import FormLabel from '../../../../../components/FormLabel';
import { toast } from 'react-toastify';

export default function PolyesterForm() {

    const params = useParams();

    const navigate = useNavigate();

    const isEditing = params.polyesterId !== 'create';

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
        },
        tipoMaterial: {
            value: null,
            id: "tipoMaterial",
            name: "tipoMaterial",
            placeholder: "Tipo de Material",
            validation: (value: any) => value !== null,
            message: "Tipo de Material é obrigatório",
        },
        implantacao: {
            value: "",
            id: "implantacao",
            name: "implantacao",
            placeholder: "Implantação"
        },
        porcentagemPerda: {
            value: null,
            id: "porcentagemPerda",
            name: "porcentagemPerda",
            type: "number",
            placeholder: "Porcentagem de perda",
            validation: function (value: any) {
                return value === "" || value === null || Number(value) >= 0;
            },
            message: "Porcentagem de perda não pode ser negativa"
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
    });

    useEffect(() => {
        if (isEditing) {
            poliesterService.pesquisarPorId(Number(params.polyesterId))
                .then(response => {
                    const newFormData = forms.updateAll(formData, response.data);
                    
                    // enums
                    const tipoMaterialValue = response.data.tipoMaterial;
                    newFormData.tipoMaterial.value = tipoMaterialOptions.find(option => option.value === tipoMaterialValue);

                    // dates
                    setDateTimeStart(response.data.implantacao ? new Date(response.data.implantacao).toISOString().split('T')[0] : '');

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

        const successMessage = isEditing ? 'Poliéster editado!' : 'Poliéster Inserido!';

        const formDataValidated = forms.dirtyAndValidateAll(formData);
        if (forms.hasAnyInvalid(formDataValidated)) {
            setFormData(formDataValidated);
            return;
        }

        const requestBody = forms.toValues(formData);

        // date format
        requestBody.implantacao = dateTimeStart;

        // enum format
        requestBody.tipoMaterial = formData.tipoMaterial.value.value;

        // nullable fields
        ['implantacao'].forEach((field) => {
            if (requestBody[field] === "") {
                requestBody[field] = null;
            }
        });

        if (isEditing) {
            requestBody.codigo = Number(params.polyesterId);
        }

        const request = isEditing
            ? poliesterService.atualizar(requestBody)
            : poliesterService.criar(requestBody);

        request
            .then(() => {
                toast.success(successMessage);
                navigate("/polyesters");
            })
            .catch(error => {
                toast.error(error.response.data.error);
                const newInputs = forms.setBackendErrors(formData, error.response.data.errors);
                setFormData(newInputs);
            });
    }

    const tipoMaterialOptions = Object.values(DTipoMaterialEnum).map((item) => ({
        value: item.name,
        label: item.label,
    }));

    const [dateTimeStart, setDateTimeStart] = useState('');

    const handleDateTimeStartChange = (selectedDateTime: string | Date[]) => {
        if (Array.isArray(selectedDateTime) && selectedDateTime.length > 0) {
            const selectedDate = selectedDateTime[0] as Date;
            const formattedDate = selectedDate.toISOString().split('T')[0]; // Extrai "yyyy-MM-dd"
            setDateTimeStart(formattedDate);
        } else {
            setDateTimeStart('');
        }
    };  
    
    return(
        <main>
            <section id="form-section" className="container">
                <div className="form-container">
                    <form className="card form" onSubmit={handleSubmit}>
                        <h2>Poliéster</h2>
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
                            <div>
                                <FormLabel text="Tipo de Material" isRequired />
                                <FormSelect
                                    {...formData.tipoMaterial}
                                    className="form-control form-select-container"
                                    options={tipoMaterialOptions}
                                    value={formData.tipoMaterial.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "tipoMaterial",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                />
                                <div className="form-error">{formData.tipoMaterial.message}</div>
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
                            <div>
                                <FormLabel text="Porcentagem de Perda (%)" isRequired />
                                <FormInput
                                    {...formData.porcentagemPerda}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.porcentagemPerda.message}</div>
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
                        </div>
                        <div className="form-buttons">
                            <Link to="/polyesters">
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