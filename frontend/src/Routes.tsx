import Footer from "Components/Footer";
import Navbar from "Components/Navbar";
import Admin from "pages/Admin";
import Auth from "pages/Auth";
import DrawerPulls from "pages/CRUDs/Aluminium/DrawerPull";
import Glasses from "pages/CRUDs/Aluminium/Glass";
import Moldings from "pages/CRUDs/Aluminium/Molding";
import Screws from "pages/CRUDs/Aluminium/Screw";
import TrySquares from "pages/CRUDs/Aluminium/TrySquare";
import Machines from "pages/CRUDs/Guides/Machine";
import MachineGroups from "pages/CRUDs/Guides/MachineGroup";
import Paintings from "pages/CRUDs/MDF/Painting";
import PaintingBorderBackgrounds from "pages/CRUDs/MDF/PaintingBorderBackground";
import Polyesters from "pages/CRUDs/MDF/Polyester";
import EdgeBandings from "pages/CRUDs/MDP/EdgeBanding";
import Glues from "pages/CRUDs/MDP/Glue";
import Sheets from "pages/CRUDs/MDP/Sheet";
import CornerBrackets from "pages/CRUDs/Packaging/CornerBracket";
import NonwovenFabrics from "pages/CRUDs/Packaging/NonwovenFabric";
import Plastics from "pages/CRUDs/Packaging/Plastic";
import Polyethylenes from "pages/CRUDs/Packaging/Polyethylene";
import Colors from "pages/CRUDs/Public/Color";
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

                    <Route path="/edgeBandings">
                        <EdgeBandings/>
                    </Route>

                    <Route path="/glues">
                        <Glues/>
                    </Route>

                    <Route path="/paintings">
                        <Paintings/>
                    </Route>

                    <Route path="/paintingBorderBackgrounds">
                        <PaintingBorderBackgrounds/>
                    </Route>
                    
                    <Route path="/polyesters">
                        <Polyesters/>
                    </Route>

                    <Route path="/drawerPulls">
                        <DrawerPulls/>
                    </Route>
                    
                    <Route path="/glasses">
                        <Glasses/>
                    </Route>
                                        
                    <Route path="/trySquares">
                        <TrySquares/>
                    </Route>
                                                            
                    <Route path="/moldings">
                        <Moldings/>
                    </Route>

                    <Route path="/screws">
                        <Screws/>
                    </Route>
                    
                    <Route path="/cornerBrackets">
                        <CornerBrackets/>
                    </Route>

                    <Route path="/nonwovenFabrics">
                        <NonwovenFabrics/>
                    </Route>

                    <Route path="/plastics">
                        <Plastics/>
                    </Route>

                    <Route path="/polyethylenes">
                        <Polyethylenes/>
                    </Route>

                    <Route path="/machines">
                        <Machines/>
                    </Route>

                    <Route path="/machineGroups">
                        <MachineGroups/>
                    </Route>

                    <Route path="/colors">
                        <Colors/>
                    </Route>

                </Switch>
            </div>
            <Footer/>
        </Router>
    );
}

export default Routes;