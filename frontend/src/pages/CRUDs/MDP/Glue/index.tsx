import { Route, Switch } from "react-router-dom";
import List from "./List";

const Glues = () => {
    return (
        <Switch>
          <Route path="/glues" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Glues;