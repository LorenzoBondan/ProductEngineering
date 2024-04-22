import { Route, Switch } from "react-router-dom";
import List from "./List";

const Machines = () => {
    return (
        <Switch>
          <Route path="/machines" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Machines;