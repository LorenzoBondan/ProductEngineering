import { Route, Switch } from "react-router-dom";
import List from "./List";

const DrawerPulls = () => {
    return (
        <Switch>
          <Route path="/drawerPulls" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default DrawerPulls;