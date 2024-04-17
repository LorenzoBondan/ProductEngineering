/* eslint-disable jsx-a11y/anchor-is-valid */
import { useCallback, useEffect, useState } from 'react';
import './styles.css';
import { User } from 'types';
import { getTokenData } from 'util/auth';
import { AxiosRequestConfig } from 'axios';
import { requestBackend } from 'util/requests';
import RecipeCard from 'Components/RecipeCard';
import FavoritedRecipeCard from './FavoritedRecipeCard';
import { GiNoodles } from 'react-icons/gi';
import { AiFillStar } from 'react-icons/ai';
import { BsFillGearFill } from 'react-icons/bs';
import Modal from 'react-modal';
import { useForm } from 'react-hook-form';

const Profile = () => {

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

    const [modalIsOpen, setIsOpen] = useState(false);

    function openModal(){
      setIsOpen(true);
      console.log("executou")
    }
  
    function closeModal(){
      setIsOpen(false);
    }
  
    const { register, handleSubmit, formState: {errors}, setValue } = useForm<User>();
  
    useEffect(() => {
      if(user){
        requestBackend({url:`/users/${user?.id}`, withCredentials:true})
          .then((response) => {
              const user = response.data as User;
  
              setValue('name', user.name);
              setValue('imgUrl', user.imgUrl);
              setValue('password', user.password);
              setValue('email', user.email);
              setValue('favoritesId', user.favoritesId);
              setValue('feedbacksId', user.feedbacksId);
              setValue('recipes', user.recipes);
              setValue('roles', user.roles);
        })  
      }
    }, [user, setValue]);
  
    const onSubmit = (formData : User) => {
      const params : AxiosRequestConfig = {
          method:"PUT",
          url : `/users/${user?.id}`,
          data: formData,
          withCredentials: true
      };
  
      requestBackend(params)
          .then(response => {
              console.log('success', response.data);
              closeModal();
              getUser();
          })
    };

    return(
        <div className='profile-container'>
            <div className='profile-user-info base-card'>
                <div className='profile-image'>
                    <img src={user?.imgUrl} alt="" />
                </div>
                <h2>{user?.name}</h2>
                <p style={{fontFamily: "Be Vietnam Pro"}}>{user?.email}</p>
                <div className='profile-user-badge'>
                    <div className='profile-badge-item'>
                        <h5>{user?.recipes.length}</h5>
                        <p>Recipes</p>
                    </div>
                    <p className='recipe-info-item-separator'></p>
                    <div className='profile-badge-item'>
                        <h5>{user?.favoritesId.length}</h5>
                        <p>Favorites</p>
                    </div>
                    <p className='recipe-info-item-separator'></p>
                    <div className='profile-badge-item'>
                        <h5>{user?.feedbacksId.length}</h5>
                        <p>Feedbacks</p>
                    </div>
                </div>
                <div className='profile-user-edit'>
                    <a onClick={openModal}>
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        <h6><BsFillGearFill style={{marginRight:"3px"}}/>Edit Profile</h6>
                    </a>
                    <Modal 
                        isOpen={modalIsOpen}
                        onRequestClose={closeModal}
                        contentLabel="Example Modal"
                        overlayClassName="modal-overlay"
                        className="modal-content"
                        >
                        <form onSubmit={handleSubmit(onSubmit)} className="recipe-feedback-form">
                            <h4>Edit Profile</h4>
                            <div className='recipe-feedback-input-container'>
                                <label htmlFor="">Img Url</label>
                                <input 
                                    {...register("imgUrl", {
                                        required: 'Campo obrigatório',
                                        pattern: { 
                                        value: /^(https?|chrome):\/\/[^\s$.?#].[^\s]*$/gm,
                                        message: 'Insira uma URL válida'
                                        }
                                    })}
                                    type="text"
                                    className={`form-control base-input ${errors.imgUrl ? 'is-invalid' : ''}`}
                                    placeholder="URL of user's image"
                                    name="imgUrl"
                                />
                                <div className='invalid-feedback d-block'>{errors.imgUrl?.message}</div>
                            </div>
                            <div className="recipe-feedback-buttons">
                                <button onClick={closeModal} className="btn">Close</button>
                                <button className="btn">Submit</button>
                            </div>
                        </form>
                    </Modal>
                </div>
            </div>
            <div className='profile-user-recipes'>
                <div className='profile-my-recipes base-card'>
                    <h3><GiNoodles style={{marginRight:"5px"}}/> My Recipes <h6>({user?.recipes.length})</h6></h3>
                    <div className='row my-recipes-row'>
                        {user?.recipes.map(recipe => (
                            <div className='col-sm-12 col-md-12 col-lg-6 col-xl-6 recipes-column'>
                                <RecipeCard recipe={recipe} onUpdateFavorite={getUser} key={recipe.id}/>
                            </div>
                        ))}
                    </div>
                </div>
                <div className='profile-favorited-recipes base-card'>
                    <h3><AiFillStar style={{marginRight:"5px"}}/> My Favorited Recipes <h6>({user?.favoritesId.length})</h6></h3>
                    <div className='row my-recipes-row'>
                        {user?.favoritesId.map(favoriteId => (
                            <div className='col-sm-12 col-md-12 col-lg-6 col-xl-6 recipes-column'>
                                <FavoritedRecipeCard recipeId={favoriteId} onUpdateFavorite={getUser} key={favoriteId} />
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Profile;