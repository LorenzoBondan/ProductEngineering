import { Route, Switch } from "react-router-dom";
import List from "./List";

const Plastics = () => {
    return (
        <Switch>
          <Route path="/plastics" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Plastics;