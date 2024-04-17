import { useParams } from 'react-router-dom';
import './styles.css';
import { useCallback, useEffect, useState } from 'react';
import { Category, Feedback, Recipe, User } from 'types';
import { AxiosRequestConfig } from 'axios';
import { requestBackend } from 'util/requests';
import { getTokenData } from 'util/auth';
import starIcon from 'assets/images/star.png';
import like from 'assets/images/like.png';
import likeFilled from 'assets/images/like_filled.png';
import { toast } from 'react-toastify';
import FeedbackCard from './FeedbackCard';
import Modal from 'react-modal';
import { Controller, useForm } from 'react-hook-form';
import { FaStar } from 'react-icons/fa';
import Select from 'react-select';

type UrlParams = {
    recipeId: string;
}

const RecipeDetails = () => {

    const { recipeId } = useParams<UrlParams>();

    const [recipe, setRecipe] = useState<Recipe>();

    const getRecipe = useCallback(async () => {
        try {
            const params: AxiosRequestConfig = {
            method: "GET",
            url: `/recipes/${recipeId}`,
            withCredentials: true,
        };
  
        const response = await requestBackend(params);
        setRecipe(response.data);
        
        } catch (error) {
            console.log("Error: " + error);
        }
    }, [recipeId]);

    useEffect(() => {
        getRecipe();
    }, [getRecipe]);

    const [user, setUser] = useState<User | null>(null);

    const getUser = useCallback(async () => {
        try {
            const email = getTokenData()?.user_name;

            if (email) {
                const params: AxiosRequestConfig = {
                method: "GET",
                url: `/users/email/${email}`,
                withCredentials: true,
            };

            const response = await requestBackend(params);
            setUser(response.data);
        }
        } catch (error) {
            console.log("Error: " + error);
        }
    }, []);

    useEffect(() => {
        getUser();
    }, [getUser]);

    const plotStars = (quantity: number) => {
        const arrayImages = Array.from({length: quantity});

        return(
            <div style={{display:"flex", flexDirection:"row"}}>
                {quantity !== 0 ? 
                    arrayImages.map((_) => (
                        <img src={starIcon} alt="" style={{height:"20px", marginLeft:"5px"}}/>
                    )) : 
                        <p></p>
                }
            </div>
        );
    };

    const [creator, setCreator] = useState<User | null>(null);

    const getCreator = useCallback(async () => {
        try {
            if (recipe) {
                const params: AxiosRequestConfig = {
                method: "GET",
                url: `/users/${recipe.authorId}`,
                withCredentials: true,
                };

                const response = await requestBackend(params);
                setCreator(response.data);
            }
        } catch (error) {
            console.log("Error: " + error);
        }
    }, [recipe]);

    useEffect(() => {
        getCreator();
    }, [getCreator]);

    const addRecipeAsFavorite = () => {
        const params : AxiosRequestConfig = {
            method:"PUT",
            url: `/recipes/addFavorite/${recipe?.id}`,
            withCredentials:true
          }
          requestBackend(params) 
            .then(response => {
                getRecipe();
                toast.info(`${recipe?.name} added to your favorites!`, {
                    className: 'custom-toast',
                    bodyClassName: 'custom-toast-body',
                });
            })
    }

    const removeRecipeAsFavorite = () => {
        const params : AxiosRequestConfig = {
            method:"PUT",
            url: `/recipes/removeFavorite/${recipe?.id}`,
            withCredentials:true
          }
          requestBackend(params) 
            .then(response => {
                getRecipe();
                toast.info(`${recipe?.name} removed from your favorites!`, {
                    className: 'custom-toast',
                    bodyClassName: 'custom-toast-body-removed',
                });
            })
    }

    const numberOfIngredients = recipe?.ingredients.split(',').length;
    const ingredients = recipe?.ingredients.split(',');

    function capitalizeFirstLetter(str: string): string {
        return str.replace(/(?:^|\s)\S/g, (match) => match.toUpperCase());
    }

    const { register: registerFeedback , handleSubmit: handleSubmitFeedback, formState: {errors}, setValue } = useForm<Feedback>();

    const [modalIsOpen, setModalIsOpen] = useState(false);

    function openModal(){
        setModalIsOpen(true);
    }
  
    function closeModal(){
        setModalIsOpen(false);
        setValue('comment', '');
        setPontuation(0);
        setAlertMessage('');
    }

    const onSubmitFeedback = (formData : Feedback) => {
        if(user && recipe){
            formData.user = user;
            formData.pontuation = pontuation;
            formData.recipeId = recipe.id;
            const startDate = new Date();
            startDate.setHours(startDate.getHours() - 3);
            formData.moment = startDate.toISOString();

            const params : AxiosRequestConfig = {
              method: "POST",
              url : `/feedbacks`,
              data: formData,
              withCredentials: true
            };
      
            requestBackend(params)
              .then(response => {
                  toast.success("Feedback added");
                  closeModal();
                  getRecipe();
                  
                  setValue('comment', '');
                  setPontuation(0);
              })
              .catch((error) => {
                  if (error.response && error.response.status === 400) {
                    setAlertMessage('You have already provided a feedback for this recipe.');
                  } else {
                    setAlertMessage('An error occurred while processing the request.');
                  }
              })
        }
    };

    const [pontuation, setPontuation] = useState(0);

    const handleStarClick = (index: number) => {
        setPontuation(index + 1);
    };

    const amITheAuthor = () => {
        if(recipe?.authorId === user?.id){
            return true;
        }
        else{
            return false;
        }
    }

    const { register: registerRecipe, handleSubmit: handleSubmitRecipe, setValue: setValueRecipe, control } = useForm<Recipe>();

    useEffect(() => {
        requestBackend({url:`/recipes/${recipeId}`, withCredentials:true})
            .then((response) => {
                const recipe = response.data as Recipe;

                setValueRecipe('name', recipe.name);
                setValueRecipe('imgUrl', recipe.imgUrl);
                setValueRecipe('authorId', recipe.authorId);
                setValueRecipe('ingredients', recipe.ingredients);
                setValueRecipe('preparation', recipe.preparation);
                setValueRecipe('time', recipe.time);
                setValueRecipe('categories', recipe.categories);
                setValueRecipe('feedbacks', recipe.feedbacks);
                setValueRecipe('pontuationAverage', recipe.pontuationAverage);
                setValueRecipe('usersFavoritedId', recipe.usersFavoritedId);
            })
    }, [recipeId, setValueRecipe]);

    const [recipeModalIsOpen, setRecipeModalIsOpen] = useState(false);

    function openRecipeModal(){
        setRecipeModalIsOpen(true);
    }
  
    function closeRecipeModal(){
        setRecipeModalIsOpen(false);
    }

    const onSubmitRecipe = (formData : Recipe) => {
        if(user && recipe){

            const params : AxiosRequestConfig = {
              method: "PUT",
              url : `/recipes/${recipe.id}`,
              data: formData,
              withCredentials: true
            };
      
            requestBackend(params)
              .then(response => {
                  toast.success("Recipe updated");
                  closeRecipeModal();
                  getRecipe();
                  console.log(formData);
                  
              })
              .catch((error) => {
                  console.log(error);
              })
        }
    };

    const [selectCategories, setSelectCategories] = useState<Category[]>();

    useEffect(() => {
      requestBackend({ url: "/categories", withCredentials: true }).then(
        (response) => {
          setSelectCategories(response.data.content);
        }
      );
    }, []);

    const [alertMessage, setAlertMessage] = useState('');

    return(
        <div className='recipe-details-container'>
            <div className='recipe-info-container '>
                <div className='recipe-info-content'>
                    <div className='recipe-info-title'>
                        <h1>{recipe?.name}</h1>
                        {recipe && recipe?.feedbacks.length > 0 && 
                            <div className='recipe-info-feedback'>
                                {recipe && plotStars(recipe?.pontuationAverage)}
                                <p>({recipe?.pontuationAverage})</p>
                            </div>
                        }
                    </div>
                    <div className='recipe-info-time'>
                        <div className='recipe-info-item'>
                            <h5>{recipe?.time}</h5>
                            <p>Minutes</p>
                        </div>
                        <span className='recipe-info-item-separator'></span>
                        <div className='recipe-info-item'>
                            <h5>{numberOfIngredients}</h5>
                            <p>Ingredients</p>
                        </div>
                        <span className='recipe-info-item-separator'></span>
                        <div className='recipe-info-item'>
                            <h5>{recipe?.feedbacks.length}</h5>
                            <p>Feedbacks</p>
                        </div>
                    </div>
                    <div className='recipe-info-feedback-buttons'>
                        <button onClick={openModal} className='btn btn-primary'>Give a Feedback</button>
                        <Modal 
                              isOpen={modalIsOpen}
                              onRequestClose={closeModal}
                              contentLabel="Example Modal"
                              overlayClassName="modal-overlay"
                              className="modal-content"
                              >
                              <form onSubmit={handleSubmitFeedback(onSubmitFeedback)} className="recipe-feedback-form">
                                  <h4>Recipe Feedback</h4>
                                  <div className='stars-row'>
                                    {Array.from({ length: 5 }).map((_, index) => (
                                        <FaStar
                                        key={index}
                                        className={`star ${index < pontuation ? 'selected' : ''}`}
                                        onClick={() => handleStarClick(index)}
                                        />
                                    ))}
                                  </div>
                                  <div className="recipe-feedback-input-container">
                                      <label htmlFor="">Comment</label>
                                      <textarea 
                                        rows={5} 
                                        {...registerFeedback("comment", {
                                        required: 'Campo obrigatório',
                                        })}
                                        className={`form-control base-input ${errors.comment ? 'is-invalid' : ''} h-auto`}
                                        placeholder="Comment"
                                        name="comment"
                                       />
                                        <div className='invalid-feedback d-block'>{errors.comment?.message}</div>
                                  </div>
                                  {alertMessage && <p className="error-message">{alertMessage}</p>}
                                  <div className="recipe-feedback-buttons">
                                      <button onClick={closeModal} className="btn">Close</button>
                                      <button className="btn">Submit</button>
                                  </div>
                            </form>
                        </Modal>
                        <div className='recipe-info-buttons'>
                            {user && recipe?.usersFavoritedId.includes(user?.id) ? (
                                <img src={likeFilled} alt="" onClick={() => removeRecipeAsFavorite()}/>
                            ) : (
                                <img src={like} alt="" onClick={() => addRecipeAsFavorite()}/>
                            )}
                            <p>{recipe?.usersFavoritedId.length}</p>
                        </div>
                        {amITheAuthor() ? (
                            <div className='recipe-info-creator'> 
                            <button onClick={openRecipeModal} className='btn btn-primary'>Edit Recipe</button>
                                <Modal 
                                    isOpen={recipeModalIsOpen}
                                    onRequestClose={closeRecipeModal}
                                    contentLabel="Example Modal"
                                    overlayClassName="modal-overlay"
                                    className="modal-content"
                                    >
                                    <form onSubmit={handleSubmitRecipe(onSubmitRecipe)} className="recipe-feedback-form">
                                        <h4>Edit Recipe</h4>
                                        <div className='recipe-feedback-input-container'>
                                            <label htmlFor="">Name</label>
                                            <input 
                                                {...registerRecipe("name", {
                                                required: 'Campo obrigatório',
                                                })}
                                                type="text"
                                                className={`form-control base-input`}
                                                placeholder="Name"
                                                name="name"
                                            />
                                            <label htmlFor="">Ingredients</label>
                                            <input 
                                                {...registerRecipe("ingredients", {
                                                required: 'Campo obrigatório',
                                                })}
                                                type="text"
                                                className={`form-control base-input`}
                                                placeholder="Ingredients"
                                                name="ingredients"
                                            />
                                            <label htmlFor="">Preparation</label>
                                            <input 
                                                {...registerRecipe("preparation", {
                                                required: 'Campo obrigatório',
                                                })}
                                                type="text"
                                                className={`form-control base-input`}
                                                placeholder="Preparation"
                                                name="preparation"
                                            />
                                            <label htmlFor="">Minutes</label>
                                            <input 
                                                {...registerRecipe("time", {
                                                required: 'Campo obrigatório',
                                                })}
                                                type="text"
                                                className={`form-control base-input`}
                                                placeholder="Minutes"
                                                name="time"
                                            />
                                            <label htmlFor="">Img Url</label>
                                            <input 
                                                {...registerRecipe("imgUrl", {
                                                    required: 'Campo obrigatório',
                                                    pattern: { 
                                                    value: /^(https?|chrome):\/\/[^\s$.?#].[^\s]*$/gm,
                                                    message: 'Insira uma URL válida'
                                                    }
                                                })}
                                                type="text"
                                                className={`form-control base-input`}
                                                placeholder="URL of recipe's image"
                                                name="imgUrl"
                                            />
                                            <label htmlFor="">
                                                Categories
                                            </label>
                                            <Controller
                                                name="categories"
                                                rules={{ required: false }}
                                                control={control}
                                                render={({ field }) => (
                                                <Select
                                                    {...field}
                                                    options={selectCategories?.sort((a, b) =>
                                                    a.name > b.name ? 1 : -1
                                                    )}
                                                    classNamePrefix="followers-crud-select"
                                                    placeholder="Categories"
                                                    isMulti
                                                    getOptionLabel={(c: Category) => c.name}
                                                    getOptionValue={(c: Category) => c.id.toString()}
                                                />
                                                )}
                                            />
                                        </div>
                                        <div className="recipe-feedback-buttons">
                                            <button onClick={closeRecipeModal} className="btn">Close</button>
                                            <button className="btn">Submit</button>
                                        </div>
                                    </form>
                                </Modal>
                            </div>
                        ) : (
                        <div className='recipe-info-creator'>   
                            <p>Published by</p>
                            <img src={creator?.imgUrl} alt="" />
                            <h6>{creator?.name}</h6>
                        </div>
                        )}
                    </div>
                </div>
                <div className='recipe-image'>
                    <img src={recipe?.imgUrl} alt="" />
                </div>
            </div>
            <div className='recipe-details-ingredients-preparation-container '>
                <div className='recipe-details-ingredients-container'>
                    <h3>Ingredients</h3>
                    {ingredients?.map(ingredient => (
                        <p key={ingredient}>- {capitalizeFirstLetter(ingredient)}</p>
                    ))}
                </div>
                <div className='recipe-details-preparation-container'>
                    <h3>Preparation Mode</h3>
                    <p>- {recipe?.preparation}</p>
                </div>
            </div>
            <div className='recipe-details-tags-container '>
                <h3>Recipe tags</h3>
                <ul className='recipe-tags'>
                    {recipe?.categories.map(category => (
                        <li>
                            <p key={category.id}>{category.name}</p>
                        </li>
                    ))}
                </ul>
            </div>
            <div className='recipe-details-feedbacks-container'>
                <h3>Reviews ({recipe?.feedbacks.length})</h3>
                {recipe?.feedbacks.map(feedback => (
                    <FeedbackCard feedback={feedback} onDelete={getRecipe} key={feedback.id}/>
                ))}
            </div>
        </div>
    );
}

export default RecipeDetails;