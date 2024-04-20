import './styles.css';
import { Switch } from 'react-router-dom';
import PrivateRoute from 'Components/PrivateRoute';
import MainComponents from './MainComponents';
import MDPPage from 'pages/Home/MainComponents/MDPPage';
import MDFPage from './MainComponents/MDFPage';
import AluminiumPage from './MainComponents/AluminiumPage';
import PackagingPage from './MainComponents/PackagingPage';

const Home = () => {

    return(
        <div className='home-page'>
            <MainComponents />
            <div className='home-items-container'>
                <Switch>
                    <PrivateRoute path="/home/mdp">
                        <MDPPage />
                    </PrivateRoute>
                    <PrivateRoute path="/home/mdf">
                        <MDFPage/>
                    </PrivateRoute>
                    <PrivateRoute path="/home/aluminium">
                        <AluminiumPage/>
                    </PrivateRoute>
                    <PrivateRoute path="/home/packaging">
                        <PackagingPage/>
                    </PrivateRoute>
                </Switch>
            </div>
        </div>
    );
}

export default Home;