import { useCallback, useEffect, useState } from 'react';
import './styles.css';
import { Recipe, SpringPage } from 'types';
import { AxiosRequestConfig } from 'axios';
import RecipeFilter, { RecipeFilterData } from 'Components/RecipeFilter';
import { requestBackend } from 'util/requests';
import Pagination from "Components/Pagination";
import RecipeCard from 'Components/RecipeCard';

type ControlComponentsData = {
    activePage: number;
    filterData: RecipeFilterData;
}

const Recipes = () => {

    // pagination and filter
    const [controlComponentsData, setControlComponentsData] = useState<ControlComponentsData>({activePage:0, filterData: { name: '', category: null },});

    const handlePageChange = (pageNumber : number) => {
      setControlComponentsData({activePage: pageNumber, filterData: controlComponentsData.filterData});
    }

    const [recipes, setRecipes] = useState<SpringPage<Recipe>>();

    const getRecipes = useCallback(() => {
        const params : AxiosRequestConfig = {
          method:"GET",
          url: "/recipes",
          params: {
            page: controlComponentsData.activePage,
            size: 8,
    
            name: controlComponentsData.filterData.name,
            categoryId: controlComponentsData.filterData.category?.id
          },
        }
      
        requestBackend(params) 
          .then(response => {
            setRecipes(response.data);
            window.scrollTo(0, 0);
          })
      }, [controlComponentsData])

    useEffect(() => {
        getRecipes();
    }, [getRecipes]);

    const handleSubmitFilter = (data : RecipeFilterData) => {
        setControlComponentsData({activePage: 0, filterData: data});
    }

    return(
        <div className="recipes-container">
            <div className='most-popular-recipes-container'>
                <div className='recipes-search-bar-container'>
                    <RecipeFilter onSubmitFilter={handleSubmitFilter} />
                </div>
                <div className='popular-recipes-title'>
                    <h3>The most popular recipes</h3>
                    <p>{recipes?.totalElements} recipes registered</p>
                </div>
                <div className='row recipes-row'>
                    {recipes?.content
                        .sort( (a,b) => a.pontuationAverage < b.pontuationAverage ? 1 : -1)
                        .map((recipe) => (
                            <div className="col-sm-12 col-md-12 col-lg-6 col-xl-3 recipes-column">
                                <RecipeCard recipe={recipe} onUpdateFavorite={() => getRecipes()} key={recipe.id}/>
                            </div>      
                        ))
                    }
                </div>
            </div>
            <div className='pagination-container'>
              <Pagination 
                pageCount={(recipes) ? recipes.totalPages : 0} 
                range={2}
                onChange={handlePageChange}
                forcePage={recipes?.number}
              />
            </div>
        </div>
    );
}

export default Recipes;