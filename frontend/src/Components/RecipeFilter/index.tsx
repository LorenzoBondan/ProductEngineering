import { Controller, useForm } from 'react-hook-form';
import { useEffect, useState } from 'react';
import {ReactComponent as SearchIcon} from 'assets/images/search-icon.svg';
import { Category } from 'types';
import { requestBackend } from 'util/requests';
import Select from 'react-select';
import './styles.css';

export type RecipeFilterData = {
    name : string;
    category: Category | null;
}

type Props = {
    onSubmitFilter : (data: RecipeFilterData) => void;
}

const RecipeFilter = ( {onSubmitFilter} : Props ) => {

    const { register, handleSubmit, setValue, getValues, control } = useForm<RecipeFilterData>();

    const [selectCategories, setSelectCategories] = useState<Category[]>();
    
    useEffect(() => {
        requestBackend({url: '/categories', params: {page: 0, size: 50, },})
            .then(response => {
                setSelectCategories(response.data.content)
            })
    }, []);

    const onSubmit = (formData : RecipeFilterData) => {
        onSubmitFilter(formData);
    };

    const handleFormClear = () => {
        setValue('name', '');
        setValue('category', null);
    }

    const handleChangeCategory = (value: Category) => {
        setValue('category', value);

        const obj : RecipeFilterData = {
            name: getValues('name'), 
            category: getValues('category'), 
        };

        onSubmitFilter(obj);
    }

    return(
        <div className="base-card user-filter-container">
            <form onSubmit={handleSubmit(onSubmit)} className='user-filter-form'>
                <div className='user-filter-name-container'>
                    <input 
                        {...register("name")}
                        type="text"
                        className={`form-control text-dark`}
                        placeholder="Recipe name"
                        name="name"
                    />
                    <button className='user-filter-button-search-icon'>
                        <SearchIcon/>
                    </button>
                </div>
                <div className='user-filter-category-container text-secondary' style={{fontSize:"20px", width:"100%"}}>
                    <Controller 
                        name = 'category'
                        control = {control}
                        render = {( {field} ) => (
                        <Select 
                            {...field}
                            options={selectCategories?.sort()}
                            isClearable
                            classNamePrefix="recipe-filter-select"
                            placeholder="Category"
                            getOptionLabel={(category: Category) => category.name}
                            getOptionValue={(category: Category) => category.id.toString()}

                            onChange={value => handleChangeCategory(value as Category)}
                        />    
                        )}
                    />
                </div>
                <div className='user-filter-bottom-container'>
                    <button onClick={handleFormClear} className='btn btn-outline-secondary btn-user-filter-clear'>
                        CLEAR <span className='btn-user-filter-word'>FILTER</span>
                    </button>
                </div>
            </form>
        </div>
    );
}

export default RecipeFilter;