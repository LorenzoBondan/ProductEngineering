import { useCallback, useEffect, useState } from 'react';
import './styles.css';
import { User } from 'types';
import { getTokenData } from 'util/auth';
import { AxiosRequestConfig } from 'axios';
import { requestBackend } from 'util/requests';
import { BsFillGearFill } from 'react-icons/bs';
import Modal from 'react-modal';
import { useForm } from 'react-hook-form';

const Profile = () => {

    const [user, setUser] = useState<User | null>(null);

    const getUser = useCallback(async () => {
        try {
            const email = getTokenData()?.user_name;
            console.log(email);

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
              console.log('Sucesso!', response.data);
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
                <div className='profile-user-edit'>
                    <a onClick={openModal}>
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        <h6><BsFillGearFill style={{marginRight:"3px"}}/>Editar Perfil</h6>
                    </a>
                    <Modal 
                        isOpen={modalIsOpen}
                        onRequestClose={closeModal}
                        contentLabel="Example Modal"
                        overlayClassName="modal-overlay"
                        className="modal-content"
                        >
                        <form onSubmit={handleSubmit(onSubmit)} className="crud-modal-form">
                            <h4 className='crud-modal-title'>Editar Perfil</h4>
                            <div className='row crud-modal-inputs-container'>
                                <label htmlFor="">Url da Imagem</label>
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
                                    placeholder="URL da Imagem"
                                    name="imgUrl"
                                />
                                <div className='invalid-feedback d-block'>{errors.imgUrl?.message}</div>
                            </div>
                            <div className="crud-modal-buttons-container">
                                <button onClick={closeModal} className="btn cancel-button crud-modal-button">Cancelar</button>
                                <button type="submit" className="btn btn-primary text-white crud-modal-button">Salvar</button>
                            </div>
                        </form>
                    </Modal>
                </div>
            </div>
        </div>
    );
}

export default Profile;