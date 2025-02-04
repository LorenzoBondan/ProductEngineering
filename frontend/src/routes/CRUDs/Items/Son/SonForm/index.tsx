import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import 'flatpickr/dist/themes/material_red.css';
import Flatpickr from "react-flatpickr";
import FormInput from '../../../../../components/FormInput';
import FormSelect from '../../../../../components/FormSelect';
import * as forms from '../../../../../utils/forms';
import * as filhoService from '../../../../../services/filhoService';
import * as corService from '../../../../../services/corService';
import * as paiService from '../../../../../services/paiService';
import * as roteiroService from '../../../../../services/roteiroService';
import * as medidasService from '../../../../../services/medidasService';
import { DCor } from '../../../../../models/cor';
import FormLabel from '../../../../../components/FormLabel';
import { DMedidas } from '../../../../../models/medidas';
import { DPai } from '../../../../../models/pai';
import { DRoteiro } from '../../../../../models/roteiro';
import { DTipoFilhoEnum } from '../../../../../models/enums/tipoFilho';

export default function SonForm() {

    const params = useParams();

    const navigate = useNavigate();

    const isEditing = params.sonId !== 'create';

    const [cores, setCores] = useState<DCor[]>([]);
    const [pais, setPais] = useState<DPai[]>([]);
    const [roteiros, setRoteiros] = useState<DRoteiro[]>([]);

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
                return Number(value) >= 0;
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
            placeholder: "Cor",
            validation: (value: any) => value !== null,
            message: "Cor é obrigatória",
        },
        pai: {
            value: null,
            id: "pai",
            name: "pai",
            placeholder: "Pai"
        },
        roteiro: {
            value: null,
            id: "roteiro",
            name: "roteiro",
            placeholder: "Roteiro"
        },
        tipo: {
            value: null,
            id: "tipo",
            name: "tipo",
            placeholder: "Tipo de Filho",
            validation: (value: any) => value !== null,
            message: "Tipo de Filho é obrigatório",
        },
    });

    useEffect(() => {
        corService.pesquisarTodos("", "", "")
            .then(response => {
                setCores(response.data.content);
            });
    }, []);

    useEffect(() => {
        paiService.pesquisarTodos("", "", "")
            .then(response => {
                setPais(response.data.content);
            });
    }, []);

    useEffect(() => {
        roteiroService.pesquisarTodos("", "", "")
            .then(response => {
                setRoteiros(response.data.content);
            });
    }, []);

    useEffect(() => {
        if (isEditing) {
            filhoService.pesquisarPorId(Number(params.sonId))
                .then(response => {
                    const newFormData = forms.updateAll(formData, response.data);
                    
                    // enums
                    const tipoValue = response.data.tipo;
                    newFormData.tipo.value = tipoOptions.find(option => option.value === tipoValue);

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

        // enum format
        requestBody.tipo = formData.tipo.value.value;
    
        // Campos opcionais devem ser `null` caso estejam vazios
        ['implantacao', 'pai', 'roteiro', 'valor'].forEach((field) => {
            if (requestBody[field] === "") {
                requestBody[field] = null;
            }
        });
    
        if (isEditing) {
            requestBody.codigo = Number(params.sonId);
        }
    
        const request = isEditing
            ? filhoService.atualizar(requestBody)
            : filhoService.criar(requestBody);
    
        request
            .then(() => navigate("/sons"))
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

    const tipoOptions = Object.values(DTipoFilhoEnum).map((item) => ({
        value: item.name,
        label: item.label,
    }));
    
    return(
        <main>
            <section id="form-section" className="container">
                <div className="form-container">
                    <form className="card form" onSubmit={handleSubmit}>
                        <h2>Filho</h2>
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
                                <FormLabel text="Pai" />
                                <FormSelect
                                    {...formData.pai}
                                    className="form-control form-select-container"
                                    options={pais}
                                    value={formData.pai.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "pai",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                    getOptionLabel={(obj: any) => obj.descricao}
                                    getOptionValue={(obj: any) => String(obj.id)}
                                />
                                <div className="form-error">{formData.pai.message}</div>
                            </div>
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
                                <FormLabel text="Roteiro" />
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
                                    getOptionValue={(obj: any) => String(obj.id)}
                                />
                                <div className="form-error">{formData.roteiro.message}</div>
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
                            <div>
                                <FormLabel text="Tipo de Filho" isRequired />
                                <FormSelect
                                    {...formData.tipo}
                                    className="form-control form-select-container"
                                    options={tipoOptions}
                                    value={formData.tipo.value}
                                    onChange={(selectedOption: any) => {
                                        const newFormData = forms.updateAndValidate(
                                            formData,
                                            "tipo",
                                            selectedOption
                                        );
                                        setFormData(newFormData);
                                    }}
                                    onTurnDirty={handleTurnDirty}
                                />
                                <div className="form-error">{formData.tipo.message}</div>
                            </div>
                        </div>
                        <div className="form-buttons">
                            <Link to="/sons">
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