import { useEffect, useState } from 'react';
import './styles.css';
import { DUser } from '../../../models/user';
import * as userService from '../../../services/userService';
import * as authService from '../../../services/authService';
import { Link } from 'react-router-dom';

export default function Profile() {

    const [user, setUser] = useState<DUser>();

    useEffect(() => {
      if(authService.isAuthenticated()){
        userService.findMe()
        .then(response => {
          setUser(response.data);
        })
        .catch(() => {});
      }
    }, []);

    return(
        <main>
            <section className='container profile-container'>
                <div className='profile-card card'>
                    {user?.userAnexo && 
                        <div className='profile-card-top'>
                            <img src={`data:image/jpeg;base64,${user?.userAnexo.anexo.binario.bytes}`} alt="" />
                        </div>
                    }
                    <div className='profile-card-bottom'>
                        <h3>{user?.name}</h3>
                        <h4>{user?.email}</h4>
                        <Link to="/profile/:userId" className='btn btn-primary edit-profile-button'>
                            Editar perfil
                        </Link>
                    </div>
                </div>
            </section>
        </main>
    );
}