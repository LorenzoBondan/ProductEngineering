import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import 'flatpickr/dist/themes/material_red.css';
import Flatpickr from "react-flatpickr";
import FormInput from '../../../../../components/FormInput';
import FormSelect from '../../../../../components/FormSelect';
import * as forms from '../../../../../utils/forms';
import * as acessorioService from '../../../../../services/acessorioService';
import * as corService from '../../../../../services/corService';
import * as medidasService from '../../../../../services/medidasService';
import { DCor } from '../../../../../models/cor';
import FormLabel from '../../../../../components/FormLabel';
import { DMedidas } from '../../../../../models/medidas';

export default function AccessoryForm() {

    const params = useParams();

    const navigate = useNavigate();

    const isEditing = params.accessoryId !== 'create';

    const [cores, setCores] = useState<DCor[]>([]);

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
            placeholder: "Implantação"
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
        cor: {
            value: null,
            id: "cor",
            name: "cor",
            placeholder: "Cor"
        },
    });

    useEffect(() => {
        corService.pesquisarTodos("", "", "")
            .then(response => {
                setCores(response.data.content);
            });
    }, []);

    useEffect(() => {
        if (isEditing) {
            acessorioService.pesquisarPorId(Number(params.accessoryId))
                .then(response => {
                    const newFormData = forms.updateAll(formData, response.data);
                    
                    // Preenche os inputs de medidas se existirem
                    if (response.data.medidas) {
                        newFormData.altura.value = response.data.medidas.altura;
                        newFormData.largura.value = response.data.medidas.largura;
                        newFormData.espessura.value = response.data.medidas.espessura;
                    }
    
                    // Formata a data de implantação corretamente
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

    async function buscarOuCriarMedidas(altura: number, largura: number, espessura: number) {
        // Tenta buscar as medidas existentes no banco
        const response = await medidasService.pesquisarPorMedidas(altura, largura, espessura);
    
        if (response.data) {
            return response.data; // Retorna as medidas encontradas
        }
    
        // Se não encontrar, cria novas medidas
        const novaMedida: DMedidas = {
            codigo: 0, // Código será gerado pelo backend
            altura,
            largura,
            espessura,
            situacao: 'ATIVO'
        };
    
        const novaResponse = await medidasService.criar(novaMedida);
        return novaResponse.data; // Retorna a nova medida criada
    }    
    
    async function handleSubmit(event: any) {
        event.preventDefault();
    
        const formDataValidated = forms.dirtyAndValidateAll(formData);
        if (forms.hasAnyInvalid(formDataValidated)) {
            setFormData(formDataValidated);
            return;
        }
    
        const requestBody = forms.toValues(formData);
    
        // Formata a data de implantação corretamente
        requestBody.implantacao = dateTimeStart || null;
    
        // Verifica se há medidas antes de buscar ou criar
        if (formData.altura?.value && formData.largura?.value && formData.espessura?.value) {
            const medidas = await buscarOuCriarMedidas(
                Number(formData.altura.value),
                Number(formData.largura.value),
                Number(formData.espessura.value)
            );
            requestBody.medidas = { codigo: medidas.codigo };
        }
    
        // Campos opcionais devem ser `null` caso estejam vazios
        ['altura', 'largura', 'espessura', 'cor', 'implantacao'].forEach((field) => {
            if (requestBody[field] === "") {
                requestBody[field] = null;
            }
        });
    
        if (isEditing) {
            requestBody.codigo = Number(params.accessoryId);
        }
    
        const request = isEditing
            ? acessorioService.atualizar(requestBody)
            : acessorioService.criar(requestBody);
    
        request
            .then(() => navigate("/accessories"))
            .catch(error => {
                const newInputs = forms.setBackendErrors(formData, error.response.data.errors);
                setFormData(newInputs);
            });
    }    

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
                        <h2>Acessório</h2>
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
                                <FormLabel text="Cor" />
                                <FormSelect
                                    {...formData.cor}
                                    className="form-control form-select-container"
                                    options={cores}
                                    value={formData.cor.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "cor",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    getOptionLabel={(obj: any) => obj.descricao}
                                    getOptionValue={(obj: any) => String(obj.id)}
                                />
                                <div className="form-error">{formData.cor.message}</div>
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
                                <FormLabel text="Altura (mm)" />
                                <FormInput
                                    {...formData.altura}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.altura.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Largura (mm)" />
                                <FormInput
                                    {...formData.largura}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.largura.message}</div>
                            </div>
                            <div>
                                <FormLabel text="Espessura (mm)" />
                                <FormInput
                                    {...formData.espessura}
                                    className="form-control"
                                    onTurnDirty={handleTurnDirty}
                                    onChange={handleInputChange}
                                />
                                <div className="form-error">{formData.espessura.message}</div>
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
                            <Link to="/accessories">
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