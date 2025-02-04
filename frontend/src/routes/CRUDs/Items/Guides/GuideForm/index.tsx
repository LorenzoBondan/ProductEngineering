import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import 'flatpickr/dist/themes/material_red.css';
import Flatpickr from "react-flatpickr";
import FormInput from '../../../../../components/FormInput';
import * as forms from '../../../../../utils/forms';
import * as roteiroService from '../../../../../services/roteiroService';
import FormLabel from '../../../../../components/FormLabel';

export default function GuideForm() {

    const params = useParams();

    const navigate = useNavigate();

    const isEditing = params.guideId !== 'create';

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
        implantacao: {
            value: "",
            id: "implantacao",
            name: "implantacao",
            placeholder: "Implantação",
            validation: (value: any) => value !== null,
            message: "Implantação é obrigatório",
        },
        dataFinal: {
            value: "",
            id: "dataFinal",
            name: "dataFinal",
            placeholder: "Data Final"
        },
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
        
    });

    useEffect(() => {
        if (isEditing) {
            roteiroService.pesquisarPorId(Number(params.guideId))
                .then(response => {
                    const newFormData = forms.updateAll(formData, response.data);
                    
                    // dates
                    setDateTimeStart(response.data.implantacao ? new Date(response.data.implantacao).toISOString().split('T')[0] : '');
                    setDateTimeEnd(response.data.dataFinal ? new Date(response.data.dataFinal).toISOString().split('T')[0] : '');

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

        // date format
        requestBody.implantacao = dateTimeStart;
        requestBody.dataFinal = dateTimeEnd;

        // nullable fields
        ['dataFinal', 'valor'].forEach((field) => {
            if (requestBody[field] === "") {
                requestBody[field] = null;
            }
        });

        if (isEditing) {
            requestBody.codigo = Number(params.guideId);
        }

        const request = isEditing
            ? roteiroService.atualizar(requestBody)
            : roteiroService.criar(requestBody);

        request
            .then(() => {
                navigate("/guides");
            })
            .catch(error => {
                const newInputs = forms.setBackendErrors(formData, error.response.data.errors);
                setFormData(newInputs);
            });
    }

    const [dateTimeStart, setDateTimeStart] = useState('');
    const [dateTimeEnd, setDateTimeEnd] = useState('');

    const handleDateTimeStartChange = (selectedDateTime: string | Date[]) => {
        if (Array.isArray(selectedDateTime) && selectedDateTime.length > 0) {
            const selectedDate = selectedDateTime[0] as Date;
            const formattedDate = selectedDate.toISOString().split('T')[0]; // Extrai "yyyy-MM-dd"
            setDateTimeStart(formattedDate);
        } else {
            setDateTimeStart('');
        }
    };  

    const handleDateTimeEndChange = (selectedDateTime: string | Date[]) => {
        if (Array.isArray(selectedDateTime) && selectedDateTime.length > 0) {
            const selectedDate = selectedDateTime[0] as Date;
            const formattedDate = selectedDate.toISOString().split('T')[0]; // Extrai "yyyy-MM-dd"
            setDateTimeEnd(formattedDate);
        } else {
            setDateTimeEnd('');
        }
    };
    
    return(
        <main>
            <section id="form-section" className="container">
                <div className="form-container">
                    <form className="card form" onSubmit={handleSubmit}>
                        <h2>Roteiro</h2>
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
                                <FormLabel text="Implantação" isRequired/>
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
                                <FormLabel text="Data Final" />
                                <Flatpickr
                                    id="dataFinal"
                                    name="dataFinal"
                                    value={dateTimeEnd}
                                    onChange={(selectedDateTime: Date[]) => handleDateTimeEndChange(selectedDateTime)}
                                    options={{
                                        enableTime: false,
                                        dateFormat: 'Y-m-d',
                                    }}
                                    className="form-control"
                                />
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
                            <Link to="/guides">
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