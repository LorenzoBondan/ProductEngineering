import { Route, Switch } from "react-router-dom";
import List from "./List";

const Materials = () => {
    return (
        <Switch>
          <Route path="/materials" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Materials;