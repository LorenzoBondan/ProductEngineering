import { Route, Switch } from "react-router-dom";
import List from "./List";

const MDPSons = () => {
    return (
        <Switch>
          <Route path="/mdpsons" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default MDPSons;