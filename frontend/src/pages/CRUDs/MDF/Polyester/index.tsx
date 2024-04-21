import { Route, Switch } from "react-router-dom";
import List from "./List";

const Polyesters = () => {
    return (
        <Switch>
          <Route path="/polyesters" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Polyesters;