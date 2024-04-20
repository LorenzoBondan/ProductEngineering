import { useForm } from 'react-hook-form';
import {ReactComponent as SearchIcon} from 'assets/images/search-icon.svg';

export type SheetFilterData = {
    description : string;
}

type Props = {
    onSubmitFilter : (data: SheetFilterData) => void;
}

const SheetFilter = ( {onSubmitFilter} : Props ) => {

    const { register, handleSubmit, setValue } = useForm<SheetFilterData>();

    const onSubmit = (formData : SheetFilterData) => {
        onSubmitFilter(formData);
    };

    const handleFormClear = () => {
        setValue('description', '');
    }

    return(
        <div className="base-card base-filter-container">
            <form onSubmit={handleSubmit(onSubmit)} className='base-filter-form'>
                <div className='base-filter-name-container'>
                    <input 
                        {...register("description")}
                        type="text"
                        className={`form-control text-dark`}
                        placeholder="Descrição da chapa"
                        name="description"
                    />
                    <button className='base-filter-button-search-icon'>
                        <SearchIcon/>
                    </button>
                </div>
                <div className='base-filter-bottom-container'>
                    <button onClick={handleFormClear} className='btn btn-outline-secondary btn-base-filter-clear'>
                        LIMPAR <span className='btn-base-filter-word'>FILTRO</span>
                    </button>
                </div>
            </form>
        </div>
    );
}

export default SheetFilter;