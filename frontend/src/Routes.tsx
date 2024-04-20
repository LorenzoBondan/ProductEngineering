import Footer from "Components/Footer";
import Navbar from "Components/Navbar";
import Admin from "pages/Admin";
import Auth from "pages/Auth";
import Sheets from "pages/CRUDs/MDP/Sheet";
import CreateRecipe from "pages/CreateRecipe";
import Home from "pages/Home";
import MDPPage from "pages/Home/MainComponents/MDPPage";
import Profile from "pages/Profile";
import RecipeDetails from "pages/RecipeDetails";
import { Redirect, Route, Router, Switch } from "react-router-dom";
import { isAuthenticated } from "util/auth";
import history from "util/history";

const Routes = () => {

    return(
        <Router history={history}> 
            <div className="flex-direction-row">
                {isAuthenticated() && <Navbar/>}

                <Switch>
                    {isAuthenticated() ? (
                        <Redirect from='/' to='/home' exact />
                    ) : (
                        <Redirect from='/' to='/auth/login' exact />
                    )}
                    
                    <Route path="/home">
                        <Home/>
                    </Route>

                    <Route path="/mdp" exact>
                        <MDPPage/>
                    </Route>

                    <Route path="/recipes/:recipeId" exact>
                        <RecipeDetails/>
                    </Route>

                    <Route path="/create" exact>
                        <CreateRecipe/>
                    </Route>

                    <Route path="/profile" exact>
                        <Profile/>
                    </Route>

                    <Redirect from='/auth' to='/auth/login' exact />
                    <Route path="/auth">
                        <Auth/>
                    </Route>

                    <Redirect from="/admin" to="/admin/users" exact />
                    <Route path="/admin">
                        <Admin/>
                    </Route>

                    <Route path="/sheets">
                        <Sheets/>
                    </Route>

                </Switch>
            </div>
            <Footer/>
        </Router>
    );
}

export default Routes;