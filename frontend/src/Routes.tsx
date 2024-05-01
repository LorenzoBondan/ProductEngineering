import Footer from "Components/Footer";
import Navbar from "Components/Navbar";
import PrivateRoute from "Components/PrivateRoute";
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
import Materials from "pages/CRUDs/Public/Materials";
import HomeBaseMaterials from "pages/Home";
import MDPPage from "pages/Home/MainComponents/MDPPage";
import Operations from "pages/Operations";
import Profile from "pages/Profile";
import MDPStruct from "pages/Structs/MDPStruct";
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
                        <Redirect from='/' to='/operations' exact />
                    ) : (
                        <Redirect from='/' to='/auth/login' exact />
                    )}

                    <PrivateRoute path="/operations">
                        <Operations/>
                    </PrivateRoute>
                    
                    <PrivateRoute path="/homebasematerials">
                        <HomeBaseMaterials/>
                    </PrivateRoute>

                    <PrivateRoute path="/mdp">
                        <MDPPage/>
                    </PrivateRoute>

                    <PrivateRoute path="/profile">
                        <Profile/>
                    </PrivateRoute>

                    <Redirect from='/auth' to='/auth/login' exact />
                    <Route path="/auth">
                        <Auth/>
                    </Route>

                    <Redirect from="/admin" to="/admin/users" exact />
                    <PrivateRoute path="/admin">
                        <Admin/>
                    </PrivateRoute>

                    <PrivateRoute path="/sheets">
                        <Sheets/>
                    </PrivateRoute>

                    <PrivateRoute path="/edgeBandings">
                        <EdgeBandings/>
                    </PrivateRoute>

                    <PrivateRoute path="/glues">
                        <Glues/>
                    </PrivateRoute>

                    <PrivateRoute path="/paintings">
                        <Paintings/>
                    </PrivateRoute>

                    <PrivateRoute path="/paintingBorderBackgrounds">
                        <PaintingBorderBackgrounds/>
                    </PrivateRoute>
                    
                    <PrivateRoute path="/polyesters">
                        <Polyesters/>
                    </PrivateRoute>

                    <PrivateRoute path="/drawerPulls">
                        <DrawerPulls/>
                    </PrivateRoute>
                    
                    <PrivateRoute path="/glasses">
                        <Glasses/>
                    </PrivateRoute>
                                        
                    <PrivateRoute path="/trySquares">
                        <TrySquares/>
                    </PrivateRoute>
                                                            
                    <PrivateRoute path="/moldings">
                        <Moldings/>
                    </PrivateRoute>

                    <PrivateRoute path="/screws">
                        <Screws/>
                    </PrivateRoute>
                    
                    <PrivateRoute path="/cornerBrackets">
                        <CornerBrackets/>
                    </PrivateRoute>

                    <PrivateRoute path="/nonwovenFabrics">
                        <NonwovenFabrics/>
                    </PrivateRoute>

                    <PrivateRoute path="/plastics">
                        <Plastics/>
                    </PrivateRoute>

                    <PrivateRoute path="/polyethylenes">
                        <Polyethylenes/>
                    </PrivateRoute>

                    <PrivateRoute path="/machines">
                        <Machines/>
                    </PrivateRoute>

                    <PrivateRoute path="/machineGroups">
                        <MachineGroups/>
                    </PrivateRoute>

                    <PrivateRoute path="/colors">
                        <Colors/>
                    </PrivateRoute>

                    <PrivateRoute path="/materials">
                        <Materials/>
                    </PrivateRoute>

                    <PrivateRoute path="/mdpstruct">
                        <MDPStruct/>
                    </PrivateRoute>

                </Switch>
            </div>
            <Footer/>
        </Router>
    );
}

export default Routes;