import { Route, Switch } from "react-router-dom";
import List from "./List";

const Moldings = () => {
    return (
        <Switch>
          <Route path="/moldings" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Moldings;