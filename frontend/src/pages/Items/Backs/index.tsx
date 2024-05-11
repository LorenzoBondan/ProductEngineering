import { Route, Switch } from "react-router-dom";
import List from "./List";

const Backs = () => {
    return (
        <Switch>
          <Route path="/backs" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Backs;