import { Route, Switch } from "react-router-dom";
import List from "./List";

const NonwovenFabrics = () => {
    return (
        <Switch>
          <Route path="/nonwovenFabrics" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default NonwovenFabrics;