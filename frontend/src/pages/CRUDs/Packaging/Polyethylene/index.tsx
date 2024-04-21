import { Route, Switch } from "react-router-dom";
import List from "./List";

const Polyethylenes = () => {
    return (
        <Switch>
          <Route path="/polyethylenes" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Polyethylenes;