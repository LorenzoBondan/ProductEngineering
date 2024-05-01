import './styles.css';
import { Switch } from 'react-router-dom';
import PrivateRoute from 'Components/PrivateRoute';
import MainComponents from './MainComponents';
import MDPPage from 'pages/Home/MainComponents/MDPPage';
import MDFPage from './MainComponents/MDFPage';
import AluminiumPage from './MainComponents/AluminiumPage';
import PackagingPage from './MainComponents/PackagingPage';
import MachinePage from './MainComponents/MachinePage';
import PublicPage from './MainComponents/PublicPage';

const HomeBaseMaterials = () => {

    return(
        <div className='home-page'>
            <MainComponents />
            <div className='home-items-container'>
                <Switch>
                    <PrivateRoute path="/homebasematerials/mdp">
                        <MDPPage />
                    </PrivateRoute>
                    <PrivateRoute path="/homebasematerials/mdf">
                        <MDFPage/>
                    </PrivateRoute>
                    <PrivateRoute path="/homebasematerials/aluminium">
                        <AluminiumPage/>
                    </PrivateRoute>
                    <PrivateRoute path="/homebasematerials/packaging">
                        <PackagingPage/>
                    </PrivateRoute>
                    <PrivateRoute path="/homebasematerials/machines">
                        <MachinePage/>
                    </PrivateRoute>
                    <PrivateRoute path="/homebasematerials/public">
                        <PublicPage/>
                    </PrivateRoute>
                </Switch>
            </div>
        </div>
    );
}

export default HomeBaseMaterials;