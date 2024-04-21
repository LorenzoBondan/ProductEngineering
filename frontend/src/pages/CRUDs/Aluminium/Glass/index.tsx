import { Route, Switch } from "react-router-dom";
import List from "./List";

const Glasses = () => {
    return (
        <Switch>
          <Route path="/glasses" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Glasses;