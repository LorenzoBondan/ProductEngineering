import { Category, Recipe, User } from "types";
import { Controller, useForm } from "react-hook-form";
import { useHistory } from "react-router-dom";
import { AxiosRequestConfig } from "axios";
import { requestBackend } from "util/requests";
import { useCallback, useEffect, useState } from "react";
import { getTokenData } from "util/auth";
import logo from 'assets/images/recipe-logo.png';
import Select from "react-select";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import "./styles.css";

const CreateRecipe = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    control,
  } = useForm<Recipe>();
  const history = useHistory();

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
        console.log("User:", response.data);
      }
    } catch (error) {
      console.log("Error: " + error);
    }
  }, []);

  useEffect(() => {
    getUser();
  }, [getUser]);

  const onSubmit = async (formData: Recipe) => {
    if (user) {
      formData.authorId = user?.id;
      formData.pontuationAverage = 0;
      formData.feedbacks = [];
      formData.usersFavoritedId = [];

      console.log(formData);

      try {
        const params: AxiosRequestConfig = {
          method: "POST",
          url: "/recipes",
          data: formData,
          withCredentials: true,
        };

        await requestBackend(params);
        history.push("/recipes");
        toast.success("recipe created!");
      } catch (error) {
        console.log("Error: " + error);
      }
    }
  };

  const handleCancel = () => {
    history.push("/recipes");
  };

  const [selectCategories, setSelectCategories] = useState<Category[]>();

  useEffect(() => {
    requestBackend({ url: "/categories", withCredentials: true }).then(
      (response) => {
        setSelectCategories(response.data.content);
      }
    );
  }, []);

  return (
    <div className="create-recipe-container">
      <h1>Create New Recipe</h1>
        <div className="base-card post-card-form-card">
          <form onSubmit={handleSubmit(onSubmit)}>
            <div className="row post-crud-inputs-container">
              <div className="post-crud-inputs-left-container">
                <div className="margin-bottom-30">
                  <label htmlFor="">Name</label>
                  <input
                    {...register("name", {
                      required: "Campo obrigatório",
                    })}
                    type="text"
                    className={`form-control base-input ${
                      errors.name ? "is-invalid" : ""
                    }`}
                    placeholder="Name"
                    name="name"
                  />
                  <div className="invalid-feedback d-block">
                    {errors.name?.message}
                  </div>
                </div>
              </div>
              <div className="post-crud-inputs-left-container">
                <div className="margin-bottom-30">
                  <label htmlFor="">Ingredients (separe them using a ,)</label>
                  <input
                    {...register("ingredients", {
                      required: "Campo obrigatório",
                    })}
                    type="text"
                    className={`form-control base-input ${
                      errors.ingredients ? "is-invalid" : ""
                    }`}
                    placeholder="Ingredients"
                    name="ingredients"
                  />
                  <div className="invalid-feedback d-block">
                    {errors.ingredients?.message}
                  </div>
                </div>
              </div>
              <div className="post-crud-inputs-left-container">
                <div className="margin-bottom-30">
                  <label htmlFor="">Preparation</label>
                  <input
                    {...register("preparation", {
                      required: "Campo obrigatório",
                    })}
                    type="text"
                    className={`form-control base-input ${
                      errors.preparation ? "is-invalid" : ""
                    }`}
                    placeholder="Preparation"
                    name="preparation"
                  />
                  <div className="invalid-feedback d-block">
                    {errors.preparation?.message}
                  </div>
                </div>
                <div className="post-crud-inputs-left-container">
                <div className="margin-bottom-30">
                  <label htmlFor="">Minutes</label>
                  <input
                    {...register("time", {
                      required: "Campo obrigatório",
                    })}
                    type="text"
                    className={`form-control base-input ${
                      errors.preparation ? "is-invalid" : ""
                    }`}
                    placeholder="Minutes"
                    name="time"
                  />
                  <div className="invalid-feedback d-block">
                    {errors.time?.message}
                  </div>
                </div>
                <div className='margin-bottom-30'>
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
                      placeholder="URL of recipe's image"
                      name="imgUrl"
                    />
                  <div className='invalid-feedback d-block'>{errors.imgUrl?.message}</div>
                </div>
                <div className="margin-bottom-30">
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
                  {errors.categories && (
                    <div className="invalid-feedback d-block">
                      Campo obrigatório
                    </div>
                  )}
                </div>
              </div>
              <div className="post-crud-buttons-container">
                <button
                  className="btn btn-outline-secondary post-crud-buttons"
                  onClick={handleCancel}
                >
                  CANCEL
                </button>
                <button
                  className="btn btn-primary text-white post-crud-buttons"
                  onClick={handleSubmit(onSubmit)}
                >
                  SAVE
                </button>
              </div>
            </div>
            </div>
          </form>
          <div className="new-recipe-image-container">
            <img src={logo} alt="" />
           </div>
        </div>
    </div>
  );
};

export default CreateRecipe;
