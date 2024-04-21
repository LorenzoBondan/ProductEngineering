import { useForm } from 'react-hook-form';
import {ReactComponent as SearchIcon} from 'assets/images/search-icon.svg';
import './styles.css';

export type UserFilterData = {
    name : string;
}

type Props = {
    onSubmitFilter : (data: UserFilterData) => void;
}

const UserFilter = ( {onSubmitFilter} : Props ) => {

    const { register, handleSubmit, setValue } = useForm<UserFilterData>();

    const onSubmit = (formData : UserFilterData) => {
        onSubmitFilter(formData);
    };

    const handleFormClear = () => {
        setValue('name', '');
    }

    return(
        <div className="base-card base-filter-container">
            <form onSubmit={handleSubmit(onSubmit)} className='base-filter-form'>
                <div className='base-filter-name-container'>
                    <input 
                        {...register("name")}
                        type="text"
                        className={`form-control text-dark`}
                        placeholder="User's name"
                        name="name"
                    />
                    <button className='base-filter-button-search-icon'>
                        <SearchIcon/>
                    </button>
                </div>
                <div className='base-filter-bottom-container'>
                    <button onClick={handleFormClear} className='btn btn-base-filter-clear'>
                        LIMPAR <span className='btn-base-filter-word'>FILTRO</span>
                    </button>
                </div>
            </form>
        </div>
    );
}

export default UserFilter;