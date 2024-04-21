import { useForm } from 'react-hook-form';
import {ReactComponent as SearchIcon} from 'assets/images/search-icon.svg';
import { useState } from 'react';

export type DescriptionFilterData = {
    description : string;
    status?: string;
}

type Props = {
    onSubmitFilter : (data: DescriptionFilterData) => void;
}

const DescriptionFilter = ( {onSubmitFilter} : Props ) => {

    const { register, handleSubmit, setValue, getValues } = useForm<DescriptionFilterData>();

    const statusOptions = [
        { value: 'ACTIVE', label: 'Ativo' },
        { value: 'INACTIVE', label: 'Inativo' },
    ];

    const [selectedStatus, setSelectedStatus] = useState<string>();

    const handleChangeStatus = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const status = e.target.value;
        setSelectedStatus(status);

        const formData: DescriptionFilterData = {
            description: getValues('description'),
            status: status,
        };

        onSubmitFilter(formData);
    };

    const onSubmit = (formData : DescriptionFilterData) => {
        if (selectedStatus) {
            formData.status = selectedStatus;
        }
        onSubmitFilter(formData);
    };

    const handleFormClear = () => {
        setValue('description', '');
        setSelectedStatus('');
    };

    return(
        <div className="base-card base-filter-container">
            <form onSubmit={handleSubmit(onSubmit)} className='base-filter-form'>
                <div className='base-filter-name-container'>
                    <input 
                        {...register("description")}
                        type="text"
                        className={`form-control text-dark`}
                        placeholder="Descrição"
                        name="description"
                    />
                    <button className='base-filter-button-search-icon'>
                        <SearchIcon/>
                    </button>
                </div>
                <div className='base-filter-bottom-container'>
                    <div className='description-filter-status-container'>
                        <select className="base-input status-filter" value={selectedStatus} onChange={handleChangeStatus}>
                            {statusOptions.map(option => (
                                <option key={option.value} value={option.value}>{option.label}</option>
                            ))}
                        </select>
                    </div>
                    <button onClick={handleFormClear} className='btn btn-outline-secondary btn-base-filter-clear'>
                        LIMPAR <span className='btn-base-filter-word'>FILTRO</span>
                    </button>
                </div>
            </form>
        </div>
    );
}

export default DescriptionFilter;