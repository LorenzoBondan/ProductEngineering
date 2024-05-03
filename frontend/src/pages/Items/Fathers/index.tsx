import { Route, Switch } from "react-router-dom";
import List from "./List";

const Fathers = () => {
    return (
        <Switch>
          <Route path="/fathers" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Fathers;